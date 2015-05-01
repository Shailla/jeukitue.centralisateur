package jkt.centralisateur.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkt.centralisateur.common.Constantes;
import jkt.centralisateur.formulaire.DownloadFormulaire;
import jkt.centralisateur.storage.dto.DownloadDto;
import jkt.centralisateur.storage.service.DownloadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.orange.ogph.exemple.type.DownloadCategoryType;

@Controller
@RequestMapping("/secure/admin/")
public class DownloadsManagerController {

    @Autowired
	@Qualifier("downloadService")
	private DownloadService downloadService;
	
    /**
     * Liste les téléchargements et propose les actions d'administration des téléchargements.
     */
    @RequestMapping("manage_downloads.do")
	public ModelAndView listDownloads() {
	    // TODO paginer la page d'affichage des utilisateurs
	    final Set<DownloadDto> downloads = downloadService.loadDownloads(0, 100);	
		
	    final Map<String, Object> model = new HashMap<String, Object>();		
		model.put("downloads", downloads);
		
		return new ModelAndView("manageDownloads", model);
	}
    
    /**
     * Ouvre la page de définition des caractéristiques d'un téléchargement à ajouter.
     */
    @RequestMapping("add_download.do")
    public ModelAndView addDownload(final DownloadFormulaire formulaire,
                                    final Errors errors) {
        final String[] fichierDeployables = downloadService.loadDeployableFiles();
        formulaire.setDeployableFiles(fichierDeployables);
        
        final ModelAndView mav = new ModelAndView("addDownload", "downloadFormulaire", formulaire);
        mav.addObject("categories", DownloadCategoryType.values());
        
        return mav;
    }

    /**
     * Enregistre un nouveau téléchargement.
     */
    @RequestMapping("save_download.do")
    public ModelAndView saveDownload(final DownloadFormulaire formulaire,
                                     final Errors errors) throws Exception {        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fichier", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "version", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categorie", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "compatibilite", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", Constantes.MSG_CHAMP_OBLIGATOIRE);
        
        if(errors.getErrorCount() == 0) {
            final DownloadDto source = new DownloadDto();
            source.setNomFichier(formulaire.getFichier());
            source.setNom(formulaire.getNom());
            source.setVersion(formulaire.getVersion());
            
            try {
                source.setCategorie(DownloadCategoryType.valueOf(formulaire.getCategorie()));
            }
            catch(final IllegalArgumentException exception) {
                errors.reject("categorie", Constantes.MSG_CHAMP_INCORRECT);
            }
            
            source.setCompatibilite(formulaire.getCompatibilite());
            source.setDescription(formulaire.getDescription());
            
            final DownloadDto dto = downloadService.deployDownload(source);
            formulaire.setTaille(Long.toString(dto.getTaille()));
            formulaire.setTypeMime(dto.getTypeMime());
            
            return new ModelAndView("redirect:manage_downloads.do");
        }
        else {
            return addDownload(formulaire, errors);
        }
    }
    
    @RequestMapping("remove_download.do")
    public ModelAndView removeDownload(final long downloadId) {
        downloadService.removeDownload(downloadId);
        
        return new ModelAndView("redirect:manage_downloads.do");
    }
}
