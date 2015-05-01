package jkt.centralisateur.service.chat.data.out;

import java.util.HashSet;
import java.util.Set;

import jkt.centralisateur.interlocutor.data.ClientIdentifiable;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;

public class ChatMessageAckData implements ServiceDataOut {
    /** Nom du joueur qui a envoyé le message */
    private String playerName;
    
    /** Message */
    private String message;

    /** Liste des clients destinations */
    private final Set<ClientIdentifiable> destinations = new HashSet<ClientIdentifiable>();
    
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Set<ClientIdentifiable> getDestinations() {
        return destinations;
    }

    public void addDestination(final ClientIdentifiable destination) {
        this.destinations.add(destination);
    }
    
    public void addDestinations(final Set<ClientIdentifiable> destinations) {
        this.destinations.addAll(destinations);
    }
}
