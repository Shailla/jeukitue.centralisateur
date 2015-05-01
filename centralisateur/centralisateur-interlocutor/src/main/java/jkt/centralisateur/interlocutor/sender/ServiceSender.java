package jkt.centralisateur.interlocutor.sender;

import java.io.IOException;

import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;

public interface ServiceSender {
    void send(ServiceDataOut dataOut) throws IOException;
    void close();
}
