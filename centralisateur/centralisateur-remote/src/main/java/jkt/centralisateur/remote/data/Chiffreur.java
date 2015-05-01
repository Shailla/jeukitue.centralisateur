package jkt.centralisateur.remote.data;

import java.awt.Point;
import java.io.Serializable;


public class Chiffreur implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;

    private int id;
    
    private String name;
    
    private Point position;

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
