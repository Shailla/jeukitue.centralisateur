package jkt.centralisateur.formulaire;


public class DownloadFormulaire {
	
	private String fichier;
	private String nom;
	private String description;
	private String categorie;
	private String compatibilite;
	private String version;
	private String taille;
	private String typeMime;
	private String[] deployableFiles;

	public String[] getDeployableFiles() {
        return deployableFiles;
    }

    public void setDeployableFiles(final String[] deployableFiles) {
        this.deployableFiles = deployableFiles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getFichier() {
		return fichier;
	}

	public void setFichier(final String fichier) {
		this.fichier = fichier;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(final String nom) {
		this.nom = nom;
	}
	
	public String getCategorie() {
		return categorie;
	}
	
	public void setCategorie(final String categorie) {
		this.categorie = categorie;
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
	
	public String getTaille() {
		return taille;
	}

	public void setTaille(final String taille) {
		this.taille = taille;
	}

	public String getTypeMime() {
		return typeMime;
	}

	public void setTypeMime(final String typeMime) {
		this.typeMime = typeMime;
	}
}
