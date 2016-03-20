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

	public static void main(String[] args) {
		for(int a=1 ; a<=9 ; a++) {
			for(int b=1 ; b<=9 ; b++) {
				for(int c=1 ; c<=9 ; c++) {
					for(int d=1 ; d<=9 ; d++) {
						for(int e=1 ; e<=9 ; e++) {
							for(int f=1 ; f<=9 ; f++) {
								for(int g=1 ; g<=9 ; g++) {
									for(int h=1 ; h<=9 ; h++) {
										for(int i=1 ; i<=9 ; i++) {
											essaie(a, b, c, d, e, f, g, h, i);
										}	
									}	
								}	
							}	
						}	
					}	
				}	
			}	
		}
	}

	private static void essaie(float a, float b, float c, float d, float e, float f, float g, float h, float i) {
		float res = a + 13f*b/c+d+12f*e-f-11f+g*h/i-10f;

		if(res == 66) {
			if(a!=b && a!=c && a!=d && a!=e && a!=f && a!=g && a!=h && a!=i) {
				if(b!=c && b!=d && b!=e && b!=f && b!=g && b!=h && b!=i) {
					if(c!=d && c!=e && c!=f && c!=g && c!=h && c!=i) {
						if(d!=e && d!=f && d!=g && d!=h && d!=i) {
							if(e!=f && e!=g && e!=h && e!=i) {
								if(f!=g && f!=h && f!=i) {
									if(g!=h && g!=i) {
										if(h!=i) {
											System.out.println("Trouvé : " + (int)a + " " + (int)b + " " + (int)c + " " + (int)d + " " + (int)e + " " + (int)f + " " + (int)g + " " + (int)h + " " + (int)i);
										}
									}
								}
							}
						}
					}
				}
			}
		}
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
