package jkt.centralisateur.service.chat.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkt.centralisateur.interlocutor.data.ClientIdentifiable;
import jkt.centralisateur.interlocutor.data.ClientIdentifier;
import jkt.centralisateur.interlocutor.data.in.ServiceDataIn;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;
import jkt.centralisateur.interlocutor.scoreboard.Client;
import jkt.centralisateur.interlocutor.sender.ServiceSender;
import jkt.centralisateur.service.chat.data.in.ChatMessageData;
import jkt.centralisateur.service.chat.data.in.PingAckData;
import jkt.centralisateur.service.chat.data.in.SignalementData;
import jkt.centralisateur.service.chat.data.out.ChatMessageAckData;
import jkt.centralisateur.service.chat.data.out.DeconnexionData;
import jkt.centralisateur.service.chat.data.out.DistributeChatMessage;
import jkt.centralisateur.service.chat.data.out.ListJoueursData;
import jkt.centralisateur.service.chat.data.out.PingRequestData;
import jkt.centralisateur.service.chat.dto.ChatMessage;

import org.apache.log4j.Logger;

public class ServiceTChatControllerImpl implements ServiceTChatController {
    private static final Logger LOGGER = Logger.getLogger(ServiceTChatControllerImpl.class);

    /** Nombre maximal de messages de chat dans l'historique. */
    static private final int MAX_TCHAT_MESSAGE_HISTORIC = 100;

    /** Délai autorisé pour un joueur pour répondre à un ping en millisecondes */
    static private final long DELAI_MAX_REPONSE_PING = 10000;

    /** Niveau d'escalade de ping maximal autorisé pour un joueur (cad nombre de fois
     * qu'il est autorisé à ne pas répondre à un ping avant d'être déconnecté) */
    static private final int PING_ESCALADE_MAX = 3;

    /** Horodatage du dernier envoi des pings */
    private Date lastPingRequestDate;
    
    /** Le sender s'occupe de l'envoi de messages */
    private ServiceSender serviceSender;

    public final Map<ClientIdentifier, Client> clients = new HashMap<ClientIdentifier, Client>();

    /** Historique des 100 derniers messages de chat. */
    private final List<ChatMessage> tchatMessageHistoric = new ArrayList<ChatMessage>();

    
    /** Spring setter. */
    public void setServiceSender(final ServiceSender serviceSender) {
        this.serviceSender = serviceSender;
    }
    
    
    /** {@inheritDoc} */
    public List<ChatMessage> getTchatMessageHistoric() {
        List<ChatMessage> result;

        synchronized (tchatMessageHistoric) {
            result = tchatMessageHistoric;
        }

        return result;
    }

