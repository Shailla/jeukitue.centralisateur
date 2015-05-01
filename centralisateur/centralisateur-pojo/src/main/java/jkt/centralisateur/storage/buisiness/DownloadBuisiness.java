package jkt.centralisateur.storage.buisiness;

import java.util.List;

import jkt.centralisateur.storage.dao.impl.GenericDaoImpl;
import jkt.centralisateur.storage.model.Download;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class DownloadBuisiness extends GenericDaoImpl<Download, Long>{

    public DownloadBuisiness() {
        super(Download.class);
    }
    
	@SuppressWarnings("unchecked")
    public List<Download> getDownloads(final int firstDownload, final int nbrDownload) {
		final Criteria criteria = createCriteria();
		criteria.setFirstResult(firstDownload);
		criteria.setMaxResults(nbrDownload);
		
		return (List<Download>) criteria.list();
	}
	
	public Download getDownloadByName(final String nomFichier) {
	    final Criteria criteria = createCriteria();
	    criteria.add(Restrictions.eq(Download._fichier, nomFichier));
		
		return (Download) criteria.uniqueResult();
	}
}
