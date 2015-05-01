package jkt.centralisateur.service.chat.converter;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Date;

import jkt.centralisateur.interlocutor.converter.DataInTranslator;
import jkt.centralisateur.interlocutor.data.ClientIdentifiable;
import jkt.centralisateur.interlocutor.data.in.ServiceDataIn;
import jkt.centralisateur.interlocutor.exception.CorruptedDataException;
import jkt.centralisateur.interlocutor.exception.UnknownDataException;
import jkt.centralisateur.service.chat.data.in.ChatMessageData;
import jkt.centralisateur.service.chat.data.in.PingAckData;
import jkt.centralisateur.service.chat.data.in.SignalementData;

public class UdpDataInTranslator implements DataInTranslator {
	
    // Messages montant d'un moteur vers le serveur centralisateur (entrants)
    /** When a new client is signaling himself */
    public static final int CODE_U_Signalement            = 200;
    /** Réception d'une réponse à une demande de ping (CODE_PingRequest) */
    public static final int CODE_U_PingAck                = 201;
    /** Réception d'un message de chat redistribué par le serveur centralisateur */
    public static final int CODE_U_ChatMessage            = 203;
	
	public ServiceDataIn convert(final Object var) throws CorruptedDataException, UnknownDataException {
		ServiceDataIn data = null;
		try {
			if(var instanceof DatagramPacket) {
			    final DatagramPacket packet = (DatagramPacket)var;
				final byte[] bytes = packet.getData();
				
				final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes)); 
				int code;
				
				try {
					code = dis.readInt();
				} catch (IOException exception) {
					CorruptedDataException ex = new CorruptedDataException();
					throw ex;
				}
				
				if(code == CODE_U_Signalement) {
					data = decodeSignalement(packet, dis);
					
				} else if(code == CODE_U_PingAck) {
					data = decodePingAck(packet, dis);
				
				} else if(code == CODE_U_ChatMessage) {
				    data = decodeChatMessage(packet, dis);
				
				} else {
					UnknownDataException ex = new UnknownDataException(code);
					throw ex;			
				}
			}
		} catch(IOException exception) {
			CorruptedDataException ex = new CorruptedDataException();
			throw ex;
		}
		
		return data;
	}
	
	/**
	 * Convert a Signalement stream to a <code>SignalementData</code>
	 * instance
	 *  
	 * @param packet UDP packet
	 * @param dis byte stream
	 * @return instance of <code>SignalementData</code>
	 * @throws IOException if the stream is not readeable
	 */
	private ServiceDataIn decodeSignalement(final DatagramPacket packet, final DataInputStream dis) throws IOException {
		SignalementData data = new SignalementData();
		
		readClientIdentifier(data, packet, dis);
		
		return data;
	}
	
	/**
	 * Convert a Ping Acknoledge stream to a <code>PingData</code>
	 * instance
	 *  
	 * @param packet UDP packet
	 * @param dis byte stream
	 * @return instance of <code>PingData</code>
	 * @throws IOException if the stream is not readeable
	 */
	private ServiceDataIn decodePingAck(final DatagramPacket packet, final DataInputStream dis) throws IOException {
		PingAckData data = new PingAckData();
		
		readClientIdentifier(data, packet, dis);
		
		// Ping time
		long sendedTime = dis.readLong();
		Date now = new Date();
		long returnTime = now.getTime();
		
		data.setRequestSentAtTime(new Date(sendedTime));
		data.setAckReceivedAtTime(now);
		data.setPingValue(returnTime - sendedTime);
		
		return data;
	}
	
	   /**
     * Convert a Chat message stream to a <code>ChatMessageData</code>
     * instance
     *  
     * @param packet UDP packet
     * @param dis byte stream
     * @return instance of <code>PingData</code>
     * @throws IOException if the stream is not readeable
     */
    private ServiceDataIn decodeChatMessage(final DatagramPacket packet, final DataInputStream dis) throws IOException {
        ChatMessageData data = new ChatMessageData();
        
        readClientIdentifier(data, packet, dis);
        
        // Message
        int messageLength = dis.readInt();
        byte[] messageBytes = new byte[messageLength];
        dis.read(messageBytes, 0, messageLength);
        String message = new String(messageBytes);
        data.setMessage(message);
        
        return data;
    }
	
	private void readClientIdentifier(final ClientIdentifiable identifiable,
								 final DatagramPacket packet,
								 final DataInputStream dis) throws IOException {
		// Lit l'identification de l'envoyeur
		InetAddress adress = packet.getAddress();
		identifiable.setHostAddress(adress.getHostAddress());
		identifiable.setPort(packet.getPort());
		
		// Lit le nom du client
		int tailleClientName = dis.readInt();
		byte[] clientNameBytes = new byte[tailleClientName];
		dis.read(clientNameBytes, 0, tailleClientName);
		String clientName = new String(clientNameBytes);
		identifiable.setClientName(clientName);
	}
}
