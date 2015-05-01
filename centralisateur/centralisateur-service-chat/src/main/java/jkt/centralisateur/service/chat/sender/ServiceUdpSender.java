package jkt.centralisateur.service.chat.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

import jkt.centralisateur.interlocutor.data.ClientIdentifier;
import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;
import jkt.centralisateur.interlocutor.sender.ServiceSender;
import jkt.centralisateur.service.chat.converter.UdpDataOutTranslator;
import jkt.centralisateur.service.chat.data.out.DeconnexionData;
import jkt.centralisateur.service.chat.data.out.DistributeChatMessage;
import jkt.centralisateur.service.chat.data.out.ListJoueursData;
import jkt.centralisateur.service.chat.data.out.PingRequestData;
import jkt.centralisateur.service.chat.listener.ServiceUdpListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceUdpSender implements ServiceSender {
    
    static private Logger LOGGER = LoggerFactory.getLogger(ServiceUdpSender.class);
    
	private UdpDataOutTranslator udpDataOutTranslator;
	
	public void close() {
	}
	
    public void send(final ServiceDataOut dataOut) throws IOException {
	    if(dataOut instanceof PingRequestData) {
	        send((PingRequestData)dataOut);
	    }
	    else if(dataOut instanceof DistributeChatMessage) {
	        send((DistributeChatMessage)dataOut);
	    }
        else if(dataOut instanceof ListJoueursData) {
            send((ListJoueursData)dataOut);
        }
	    else if(dataOut instanceof DeconnexionData) {
	        send((DeconnexionData)dataOut);
	    }
	    else {
	        LOGGER.error("Data de type inconnu : '{}'", dataOut.getClass().getName());
	    }
	}
	
    private void send(DeconnexionData dataOut) throws IOException {
        final DatagramPacket packet = udpDataOutTranslator.convert(dataOut);
        send(dataOut.getIdentifier(), packet);
    }

    private void send(final ListJoueursData dataOut) throws IOException {
        final DatagramPacket packet = udpDataOutTranslator.convert(dataOut);
        send(dataOut.getIdentifier(), packet);
    }
    
	private void send(final PingRequestData dataOut) throws IOException {
	    final DatagramPacket packet = udpDataOutTranslator.convert(dataOut);
	    send(dataOut.getIdentifier(), packet);
	}
	
	private void send(final DistributeChatMessage dataOut) throws IOException {
	    final DatagramPacket packet = udpDataOutTranslator.convert(dataOut);
	    
        final Set<ClientIdentifier> identifiers = dataOut.getDestinations();
        
        for(final ClientIdentifier identifier : identifiers) {
	        send(identifier, packet);
        }
	}
	
	private void send(final ClientIdentifier identifier, final DatagramPacket packet) throws IOException {
		final String ip = identifier.getHostAddress();
		final InetAddress iaddr = InetAddress.getByName(ip);
		packet.setAddress(iaddr);
		packet.setPort(identifier.getPort());
		
		ServiceUdpListener.socket.send(packet);		
	}
	
	public void setUdpDataOutTranslator(UdpDataOutTranslator udpDataOutTranslator) {
	    this.udpDataOutTranslator = udpDataOutTranslator;
	}
}
