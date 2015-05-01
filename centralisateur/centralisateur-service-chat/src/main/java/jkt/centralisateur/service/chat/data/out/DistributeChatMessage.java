package jkt.centralisateur.service.chat.data.out;

import java.util.Set;

import jkt.centralisateur.interlocutor.data.ClientIdentifier;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;

public class DistributeChatMessage implements ServiceDataOut {
    /** Nom du joueur qui a envoyé le message */
    String playerName;
    
    /** Message */
    String message;

    /** Liste des clients destinations */
    Set<ClientIdentifier> destinations;
    
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<ClientIdentifier> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<ClientIdentifier> destinations) {
        this.destinations = destinations;
    }
}
