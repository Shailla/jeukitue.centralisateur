package jkt.centralisateur.storage.dao.impl;

import java.io.Serializable;
import java.util.List;

import jkt.centralisateur.storage.dao.GenericDao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Generic DAO implementation for database data access.
 * 
 * @author Erwin
 *
 * @param <T> persistent class type of the DAO instance
 * @param <ID> persistent class identifier class type
 */
public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {
    
    /** Hibernate session factory. */
    private SessionFactory sessionFactory;
    
    /** Persistant class type of the DAO instance. */
    protected final Class<T> persistentClass;
    
    
    /** Spring setter. */
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
    /**
     * Constructor.
     * 
     * @param persistentClass pojo class type
     */
    public GenericDaoImpl(final Class<T> persistentClass) {
        this.persistentClass = persistentClass; 
    }
    
    /**
     * Return the current Hibernate session.
     * 
     * @return current Hibernate session
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    /**
     * Return an new Criteria instance on the persistant class type of the DAO instance.
     * 
     * @return new Criteria instance on the persistant class type
     */
    protected Criteria createCriteria() {
        final Session session = getSession();
        
        return session.createCriteria(persistentClass);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T load(final ID id) {
        return (T) getSession().get(persistentClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> loadAll() {
        return (List<T>) createCriteria().list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void remove(final ID id) {
        final T proxy = (T) getSession().load(persistentClass, id);
        
        if(proxy != null) {
            getSession().delete(proxy);    
        }
    }
    
    @Override
    public void save(final T pojo) {
        getSession().save(pojo);
    }
    
    @Override
    public void flush() {
        getSession().flush();
    }
}
