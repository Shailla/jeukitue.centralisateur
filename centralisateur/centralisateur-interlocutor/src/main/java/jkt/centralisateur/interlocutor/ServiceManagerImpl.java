package jkt.centralisateur.interlocutor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jkt.centralisateur.interlocutor.exception.ServiceStartException;
import jkt.centralisateur.interlocutor.exception.ServiceStopException;
import jkt.centralisateur.interlocutor.scoreboard.ScoreboardStateDto;
import jkt.centralisateur.interlocutor.scoreboard.State;
import jkt.centralisateur.interlocutor.supervision.SupervisionManager;
import jkt.centralisateur.interlocutor.supervision.message.SupervisionMessage;
import jkt.centralisateur.interlocutor.supervision.message.SupervisionMessages;

public class ServiceManagerImpl implements ServiceManager {
    static private Logger LOGGER = LoggerFactory.getLogger(ServiceManagerImpl.class);

    private final Map<String, Service> services = new HashMap<String, Service>();
    private SupervisionManager supervisionManager;
    private State state = State.STOPPED;

    @Override
    public void startServiceByName(final String serviceName) {
        final Service service = services.get(serviceName);

        if(service != null) {
            startService(service);
        }
        else {
            LOGGER.error("Service inconnu : '{}'", serviceName);
        }
    }

    @Override
    public void stopServiceByName(final String serviceName) {
        final Service service = services.get(serviceName);

        if(service != null) {
            stopService(service);
        }
        else {
            LOGGER.error("Service inconnu : {}", serviceName);
        }
    }

    private void startService(final Service service) {
        if(service != null) {
            final String serviceName = service.getName();

            try {
                service.start();
                supervisionManager.addInfo(SupervisionMessages.START_SERVICE, serviceName);
            }
            catch(final ServiceStartException startException) {
                LOGGER.error("Démarrage du service [{}] impossible", serviceName);
                LOGGER.error("Exception", startException);
                stopService(service);
            }
        }
    }
    
    private void stopService(final Service service) {
        if(service != null) {
            final String serviceName = service.getName();

            try {
                service.stop();
                supervisionManager.addInfo(SupervisionMessages.STOP_SERVICE, serviceName);
            }
            catch (final ServiceStopException exception) {
                LOGGER.error("Arrêt du service [{}] impossible", serviceName);
                LOGGER.error("Exception", exception);
            }
        }
    }

    @Override
    public ScoreboardStateDto getState() {
        final ScoreboardStateDto stateReport = new ScoreboardStateDto();
        stateReport.setState(state);

        for(final Entry<String, Service> service : services.entrySet()) {
            final String serviceName = service.getKey();
            final State serviceState = service.getValue().getState();
            stateReport.addServiceState(serviceName, serviceState);
        }

        return stateReport;
    }

    @Override
    public void startAllServices() {
        for(final String serviceName : services.keySet()) {
            startServiceByName(serviceName);
        }
    }

    @Override
    public void stopAllServices() {
        for(final String serviceName : services.keySet()) {
            stopServiceByName(serviceName);
        }
    }

    public Collection<SupervisionMessage> getSupervisionMessages() {
        return supervisionManager.getLogs();
    }

    public void clearSupervisionMessages() {
        supervisionManager.clearLogs();
    }

    /** Spring setter. */
    public void setSupervisionManager(SupervisionManager supervisionManager) {
        this.supervisionManager = supervisionManager;
    }

    /** Spring setter. */
    public void setServices(final List<Service> services) {
        for(final Service service : services) {
            this.services.put(service.getName(), service);
        }
    }
}
