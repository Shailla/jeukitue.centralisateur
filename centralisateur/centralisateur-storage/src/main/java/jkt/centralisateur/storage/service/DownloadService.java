package jkt.centralisateur.storage.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkt.centralisateur.common.TypeMimeHelper;
import jkt.centralisateur.common.exception.DownloadFileNotExistsException;
import jkt.centralisateur.common.exception.ExtensionAbsenteException;
import jkt.centralisateur.common.exception.ExtensionInconnueException;
import jkt.centralisateur.storage.buisiness.DownloadBuisiness;
import jkt.centralisateur.storage.common.DtoToPojoConverter;
import jkt.centralisateur.storage.common.PojoToDtoConverter;
import jkt.centralisateur.storage.dto.DownloadDto;
import jkt.centralisateur.storage.model.Download;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class DownloadService {
    static private Logger LOGGER = LoggerFactory.getLogger(DownloadService.class);

    private DownloadBuisiness downloadBuisiness;
    private PojoToDtoConverter pojoToDtoConverter;
    private DtoToPojoConverter dtoToPojoConverter;

    /** Path du répertoire des fichiers à déployer */
    private File deploiementLocation;

    /**
     * Return all user names in alphabetical order
     * 
     * @param firstUser database row of the first user
     * @param nbrDownloads number of downloads get by the request
     * @return list of user names in alphabetical order
     */
    @Transactional(readOnly=true)
    public Set<DownloadDto> loadDownloads(final int firstUser, final int nbrDownloads) {
        final List<Download> downloads = downloadBuisiness.getDownloads(firstUser, nbrDownloads);

        final Set<DownloadDto> dtos = new HashSet<DownloadDto>();

        for(Download download : downloads) {
            DownloadDto dto = pojoToDtoConverter.convert(download);
            dtos.add(dto);
        }

        return dtos;
    }

    @Transactional(readOnly=true)
    public List<Download> loadAllDownloads() {
        return downloadBuisiness.loadAll();
    }

    @Transactional(readOnly=true)
    public Download loadDownload(final long downloadId) {
        return downloadBuisiness.load(downloadId);
    }

    @Transactional(readOnly=true)
    public void writeDownloadContentInTcpStream(final long downloadId, final int tcpMsgCode, final OutputStream tcpStream) throws IOException, SQLException, DownloadFileNotExistsException {
        final Download download = downloadBuisiness.load(downloadId);
        
        if(download != null) {
            final Blob blob = download.getContentFile();
            final InputStream is = blob.getBinaryStream();
            
            // Envoi des caractéristiques du fichier
            {
                final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                final DataOutputStream dos = new DataOutputStream(bos);
                
                // Write the TCP message code
                dos.writeInt(tcpMsgCode);
                
                // Identifiant unique du fichier
                dos.writeLong(downloadId);
                
                // Nom du fichier
                dos.writeInt(download.getNom().length());
                dos.write(download.getNom().getBytes());
                
                // Catégorie du fichier (map de jeu, map de joueur, ...)
                dos.writeInt(download.getCategorie().getCode());
                
                // Taille du fichier en octets
                dos.writeInt(download.getTaille());
                
                dos.flush();
                tcpStream.write(bos.toByteArray());
                
                dos.close();
            }
    
            // Envoi du contenu du fichier
            int var;
            while((var = is.read()) != -1) {
                tcpStream.write(var);
            }
        }
        else {
            throw new DownloadFileNotExistsException();
        }
    }

    public String[] loadDeployableFiles() {
        final File[] fichiers = deploiementLocation.listFiles();

        final String[] fileNames;

        if(fichiers != null) {
            fileNames = new String[fichiers.length];

            for(int i=0 ; i<fichiers.length ; i++) {
                fileNames[i] = fichiers[i].getName();
            }
        }
        else {
            LOGGER.error("Le répertoire de déploiement n'est pas valide '{}'", deploiementLocation);
            fileNames = new String[0];
        }

        return fileNames; 
    }

    /**
     * Récupère les caractéristiques du fichier ainsi qu'un flux sur le fichier.
     * 
     * @param nomFichier nom du fichier
     * @return fichier
     * @throws FileNotFoundException 
     * @throws SQLException 
     */
    @Transactional(readOnly=true)
    public DownloadDto loadDownloadWithStream(final String nomFichier) throws FileNotFoundException, SQLException {
        final Download download = downloadBuisiness.getDownloadByName(nomFichier);
        final DownloadDto dto = pojoToDtoConverter.convert(download);

        final Blob contentFile = download.getContentFile();
        dto.setFichier(contentFile.getBinaryStream());

        return dto;
    }

    @Transactional(readOnly=false)
    public void removeDownload(final long downloadId) {
        downloadBuisiness.remove(downloadId);
    }

    /**
     * Crée en BDD une référence à un fichier présent dans le repository
     *  
     * @param download données utilisateur du fichier
     * @return données complétées du fichier (la taille et le type mime par exemple sont ajoutés)
     * @throws ExtensionInconnueException si l'extension du fichier n'est pas une extension supportée
     * @throws ExtensionAbsenteException si l'extension est absente dans le nom du fichier
     */
    @Transactional(readOnly=false)
    public DownloadDto deployDownload(final DownloadDto download) throws ExtensionInconnueException, ExtensionAbsenteException {
        final String nomFichier = download.getNomFichier();

        // Fichier source et destination
        final File fichierSource = new File(deploiementLocation + File.separator + nomFichier);

        // Taille du fichier
        download.setTaille((int) fichierSource.length());

        // Détermination du type Mime
        final int pointIndex = nomFichier.lastIndexOf('.');

        if (pointIndex > 0) {
            String extension = nomFichier.substring(pointIndex + 1);
            String typeMime = TypeMimeHelper.getTypeMimeFromExtention(extension);

            if (StringUtils.isEmpty(typeMime)) {
                throw new ExtensionInconnueException();
            }

            download.setTypeMime(typeMime);
        }
        else {
            throw new ExtensionAbsenteException();
        }

        final Download pojo = dtoToPojoConverter.convert(download);

        // Copie du fichier du répertoire de déploiement vers celui de stockage
        InputStream is = null;

        try {
            is = new FileInputStream(fichierSource);
            pojo.setContentFileAsStream(is);

            downloadBuisiness.save(pojo);
            downloadBuisiness.flush();
        }
        catch(final FileNotFoundException exception) {
            LOGGER.error("Fichier à déployer introuvable", exception);
            throw new RuntimeException(exception);
        }
        catch (final IOException exception) {
            LOGGER.error("Erreur de lecture du fichier à déployer", exception);
            throw new RuntimeException(exception);
        }
        finally {
            IOUtils.closeQuietly(is);
        }

        return download;
    }

    /** Sring setter. */
    public void setDownloadBuisiness(final DownloadBuisiness downloadBuisiness) {
        this.downloadBuisiness = downloadBuisiness;
    }

    /** Sring setter. */
    public void setPojoToDtoConverter(final PojoToDtoConverter pojoToDtoConverter) {
        this.pojoToDtoConverter = pojoToDtoConverter;
    }

    /** Sring setter. */
    public void setDtoToPojoConverter(final DtoToPojoConverter dtoToPojoConverter) {
        this.dtoToPojoConverter = dtoToPojoConverter;
    }

    /** Sring setter. */
    public void setDeploiementLocation(final File deploiementLocation) {
        this.deploiementLocation = deploiementLocation;
    }
}
