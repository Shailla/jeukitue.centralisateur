package jkt.centralisateur.service.chat.listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import jkt.centralisateur.interlocutor.converter.DataInTranslator;
import jkt.centralisateur.interlocutor.data.in.ServiceDataIn;
import jkt.centralisateur.interlocutor.exception.CorruptedDataException;
import jkt.centralisateur.interlocutor.exception.ServiceStartException;
import jkt.centralisateur.interlocutor.exception.UnknownDataException;
import jkt.centralisateur.interlocutor.listener.ServiceListener;
import jkt.centralisateur.interlocutor.supervision.SupervisionManager;
import jkt.centralisateur.interlocutor.supervision.message.SupervisionMessages;

import org.apache.log4j.Logger;

public class ServiceUdpListener implements ServiceListener {
	static final Logger log = Logger.getLogger(ServiceUdpListener.class);
	
	/** Port d'écoute UDP */
	private int port;
	private DataInTranslator dataTranslator;
	private SupervisionManager supervisionManager;
	/** Socket d'écoute UDP */
	static public DatagramSocket socket;
	private String name;

    /**
	 * Etablie la connexion sur le port d'écoute et démarre l'écoute
	 */
	public void start() throws ServiceStartException {				
		try {
			socket = new DatagramSocket(port);
			socket.setSoTimeout(1000);
		}
		catch (final SocketException exception) {
			log.warn("Unable to connect", exception);
			throw new ServiceStartException(exception);
		}
		
		supervisionManager.addInfo(SupervisionMessages.START_LISTENER, name);
	}

	/**
	 * Met fin à l'écoute et libère les ressources
	 */
	public void close() {
		// La fermeture de la socket a pour cons�quence l'arrêt du thread d'écoute
		if(socket != null) {
			socket.close();
		}
		
		supervisionManager.addInfo(SupervisionMessages.STOP_LISTENER, name);
	}
	
	public ServiceDataIn receive() {
		final DatagramPacket packet = new DatagramPacket(new byte[512],512);
		ServiceDataIn dataIn = null;
		
		try {
			socket.receive(packet);

			// Conversion du paquet UDP en données JKT
			dataIn = dataTranslator.convert(packet);
			
		} catch(SocketTimeoutException exception) {
			// Do nothing, temporisation is normal
			
		} catch (IOException exception) {
			log.error("exception", exception);
			supervisionManager.addError(SupervisionMessages.LISTENING_ERROR, name);
			
		} catch (CorruptedDataException exception) {
			log.error("exception", exception);
			supervisionManager.addError(SupervisionMessages.CORRUPTED_DATA_ERROR, name);
			
		} catch (UnknownDataException exception) {
			log.error("exception", exception);
			supervisionManager.addError(SupervisionMessages.UNKNOWN_DATA_ERROR, name, exception.getUnknownCode());
		}
		
		return dataIn;
	}
	  	
	public String getName() {
		return name;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public void setDataTranslator(DataInTranslator dataTranslator) {
		this.dataTranslator = dataTranslator;
	}

	public void setSupervisionManager(SupervisionManager supervisionManager) {
		this.supervisionManager = supervisionManager;
	}

	public void setName(String name) {
		this.name = name;
	}
}
