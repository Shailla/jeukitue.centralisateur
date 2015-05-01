package jkt.centralisateur.storage.dto;

import java.io.InputStream;

import com.orange.ogph.exemple.type.DownloadCategoryType;

public class DownloadDto {
    private long id;				          // Identifiant
    private String nom;				          // Nom du téléchargement
    private String description;               // Nom du téléchargement
    private String compatibilite;	          // Systèmes d'exploitation compatibles
    private String version;			          // Version
    private DownloadCategoryType categorie;   // Catégorie
    private int taille;                       // Taille en octets du fichier
    private String nomFichier;                // Nom du fichier sur le disque
    private String typeMime;		          // Nom du fichier sur le disque
    private InputStream fichier;	          // Flux du fichier

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public InputStream getFichier() {
        return fichier;
    }

    public void setFichier(final InputStream fichier) {
        this.fichier = fichier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getCompatibilite() {
        return compatibilite;
    }

    public void setCompatibilite(final String compatibilite) {
        this.compatibilite = compatibilite;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public DownloadCategoryType getCategorie() {
        return categorie;
    }

    public void setCategorie(final DownloadCategoryType categorie) {
        this.categorie = categorie;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(final int taille) {
        this.taille = taille;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(final String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getTypeMime() {
        return typeMime;
    }

    public void setTypeMime(final String typeMime) {
        this.typeMime = typeMime;
    }
}
