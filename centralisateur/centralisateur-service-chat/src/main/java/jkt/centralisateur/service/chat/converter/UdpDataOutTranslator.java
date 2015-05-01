package jkt.centralisateur.service.chat.converter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Date;
import java.util.List;

import jkt.centralisateur.interlocutor.data.ClientIdentifier;
import jkt.centralisateur.service.chat.data.out.DeconnexionData;
import jkt.centralisateur.service.chat.data.out.DistributeChatMessage;
import jkt.centralisateur.service.chat.data.out.ListJoueursData;
import jkt.centralisateur.service.chat.data.out.PingRequestData;

public class UdpDataOutTranslator {
    // Messages descendant du serveur centralisateur vers un moteur (sortants)
    /** Demande d'un ping au client */
    public static final int CODE_D_PingRequest            = 100;
    /** Demande d'un ping au client */
    public static final int CODE_D_ListJoueurs            = 101;
    /** Transmission d'un message de chat � tous les joueurs */
    public static final int CODE_D_ChatMessage            = 102;
    /** Demande d'un ping au client */
    public static final int CODE_D_Deconnexion            = 103;
    
    public DatagramPacket convert(ListJoueursData dataOut) throws IOException {
        List<String> playersNames = dataOut.getJoueurs();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        // Commande code Ping request
        dos.writeInt(UdpDataOutTranslator.CODE_D_ListJoueurs);

        // Nombre de noms de joueurs
        dos.writeInt(playersNames.size());
        
        // Noms des joueurs
        for(String playerName : playersNames) {
            // Nom du joueur
            byte[] bytesPlayerName = playerName.getBytes();
            dos.writeInt(bytesPlayerName.length);       // Taille du nom
            dos.write(bytesPlayerName);                 // Nom proprement dit
        }
        
        dos.flush();
        
        return new DatagramPacket(bos.toByteArray(), bos.size());
    }
    
    public DatagramPacket convert(DistributeChatMessage dataOut) throws IOException {
        String message = dataOut.getMessage();
        String playerName = dataOut.getPlayerName();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        // Commande code Ping request
        dos.writeInt(UdpDataOutTranslator.CODE_D_ChatMessage);

        // Nom du joueur ayant envoyé le message de chat
        byte[] bytesPlayerName = playerName.getBytes();
        dos.writeInt(bytesPlayerName.length);
        dos.write(bytesPlayerName);
        
        // Contenu du message
        byte[] bytesMessage = message.getBytes();
        dos.writeInt(bytesMessage.length);
        dos.write(bytesMessage);
        
        dos.flush();
        
        return new DatagramPacket(bos.toByteArray(), bos.size());
    }
    
    public DatagramPacket convert(PingRequestData dataOut) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        // Commande code Ping request
        dos.writeInt(UdpDataOutTranslator.CODE_D_PingRequest);
        
        // Nom du client
        ClientIdentifier clientId = dataOut.getIdentifier();
        String playerName = clientId.getClientName();
        byte[] bytes = playerName.getBytes();
        dos.writeInt(bytes.length);
        dos.write(bytes);
        
        // Valeur du time à l'envoi
        dos.writeLong(new Date().getTime());
        
        dos.flush();
        
        return new DatagramPacket(bos.toByteArray(), bos.size());
    }

    public DatagramPacket convert(DeconnexionData dataOut) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        // Commande code Ping request
        dos.writeInt(UdpDataOutTranslator.CODE_D_PingRequest);
                
        dos.flush();
        
        return new DatagramPacket(bos.toByteArray(), bos.size());
    }
}