    /** {@inheritDoc} */
    public void distributeChatMessage(final String clientName, final String message) {
        synchronized (tchatMessageHistoric) {
            // Assure que l'historique ne dépasse pas 100 messages de tchat
            if(tchatMessageHistoric.size() >= MAX_TCHAT_MESSAGE_HISTORIC) {
                tchatMessageHistoric.remove(0);
            }

            // Ajoute le message à l'historique
            final ChatMessage chatMessage = new ChatMessage();
            chatMessage.setClientName(clientName);
            chatMessage.setTime(new Date());
            chatMessage.setMessage(message);

            tchatMessageHistoric.add(chatMessage);
            
            final DistributeChatMessage msg = new DistributeChatMessage();
            msg.setMessage(message);
            msg.setPlayerName(clientName);
            msg.setDestinations(clients.keySet());
            
            try {
                serviceSender.send(msg);
            }
            catch(final IOException exception) {
                LOGGER.error("Erreur envoi", exception);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public ServiceDataOut traite(final ServiceDataIn dataIn) {
        ServiceDataOut dataOut;

        if(dataIn instanceof SignalementData) {
            final SignalementData signalementData = (SignalementData)dataIn;
            Client client = getClient(signalementData);

            if(client == null) {
                client = new Client(signalementData);
                final ClientIdentifier identifier = new ClientIdentifier();
                identifier.setHostAddress(client.getHostAddress());
                identifier.setPort(client.getPort());
                identifier.setClientName(client.getClientName());

                clients.put(identifier, client);

                // Envoi de la liste des joueurs connect�s au chat
                final ListJoueursData listJoueursData = new ListJoueursData(identifier);
                final List<String> joueursNames = new ArrayList<String>();

                for(ClientIdentifier clientId : clients.keySet()) {
                    joueursNames.add(clientId.getClientName());
                }

                listJoueursData.setJoueurs(joueursNames);

                dataOut = listJoueursData;
            }
            else {
                // Client already registered
                dataOut = null;
            }
        }
        else if(dataIn instanceof PingAckData) {
            final PingAckData pingData = (PingAckData)dataIn;
            final Client client = getClient(pingData);

            // Si le client est connecté on enregistre la valeur de son ping
            if(client != null) {
                client.setLastPingValue(pingData.getPingValue());
                client.setLastPingAckReceivedDate(pingData.getAckReceivedAtTime());
            }

            dataOut = null;
        }
        else if(dataIn instanceof ChatMessageData) {
            final ChatMessageData chatMessageData = (ChatMessageData) dataIn;
            Client client = getClient(chatMessageData);
            
            if(client != null) {
                final String message = chatMessageData.getMessage();
                final String clientName = chatMessageData.getClientName();
    
                // Distribut le message de chat aux clients de l'IHM et du jeu
                distributeChatMessage(clientName, message);
                
                // Acknoledge the reception of the chat message from the client
                final ChatMessageAckData ack = new ChatMessageAckData();
                ack.addDestination(client);
                
                dataOut = ack;
            }
            else {
                dataOut = null;
            }
        }
        else {
            LOGGER.error("Instance Data de type inconnu, ce cas ne devrait jamais arriver");
            dataOut = null;
        }

        return dataOut;
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceDataOut> controlle(Object mutex) {
        // On attend un peu histoire de pas saturer le processeur pour rien
        try {
            mutex.wait(1000);
        } catch (InterruptedException e) {
            // Pas de soucis
        }

        List<ServiceDataOut> datasOut = new ArrayList<ServiceDataOut>();   // Données à envoyer
        Date nowDate = new Date();
        long nowTime = nowDate.getTime();

        // Vérification des délais de réponse aux pings des clients
        for(Entry<ClientIdentifier, Client> entry : clients.entrySet()) {
            ClientIdentifier idClient = entry.getKey();
            Client client = entry.getValue();

            // Si on lui a envoyé une requête de ping qui n'a pas encore été traitée
            Date lastPingRequestSentDate = client.getLastPingRequestDate();
            if(lastPingRequestSentDate != null) {
                Date lastPingAckReceivedDate = client.getLastPingAckReceivedDate();

                // S'il n'a pas répondu et qu'on lui a envoyé le ping request depuis trop longtemps
                if(lastPingAckReceivedDate == null &&
                        (nowTime - lastPingRequestSentDate.getTime() >= DELAI_MAX_REPONSE_PING)) {
                    client.incrementePingEscalade();       // Incrémente le niveau d'escalade de ping du client
                    client.setLastPingRequestDate(null);   // Indique qu'on est plus en attente de réponse du ping
                }

                // Si son niveau d'escalade de ping atteind 3 on considère que le joueur n'est plus connecté
                if(client.getPingEscalade() >= PING_ESCALADE_MAX) {
                    deconnecte(idClient, datasOut);
                }
            }

            // Si on est pas en attente de réponse de ping de ce client
            // Et si on a jamais envoyé de ping ou qu'on en a pas envoyé depuis plus de 5 secondes
            // alors on lui en envoie un
            else if(lastPingRequestDate == null || nowTime - lastPingRequestDate.getTime() > 5000) {
                PingRequestData ping = new PingRequestData();
                ping.setIdentifier(idClient);

                datasOut.add(ping);
            }
        }

        return datasOut;
    }

    /**
     * Envoi un message de déconnexion au client et le retire de la liste des joueurs connectés
     * 
     * @param identifier identifiant du client à déconnecter
     * @param datasOut pile d'envoi de messages
     */
    private void deconnecte(ClientIdentifier idClient, List<ServiceDataOut> datasOut) {
        // Suppresion du joueur de la liste des joueurs connecté�s
        clients.remove(idClient);

        // Envoi d'un message au joueur pour lui indiquer qu'il est déconnecté
        datasOut.add(new DeconnexionData(idClient));
    }

    /**
     * Essaie d'identifier un joueur connecté à partir de :
     *     - un nom de joueur
     *     - une ip
     *     - un port
     *     
     * @param identifiable données d'identification
     * @return joueur trouvé ou null
     */
    private Client getClient(final ClientIdentifiable identifiable) {
        final ClientIdentifier identifier = new ClientIdentifier();
        identifier.setHostAddress(identifiable.getHostAddress());
        identifier.setPort(identifiable.getPort());
        identifier.setClientName(identifiable.getClientName());

        Client client = clients.get(identifier);

        return client;
    }

    @Override
    public Map<ClientIdentifier, Client> getClients() {
        return clients;
    }


    @Override
    public void close() {
    }	
}
