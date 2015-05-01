package jkt.centralisateur.interlocutor.supervision.message;

public class SupervisionMessages {
	static public final String LISTENING_ERROR =           "jkt.centralisateur.interlocutor.supervision.messages.listeningError";
	public static final String CORRUPTED_DATA_ERROR =      "jkt.centralisateur.interlocutor.supervision.messages.corruptedDataError";
	public static final String UNKNOWN_DATA_ERROR =        "jkt.centralisateur.interlocutor.supervision.messages.unknownDataError";
	public static final String LISTENER_CONNEXION_ERROR =  "jkt.centralisateur.interlocutor.supervision.messages.listenerConnexionError";
	
	public static final String START_SERVICE =             "jkt.centralisateur.interlocutor.supervision.messages.service.start";
	public static final String STOP_SERVICE =              "jkt.centralisateur.interlocutor.supervision.messages.service.stop";
	
	public static final String START_LISTENER =            "jkt.centralisateur.interlocutor.supervision.messages.listenerStart";
	public static final String STOP_LISTENER =             "jkt.centralisateur.interlocutor.supervision.messages.listenerStop";
	public static final String PING_ERROR =                "jkt.centralisateur.interlocutor.supervision.messages.pingError";
}
