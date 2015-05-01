package jkt.centralisateur.interlocutor.listener;

import jkt.centralisateur.interlocutor.converter.DataInTranslator;
import jkt.centralisateur.interlocutor.data.in.ServiceDataIn;
import jkt.centralisateur.interlocutor.exception.ServiceStartException;

/**
 * <code>ServiceListener</code> écoute un vecteur de données pour un service (UDP par exemple),
 * lorsqu'une donnée arrive elle est retransmise au service qui la traite avec un ServiceController
 * 
 * @author Erwin
 */
public interface ServiceListener {
    
    /**
     * Converisseur de données depuis le flux (UDP par exemple) vers le type <code>ServiceData</code>
     * @param dataTranslator
     */
	void setDataTranslator(DataInTranslator dataTranslator);
	
	/**
	 * Nom du listener
	 * 
	 * @param name nom du listener
	 */
	void setName(String name);

	/**
     * Nom du listener.
     * 
     * @return nom du listener
     */
    String getName();
    
    /**
     * Initialisation du listener
     */
    void start() throws ServiceStartException;
    
    /**
     * Libère toutes les ressources du listener (sockets par exemple)
     */
    void close();
    
    /**
     * Attend la réception d'une donnée
     * 
     * @return donn�e re�ue
     */
    ServiceDataIn receive();
}
