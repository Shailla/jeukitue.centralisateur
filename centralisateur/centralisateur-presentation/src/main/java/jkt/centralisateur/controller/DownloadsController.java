package jkt.centralisateur.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jkt.centralisateur.storage.dto.DownloadDto;
import jkt.centralisateur.storage.service.DownloadService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/secure/")
public class DownloadsController {

    @Autowired
    @Qualifier("downloadService")
    private DownloadService downloadService;

    /**
     * Show all download files.
     */
    @RequestMapping("downloads.do")
    public ModelAndView listDownloads(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final Map<String, Object> model = new HashMap<String, Object>();
        final Set<DownloadDto> downloads = downloadService.loadDownloads(0, 100);	// TODO paginer la page d'affichage des utilisateurs
        model.put("downloads", downloads);

        return new ModelAndView("downloads", model);
    }

    /**
     * Download one download file.
     */
    @RequestMapping("download.do")
    public ModelAndView download(final HttpServletRequest request,
                                 final HttpServletResponse response) throws Exception {

        final String nomFichier = request.getParameter("fichier");
        final DownloadDto download = downloadService.loadDownloadWithStream(nomFichier);

        // Caractéristiques du fichier
        response.setContentType(download.getTypeMime());
        response.setContentLength(download.getTaille());

        // Flux du fichier
        final OutputStream out = response.getOutputStream();
        final InputStream in = download.getFichier();

        IOUtils.copy(in, out);

        return null;
    }
}
