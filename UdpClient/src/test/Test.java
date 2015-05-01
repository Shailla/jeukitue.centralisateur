package test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test extends JFrame implements ActionListener, WindowListener, Runnable {
	private static final long serialVersionUID = 1L;
	
	private JButton signalementButton = new JButton("signalement");
	
	private DatagramSocket socket;

	static public void main(final String argv[]) {
		new Test();
	}
	
	private Test() {
		signalementButton.addActionListener(this);
		
		this.getContentPane().add(signalementButton);
		
		GridLayout layout = new GridLayout(3, 3);
		setLayout(layout);
		
		try {
			socket = new DatagramSocket(9659);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		Thread thread = new Thread(this);
		thread.start();
		
		addWindowListener(this);
		pack();
		setSize(200, 200);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == signalementButton) {
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				
				String clientName = "Albator";
				dos.writeInt(100);
				dos.writeInt(clientName.length());
				dos.writeBytes(clientName);
				
				dos.flush();

				DatagramPacket packet = new DatagramPacket(bos.toByteArray(), bos.size());
				InetAddress iaddr = InetAddress.getLocalHost();
				packet.setAddress(iaddr);
				packet.setPort(4634);
				
				socket.send(packet);
				
				System.out.println("Signalement envoyé");
				
				socket.disconnect();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		while(!socket.isClosed()) {
			System.out.println("Listening");
			
			DatagramPacket packet = new DatagramPacket(new byte[512],512);
			
			try {
				socket.receive(packet);
				System.out.println("Data received");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			byte[] bytes = packet.getData();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			DataInputStream dis = new DataInputStream(bais);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			
			try {
				// Code commande
				int commande = dis.readInt();
				dos.writeInt(105);
				
				// Nom client
				int tailleClientName = dis.readInt();
				byte[] clientNameBytes = new byte[tailleClientName];
				dos.writeInt(tailleClientName);
				dis.read(clientNameBytes, 0, tailleClientName);
				dos.write(clientNameBytes, 0, tailleClientName);
				
				// Time d'envoi du ping
				long time = dis.readLong();
				dos.writeLong(time);
				dos.flush();
				
				System.out.println("Commande code=" + commande + ", Time="+time);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			
			try {
				DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(), baos.size());
				packetOut.setAddress(packet.getAddress());
				packetOut.setPort(packet.getPort());
				
				socket.send(packetOut);
				
				System.out.println("Mirroir");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
