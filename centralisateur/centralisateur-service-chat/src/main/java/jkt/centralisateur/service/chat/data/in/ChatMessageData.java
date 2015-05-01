package jkt.centralisateur.service.chat.data.in;

import jkt.centralisateur.interlocutor.data.in.ServiceUdpDataIn;


public class ChatMessageData extends ServiceUdpDataIn {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
