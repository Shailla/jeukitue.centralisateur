package jkt.centralisateur.interlocutor;

import jkt.centralisateur.interlocutor.exception.ServiceStartException;
import jkt.centralisateur.interlocutor.exception.ServiceStopException;
import jkt.centralisateur.interlocutor.scoreboard.State;
import jkt.centralisateur.interlocutor.supervision.SupervisionManager;

import org.apache.log4j.Logger;

/**
 * Un <code>Service</code>  
 * 
 * @author Erwin
 */
public abstract class Service {
    private static final Logger LOGGER = Logger.getLogger(Service.class);
    
    /** Nom identifiant le service */
    private String name;
    
    /** Description du service */
    private String description;
    
    /** Etat du service */
    private State state;
    
    protected SupervisionManager supervisionManager;
    
    
    public State getState() {
        return state;
    }

    /**
     * Démarre les éléments internes au service et alloue leurs ressources.
     * @throws ServiceStartException
     */
    protected abstract void startService() throws ServiceStartException;

    /**
     * Stoppe les éléments internes au service et libère leurs ressources.
     * @throws ServiceStartException
     */
    protected abstract void stopService() throws ServiceStopException;
    
    /**
     * Démarre le service et lui alloue les ressources nécessaires à son fonctionnement.
     * 
     * @throws ServiceStartException
     */
    public void start() throws ServiceStartException {
        if(state == null || State.STOPPED.equals(state)) {
            // Le service est en cours de démarrage
            state = State.STARTING;
            
            try {
                startService();
                
                // Le service est démarré
                state = State.STARTED;
            }
            catch(final ServiceStartException exception) {
                LOGGER.error("Service start exception", exception);
                throw exception;
            }
        }
        else {
            LOGGER.error("Cannot start an not stopped service");
        }
    }
    
    /**
     * Arrête le service et libère toutes ses ressources.
     */
    public void stop() throws ServiceStopException {
        // Le service est en cours d'arrêt
        state = State.STOPPING;

        try {
            stopService();
            
            // Le service est arrêté
            state = State.STOPPED;
        }
        catch (final ServiceStopException exception) {
            LOGGER.error("Service stop exception", exception);
            throw exception;
        }
    }

    /** Spring setter. */
    public void setSupervisionManager(final SupervisionManager supervisionManager) {
        this.supervisionManager = supervisionManager;
    }

    /** Spring setter. */
    public void setName(final String name) {
        this.name = name;
    }

    /** Spring setter. */
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getName() {
        return name;
    }
}
