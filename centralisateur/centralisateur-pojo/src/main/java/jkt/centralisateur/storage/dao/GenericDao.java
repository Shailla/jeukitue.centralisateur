package jkt.centralisateur.storage.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Generic DAO for data access.
 * 
 * @author Erwin
 *
 * @param <T> persistent class type of the DAO instance
 * @param <ID> persistent class identifier class type
 */
public interface GenericDao<T, ID extends Serializable> {
    
    /**
     * Load an object of the DAO persistant class type found with it's identifier.
     * 
     * @param id object identifier
     * @return object found or null
     */
    T load(ID id);
    
    /**
     * Load all the objects of the DAO persistent class type.
     * 
     * @return all the objects of the DAO persistent class type
     */
    List<T> loadAll();

    /**
     * Remove an object of the DAO persistent class type found with it's identifier.
     * 
     * @param id object identifier
     */
    void remove(ID id);
    
    /**
     * Save an object of the DAO persistent class type.
     * 
     * @return object of the DAO persistent class type
     */
    void save(T pojo);
    
    /**
     * Flushes the Hibernate cache to the DB.
     */
    void flush();
}
