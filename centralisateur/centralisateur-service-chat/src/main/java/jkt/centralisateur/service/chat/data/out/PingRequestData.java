package jkt.centralisateur.service.chat.data.out;

import jkt.centralisateur.interlocutor.data.ClientIdentifier;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;

public class PingRequestData implements ServiceDataOut {
    ClientIdentifier identifier;

    public ClientIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(ClientIdentifier identifier) {
        this.identifier = identifier;
    }
}
