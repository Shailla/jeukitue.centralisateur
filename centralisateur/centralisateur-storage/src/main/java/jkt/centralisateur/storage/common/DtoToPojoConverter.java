package jkt.centralisateur.storage.common;

import jkt.centralisateur.storage.dto.DownloadDto;
import jkt.centralisateur.storage.model.Download;

public class DtoToPojoConverter {
	public Download convert(final DownloadDto dto) {
	    final Download pojo;
	    
	    if(dto != null) {
    		pojo = new Download();
    		pojo.setCategorie(dto.getCategorie());
    		pojo.setCompatibiles(dto.getCompatibilite());
    		pojo.setFichier(dto.getNomFichier());
    		pojo.setNom(dto.getNom());
    		pojo.setTaille(dto.getTaille());
    		pojo.setTypeMime(dto.getTypeMime());
    		pojo.setVersion(dto.getVersion());
    		pojo.setDescription(dto.getDescription());
	    }
	    else {
	        pojo = null;
	    }
	    
		return pojo;
	}
}
