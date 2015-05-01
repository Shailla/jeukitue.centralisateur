package jkt.centralisateur.remote.data;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class VpnInstance implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String name;
    
    private Point position;
    
    private Set<Chiffreur> chiffreurs = new HashSet<Chiffreur>();
    
    public int getId() {
        return id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Set<Chiffreur> getChiffreurs() {
        return chiffreurs;
    }
    
    public void setChiffreurs(final Set<Chiffreur> chiffreurs) {
        this.chiffreurs = chiffreurs;
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
