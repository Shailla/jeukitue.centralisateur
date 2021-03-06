package jkt.centralisateur.storage.model;

// Generated by Hibernate Tools 3.2.4.GA

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.engine.jdbc.NonContextualLobCreator;

import com.orange.ogph.exemple.type.DownloadCategoryType;

/**
 * Download generated by hbm2java
 */
@Entity
@Table(name = "download", catalog = "centralisateur")
public class Download implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	static public final String _id = "id";
	static public final String _fichier = "fichier";
	static public final String _description = "description";
	static public final String _nom = "nom";
	static public final String _categorie = "categorie";
	static public final String _taille = "taille";
	static public final String _version = "version";
	static public final String _compatibiles = "compatibiles";
	static public final String _typeMime = "typeMime";
	static public final String _contentFile = "typeMime";

	private long id;
	private String fichier;
	private String description;
	private String nom;
	private DownloadCategoryType categorie;
	private int taille;
	private String version;
	private String compatibiles;
	private String typeMime;
	private Blob contentFile;

	@Id
	/*BasicAnnotation:getId*/
	/*AnnColumnAnnotation:getId*/@Column(name = "DWD_ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	/*BasicAnnotation:getFichier*/
	/*AnnColumnAnnotation:getFichier*/@Column(name = "DWD_FICHIER", nullable = false, length = 50)
	public String getFichier() {
		return this.fichier;
	}

	public void setFichier(final String fichier) {
		this.fichier = fichier;
	}
	
    /*BasicAnnotation:getDescription*/
    /*AnnColumnAnnotation:getFichier*/@Column(name = "DWD_DESCRIPTION", nullable = false, length = 1000)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

	/*BasicAnnotation:getNom*/
	/*AnnColumnAnnotation:getNom*/@Column(name = "DWD_NOM", nullable = false, length = 50)
	public String getNom() {
		return this.nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	/*BasicAnnotation:getCategorie*/
	/*AnnColumnAnnotation:getCategorie*/@Column(name = "DWD_CATEGORIE", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	public DownloadCategoryType getCategorie() {
		return this.categorie;
	}

	public void setCategorie(final DownloadCategoryType categorie) {
		this.categorie = categorie;
	}

	/*BasicAnnotation:getTaille*/
	/*AnnColumnAnnotation:getTaille*/@Column(name = "DWD_TAILLE", nullable = false)
	public int getTaille() {
		return this.taille;
	}

	public void setTaille(final int taille) {
		this.taille = taille;
	}

	/*BasicAnnotation:getVersion*/
	/*AnnColumnAnnotation:getVersion*/@Column(name = "DWD_VERSION", nullable = false, length = 10)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	/*BasicAnnotation:getCompatibiles*/
	/*AnnColumnAnnotation:getCompatibiles*/@Column(name = "DWD_COMPATIBILES", nullable = false, length = 100)
	public String getCompatibiles() {
		return this.compatibiles;
	}

	public void setCompatibiles(final String compatibiles) {
		this.compatibiles = compatibiles;
	}

	/*BasicAnnotation:getTypeMime*/
	/*AnnColumnAnnotation:getTypeMime*/@Column(name = "DWD_TYPE_MIME", nullable = false, length = 50)
	public String getTypeMime() {
		return this.typeMime;
	}

	public void setTypeMime(final String typeMime) {
		this.typeMime = typeMime;
	}
	
	/*BasicAnnotation:getContentFile*/
    /*AnnColumnAnnotation:getContentFile*/@Column(name = "DWD_CONTENT_FILE", nullable = false, length = 10000000)
    public Blob getContentFile() {
        return this.contentFile;
    }

    public void setContentFile(final Blob contentFile) {
        this.contentFile = contentFile;
    }
    
    public void setContentFileAsStream(final InputStream contentFileStream, long length) throws IOException {
        this.contentFile = NonContextualLobCreator.INSTANCE.createBlob(contentFileStream, length);
    }
}
