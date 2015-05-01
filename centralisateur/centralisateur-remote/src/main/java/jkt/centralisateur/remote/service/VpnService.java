package jkt.centralisateur.remote.service;

import jkt.centralisateur.remote.data.Site;

public interface VpnService {
    Site createNewSite();
    Site loadSiteById(int siteId);
    void saveSite(Site site);
}
