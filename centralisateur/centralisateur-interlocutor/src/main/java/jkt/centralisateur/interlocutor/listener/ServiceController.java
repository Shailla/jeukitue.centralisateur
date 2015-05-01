package jkt.centralisateur.interlocutor.listener;

import java.util.List;

import jkt.centralisateur.interlocutor.data.in.ServiceDataIn;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;

public interface ServiceController {
	/**
	 * Traite les données reçues.
	 * 
	 * @param data données reçue
	 * @return données à envoyer
	 */
    ServiceDataOut traite(ServiceDataIn data);
    
    /**
     * Effectue des calculs, des contrôles ou des actions périodiques
     * 
     * @param mutex mutex de synchronisation
     * @return données à envoyer
     */
    List<ServiceDataOut> controlle(Object mutex);
    
    void close();
}
