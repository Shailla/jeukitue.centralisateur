package jkt.centralisateur.service.chat.data.out;

import java.util.List;

import jkt.centralisateur.interlocutor.data.ClientIdentifier;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;

public class ListJoueursData implements ServiceDataOut {
    private List<String> joueurs;
    private ClientIdentifier identifier;

    public ListJoueursData(ClientIdentifier identifier) {
        this.identifier = identifier;
    }
    
    public List<String> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<String> joueurs) {
        this.joueurs = joueurs;
    }
    
    public ClientIdentifier getIdentifier() {
        return identifier;
    }
}
