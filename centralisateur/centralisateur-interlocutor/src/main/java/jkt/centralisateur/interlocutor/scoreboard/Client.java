package jkt.centralisateur.interlocutor.scoreboard;

import java.util.Date;

import jkt.centralisateur.interlocutor.data.ClientIdentifiable;


public class Client implements ClientIdentifiable {
	/** IP du joueur */
    private String hostAddress;
	
	/** Port du joueur */
	private int port;
	
	/** Nom du joueur */
	private String clientName;
	
	/** Horodatage de la dernière requête de ping envoyée au joueur */
	private Date lastPingRequestDate;

	/** Horodatage du la dernière réponse à un ping de ce client ou null si le dernier ping a été traité */
	private Date lastPingAckReceivedAtTime;

    /** Valeur du dernier ping mesuré */
    private long lastPingValue;

    /**
     * Le niveau d'escalade de ping d'un joueur est le nombre de fois qu'il a dépassé le délai
     * autorisé pour répondre à un ping. Quand celui-ci atteind une certaine valeur on considère
     * que le joueur n'est plus connecté.
     */
    private int pingEscalade;
	
	public Client(ClientIdentifiable identifiable) {
		hostAddress = identifiable.getHostAddress();
		port = identifiable.getPort();
		clientName = identifiable.getClientName();
	}

	public Date getLastPingRequestDate() {
	    return lastPingRequestDate;
	}


	public void setLastPingRequestDate(Date lastPingRequestDate) {
	    this.lastPingRequestDate = lastPingRequestDate;
	}
	
	public long getLastPingValue() {
		return lastPingValue;
	}

	public void setLastPingValue(long lastPingValue) {
		this.lastPingValue = lastPingValue;
	}

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

    public Date getLastPingAckReceivedDate() {
        return lastPingAckReceivedAtTime;
    }
	
    public void setLastPingAckReceivedDate(Date date) {
        this.lastPingAckReceivedAtTime = date;
    }

    /**
     * Augment de 1 le niveau d'escalade de ping du joueur
     */
    public void incrementePingEscalade() {
        pingEscalade++;
    }
    
    /**
     * Remet le niveau d'escalade de ping du joueur à 0
     */
    public void clearPingEscalade() {
        pingEscalade = 0;
    }
    
    /**
     * Retourne le niveau d'escalade de ping du joueur
     * @return
     */
    public int getPingEscalade() {
        return pingEscalade;
    }
}
