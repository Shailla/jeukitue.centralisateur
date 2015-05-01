package jkt.centralisateur.service.tcp;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import jkt.centralisateur.common.exception.DownloadFileNotExistsException;
import jkt.centralisateur.interlocutor.Service;
import jkt.centralisateur.interlocutor.exception.ServiceStartException;
import jkt.centralisateur.interlocutor.exception.ServiceStopException;
import jkt.centralisateur.interlocutor.scoreboard.State;
import jkt.centralisateur.storage.model.Download;
import jkt.centralisateur.storage.service.DownloadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service permettant aux joueurs de télécharger par TCP des fichiers fournis par le serveur.
 * Exemple : Mise à jour du jeu, de nouvelles map, ...
 *  
 * @author Erwin
 */
public class ServiceTcpExchange extends Service {
    
    static private Logger LOGGER = LoggerFactory.getLogger(ServiceTcpExchange.class);

    /** TCP port for server listening. */
    private final int serverTcpPort;
    
    /** Server listening socket. */
    private ServerSocket serverSocket;
    
    /** Server listening thread. */
    private Thread serverListenerThread;
    
    private DownloadService downloadService;
    
    enum TcpMessage {
        // Message codes received by the Centralisateur
        LIST_FILES(1, "Demande de la liste des fichiers"),
        GET_FILE(2, "Demande de fichier"),
        
        // Message codes sent by the Centralisateur : Download file
        DOWNLOAD_FILE_HERE(10, "Envoi du fichier"),
        DOWNLOAD_FILE_NOT_EXISTS(11, "Le fichier n'existe pas");
        
        private int code;
        private String nom;
        
        TcpMessage(final int code, final String nom) {
            this.code = code;
            this.nom = nom;
        }
        
        int getCode() {
            return code;
        }
        
        String getNom() {
            return nom;
        }
    }

    /**
     * Default constructor for Spring AOP.
     */
    public ServiceTcpExchange() {
        this.serverTcpPort = 0;
    }
    
    /**
     * Constructor.
     * 
     * @param serverTcpPort TCP port for server listening
     */
    public ServiceTcpExchange(final int serverTcpPort) {
        this.serverTcpPort = serverTcpPort;
    }
    
    @Override
    protected void startService() throws ServiceStartException {
        try {
            // Open the server listening socket
            serverSocket = new ServerSocket(serverTcpPort);
            
            // Start the server listening thread
            serverListenerThread = new Thread(new ServerListener());
            serverListenerThread.start();
        }
        catch (final IOException exception) {
            LOGGER.error("Cannot open TCP socket", exception);
            throw new ServiceStartException(exception);
        }
    }
    
    private class ServerListener implements Runnable {
        @Override
        public void run() {
            while(State.STARTED.equals(getState()) || State.STARTING.equals(getState())) {
                if(State.STARTED.equals(getState())) {
                    try {
                        final Socket socket = serverSocket.accept();
                        
                        // Launch a new thread to threat the received request
                        new Thread(new Runnable() {
                            public void run() {
                                accept(socket);
                            }
                        });
                    }
                    catch (final IOException exception) {
                        LOGGER.error("Exception", exception);
                    }
                }
            }
        }
    }
    
    // Threat an accepted socket
    private void accept(final Socket socket) {
        LOGGER.info("Acception socket entrant");

        try {
            final InputStream is = socket.getInputStream();
            final DataInputStream bis = new DataInputStream(is);
            
            // Le premier 'int' du flux TCP indique l'action demandée au serveur
            final int code = bis.readInt();
            
            if(TcpMessage.LIST_FILES.getCode() == code) {       // Demande de la liste des fichiers disponibles en téléchargement
                LOGGER.info("Message de type [{}]", TcpMessage.LIST_FILES.getNom());
                
                sendListFiles(socket);
                socket.close();
            }
            else if(TcpMessage.GET_FILE.getCode() == code) {    // Demande d'un fichier disponible en téléchargement
                LOGGER.info("Message de type [{}]", TcpMessage.GET_FILE.getNom());
                
                final long fileId = bis.readLong();
                sendFile(socket, fileId);
                socket.close();
            }
            else {
                LOGGER.info("Message inconnu [{}]", code);
            }
        }
        catch (final IOException exception) {
            LOGGER.error("Exception", exception);
        }
        catch (final SQLException exception) {
            LOGGER.error("Exception", exception);
        }
    }

    /**
     * Send by TCP the list of the available in the Centralisateur files to download.
     * 
     * @param socket received socket request
     * @throws IOException
     */
    public void sendListFiles(final Socket socket) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(bos);
        
        final List<Download> files = downloadService.loadAllDownloads();
        
        // Nombre de fichiers disponibles
        dos.writeInt(files.size());
        dos.flush();
        LOGGER.info("Nombre de fichiers : {}", files.size());
        
        for(final Download file : files) {
            // Identifiant unique du fichier
            final long fileId = file.getId();
            dos.writeLong(fileId);
            
            // Nom du fichier
            dos.writeInt(file.getNom().length());
            dos.write(file.getNom().getBytes());
            
            // Catégorie du fichier (map de jeu, map de joueur, ...)
            dos.writeInt(file.getCategorie().getCode());
            
            // Taille du fichier en octets
            dos.writeInt(file.getTaille());
            
            // Description du fichier
            dos.writeInt(file.getDescription().length());
            dos.write(file.getDescription().getBytes());
        }
        
        dos.flush();
        socket.getOutputStream().write(bos.toByteArray());
        
        socket.getOutputStream().flush();
    }
    
    /**
     * Send by TCP the requested download file.
     * 
     * @param socket received socket request
     * @param downloadId technical identifier of the download file
     * @throws IOException
     */
    public void sendFile(final Socket socket, final long downloadId) throws IOException, SQLException {       
        final OutputStream os = socket.getOutputStream();

        try {
            downloadService.writeDownloadContentInTcpStream(downloadId, TcpMessage.DOWNLOAD_FILE_HERE.getCode(), os);
        }
        catch(final DownloadFileNotExistsException exception) {
            LOGGER.info("The requested file does not exists : downloadId='{}'", downloadId);
            
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final DataOutputStream dos = new DataOutputStream(bos);
            dos.writeInt(TcpMessage.DOWNLOAD_FILE_NOT_EXISTS.getCode());
        }
        
        os.flush();
    }
    
    @Override
    protected void stopService() throws ServiceStopException {
        try {
            serverSocket.close();
        }
        catch (final IOException exception) {
            LOGGER.error("Cannot close TCP socket", exception);
            throw new ServiceStopException(exception);
        }
    }

    /** Spring setter. */
    public void setDownloadService(final DownloadService downloadService) {
        this.downloadService = downloadService;
    }
}
