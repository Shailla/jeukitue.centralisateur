package jkt.centralisateur.interlocutor.data.in;

import jkt.centralisateur.interlocutor.data.ClientIdentifiable;

public class ServiceUdpDataIn implements ServiceDataIn, ClientIdentifiable {
    private String hostAddress;
    private int port;
    private String clientName;
    
    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
