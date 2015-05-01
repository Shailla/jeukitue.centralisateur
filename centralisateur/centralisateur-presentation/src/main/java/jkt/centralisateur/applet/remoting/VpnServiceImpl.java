package jkt.centralisateur.applet.remoting;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkt.centralisateur.remote.data.Chiffreur;
import jkt.centralisateur.remote.data.Site;
import jkt.centralisateur.remote.data.VpnInstance;
import jkt.centralisateur.remote.service.VpnService;

public class VpnServiceImpl implements VpnService {
    private Map<Integer, Site> siteMap = new HashMap<Integer, Site>();

    @Override
    public Site loadSiteById(final int siteId) {
        Site site = siteMap.get(siteId);
        
        return site;
    }

    @Override
    public Site createNewSite() {
        int newSiteId = 1;
        
        for(int id : siteMap.keySet()) {
            if(id >= newSiteId) {
                newSiteId = id + 1;
            }
        }
        
        Site site = new Site(newSiteId);
        site.setName("Rennes");
        site.setPosition(new Point(250, 100));
        
        Set<VpnInstance> instances = new HashSet<VpnInstance>();
        site.setVpnInstances(instances);
        VpnInstance instance1 = new VpnInstance();
        instance1.setId(1);
        instance1.setName("Instance VPN 1");
        instance1.setPosition(new Point(180, 250));
        instances.add(instance1);
        
        Set<Chiffreur> chiffreursInstance1 = new HashSet<Chiffreur>();
        instance1.setChiffreurs(chiffreursInstance1);

        Chiffreur chiffreur1_1 = new Chiffreur();
        chiffreur1_1.setId(1);
        chiffreur1_1.setName("Chiffreur 1_1");
        chiffreur1_1.setPosition(new Point(60, 350));        
        chiffreursInstance1.add(chiffreur1_1);
        
        Chiffreur chiffreur1_2 = new Chiffreur();
        chiffreur1_2.setId(1);
        chiffreur1_2.setName("Chiffreur 1_2");
        chiffreur1_2.setPosition(new Point(200, 350));        
        chiffreursInstance1.add(chiffreur1_2);
        
        if(Math.random() > 0.5d) {
            VpnInstance instance2 = new VpnInstance();
            instance2.setId(2);
            instance2.setName("Instance VPN 2");
            instance2.setPosition(new Point(320, 250));
            instances.add(instance2);
            
            if(Math.random() > 0.5d) {
                Set<Chiffreur> chiffreursInstance2 = new HashSet<Chiffreur>();
                instance2.setChiffreurs(chiffreursInstance2);
        
                Chiffreur chiffreur2_1 = new Chiffreur();
                chiffreur2_1.setId(1);
                chiffreur2_1.setName("Chiffreur 1_1");
                chiffreur2_1.setPosition(new Point(320, 350));        
                chiffreursInstance2.add(chiffreur2_1);
            }
        }
        
        return site;
    }

    @Override
    public void saveSite(final Site site) {
        siteMap.put(site.getId(), site);
    }
}
