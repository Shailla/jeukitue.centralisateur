package jkt.centralisateur.remote.data;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Site implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String name;
    
    private Point position;

    private Set<VpnInstance> vpnInstances = new HashSet<VpnInstance>();
    
    private Set<Equipement> equipements = new HashSet<Equipement>();

    public Site(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    
    public Set<VpnInstance> getVpnInstances() {
        return vpnInstances;
    }

    public void setVpnInstances(final Set<VpnInstance> vpnInstances) {
        this.vpnInstances = vpnInstances;
    }

    public Set<Equipement> getEquipements() {
        return equipements;
    }

    public void setEquipements(final Set<Equipement> equipements) {
        this.equipements = equipements;
    }
    
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return name;
    }
}
