package jkt.centralisateur.interlocutor.data;

public interface ClientIdentifiable {
	String getHostAddress();
	void setHostAddress(String hostAddress);
	int getPort();
	void setPort(int port);
	void setClientName(String clientName);
	String getClientName();
}
