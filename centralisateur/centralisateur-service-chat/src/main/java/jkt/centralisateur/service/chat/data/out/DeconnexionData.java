package jkt.centralisateur.service.chat.data.out;

import jkt.centralisateur.interlocutor.data.ClientIdentifier;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;

/**
 * Data pour indiquer au joueur qu'il a été déconnecté
 * 
 * @author Erwin
 */
public class DeconnexionData implements ServiceDataOut {
    ClientIdentifier identifier;

    public DeconnexionData(ClientIdentifier identifier) {
        this.identifier = identifier;
    }
    
    public ClientIdentifier getIdentifier() {
        return identifier;
    }
}
