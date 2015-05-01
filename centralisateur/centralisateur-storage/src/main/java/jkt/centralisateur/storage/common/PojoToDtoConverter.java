package jkt.centralisateur.storage.common;

import jkt.centralisateur.storage.dto.DownloadDto;
import jkt.centralisateur.storage.model.Download;

public class PojoToDtoConverter {

	public DownloadDto convert(final Download pojo) {
		final DownloadDto dto = new DownloadDto();
		dto.setId(pojo.getId());
		dto.setNomFichier(pojo.getFichier());
		dto.setNom(pojo.getNom());
		dto.setCategorie(pojo.getCategorie());
		dto.setVersion(pojo.getVersion());
		dto.setCompatibilite(pojo.getCompatibiles());
		dto.setTaille(pojo.getTaille());
		dto.setTypeMime(pojo.getTypeMime());
		
		return dto;
	}
}
