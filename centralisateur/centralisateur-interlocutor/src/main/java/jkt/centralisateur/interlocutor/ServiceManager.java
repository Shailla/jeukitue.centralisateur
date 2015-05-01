package jkt.centralisateur.interlocutor;

import java.util.Collection;

import jkt.centralisateur.interlocutor.scoreboard.ScoreboardStateDto;
import jkt.centralisateur.interlocutor.supervision.message.SupervisionMessage;

public interface ServiceManager {
	Collection<SupervisionMessage> getSupervisionMessages();
	ScoreboardStateDto getState();
	
	/**
	 * Efface tous les messages de supervision
	 */
	void clearSupervisionMessages();
	
	/**
	 * Démarre un service identifié par son nom
	 * 
	 * @param serviceName nom du service
	 */
    void startServiceByName(String serviceName);
    
    /**
     * Arrête un service identifié par son nom
     * 
     * @param serviceName nom du service
     */
    void stopServiceByName(String serviceName);
    
    /**
     * Démarre tous les services du manager de services
     */
    void startAllServices();
    
    /**
     * Arrête tous les services du manager de services
     */
    void stopAllServices();
}
