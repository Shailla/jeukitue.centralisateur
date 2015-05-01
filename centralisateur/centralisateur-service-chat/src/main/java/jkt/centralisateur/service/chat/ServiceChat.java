package jkt.centralisateur.service.chat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jkt.centralisateur.interlocutor.Service;
import jkt.centralisateur.interlocutor.data.ClientIdentifier;
import jkt.centralisateur.interlocutor.data.in.ServiceDataIn;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;
import jkt.centralisateur.interlocutor.exception.ServiceStartException;
import jkt.centralisateur.interlocutor.listener.ServiceListener;
import jkt.centralisateur.interlocutor.scoreboard.Client;
import jkt.centralisateur.interlocutor.scoreboard.State;
import jkt.centralisateur.interlocutor.sender.ServiceSender;
import jkt.centralisateur.service.chat.manager.ServiceTChatController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>ServiceChat</code> est un service fonctionnant de façon autonome dont le
 * but est de recevoir des messages par un flux (UDP par exemple), de les traiter et
 * d'y répondre par un autre flux UDP.
 * 
 * Il se compose :
 *  - d'un <code>ServiceListener</code> qui reçoit les messages entrants
 *  - d'un <code>ServiceController</code> qui traite les messages mais est threadé et
 *          est donc aussi capable de prendre des initiatives (envoi de message pas exemple)
 *  - d'un <code>ServiceSender</code> qui est piloté par le ServiceController pour l'envoi de messages
 */
public class ServiceChat extends Service {
    
    static private Logger LOGGER = LoggerFactory.getLogger(ServiceChat.class);

    /** Le listener écoute l'arrivée de messages */
    private ServiceListener serviceListener;
    
    /** Le serviceController traite les messages entrants ou prend des initiatives */
    protected ServiceTChatController serviceController;
    
    /** Le sender s'occupe de l'envoi de messages */
    private ServiceSender serviceSender;

    /** Thread du listener */
    private Thread listenerThread;
    
    /** Thread du controller */
    private Thread controllerThread;
    
    public Map<ClientIdentifier, Client> getClients() {
        return serviceController.getClients();
    }
    
    @Override
    protected void startService() throws ServiceStartException {
        // Initialisation du listener du service
        serviceListener.start();
        
        // Démarrage du controlleur du service
        listenerThread = new Thread(new ListenerThread());
        listenerThread.start();
        
        controllerThread = new Thread(new ControllerThread());
        controllerThread.start();
    }
    
    @Override
    protected void stopService() {
        // Initialisation du listener du service
        serviceListener.close();
        serviceController.close();
        serviceSender.close();
    }
    
    
    private class ListenerThread implements Runnable { 
        public synchronized void run() {
            while(State.STARTED.equals(getState()) || State.STARTING.equals(getState())) {
                if(State.STARTED.equals(getState())) {
                    final ServiceDataIn dataIn = serviceListener.receive();
                    
                    if(dataIn != null) {
                        final ServiceDataOut dataOut = serviceController.traite(dataIn);
                        
                        if(dataOut != null) {
                            try {
                                serviceSender.send(dataOut);
                            }
                            catch(final IOException exception) {
                                LOGGER.error("Exception", exception);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private class ControllerThread implements Runnable {
        private Object mutex = "mutexControllerThread";
        
        public void run() {
            synchronized (mutex) {
                while(State.STARTED.equals(getState()) || State.STARTING.equals(getState())) {
                    if(State.STARTED.equals(getState())) {
                        final List<ServiceDataOut> datasOut = serviceController.controlle(mutex);
                        
                        for(final ServiceDataOut dataOut : datasOut) {
                            try {
                                serviceSender.send(dataOut);
                            }
                            catch(final IOException exception) {
                                LOGGER.error("Exception", exception);
                            }
                        }
                    }
                }                
            }
        }
    }
    
    /** Spring setter. */
    public void setServiceListener(final ServiceListener serviceListener) {
        this.serviceListener = serviceListener;
    }
    
    /** Spring setter. */
    public void setServiceController(final ServiceTChatController serviceController) {
        this.serviceController = serviceController;
    }
    
    /** Spring setter. */
    public void setServiceSender(final ServiceSender serviceSender) {
        this.serviceSender = serviceSender;
    }
}
