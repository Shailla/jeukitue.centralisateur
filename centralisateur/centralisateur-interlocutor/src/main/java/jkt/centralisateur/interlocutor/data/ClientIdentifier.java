package jkt.centralisateur.interlocutor.data;

public class ClientIdentifier implements ClientIdentifiable, Comparable<ClientIdentifier> {
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

	@Override
	public int compareTo(ClientIdentifier identifier) {
		int result;
		
		if(hostAddress.compareTo(identifier.getHostAddress()) < 0) {
			result = -1;
		} else if(hostAddress.compareTo(identifier.getHostAddress()) > 0) {
			result = +1;
		} else {
			if(port < this.port) {
				result = -1;	
			} else if(port > this.port) {
				result = +1;
			} else {
				result = clientName.compareTo(identifier.clientName);
			}
		}
		
		return result;
	}

	@Override
	public boolean equals(Object object) {
		boolean result;
		
		if(object instanceof ClientIdentifier) {
			ClientIdentifier identifier = (ClientIdentifier) object;
			
			if(port==identifier.port
					&& hostAddress.equals(identifier.hostAddress)
					&& clientName.equals(identifier.clientName)) {
				result = true;
			} else {
				result = false;
			}
		}
		else {
			result = false;
		}
		
		return result;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return "[" + clientName + "@" + hostAddress + ":" + port + "]";
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
