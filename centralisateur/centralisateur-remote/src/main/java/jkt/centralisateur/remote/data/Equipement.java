package jkt.centralisateur.remote.data;

import java.io.Serializable;

public class Equipement implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String name;
    
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
    
    @Override
    public String toString() {
        return name;
    }
}
