package jkt.centralisateur.storage.buisiness;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkt.centralisateur.storage.dao.impl.GenericDaoImpl;
import jkt.centralisateur.storage.model.Profile;
import jkt.centralisateur.storage.model.User;
import jkt.centralisateur.storage.result.CreateUserResult;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.collection.PersistentSet;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class UserBuisiness extends GenericDaoImpl<User, Long> {	
	
	public UserBuisiness() {
	    super(User.class);
    }
	
	/**
	 * Get a user
	 * 
	 * @param username user name
	 * @return specified user
	 */
	public User getUser(final String username) {
		final User user = (User) createCriteria()
		                    .add(Restrictions.eq(User._login, username))
		                    .uniqueResult();
		
		final PersistentSet profiles = (PersistentSet)user.getProfiles();
		profiles.forceInitialization();
		
		return user;
	}
	
	/**
	 * Get a user
	 * 
	 * @param userId user database identifier
	 * @return specified user
	 */
	public User getUser(final Long userId) {
		return (User) getSession().get(persistentClass, userId);
	}
	
	/**
	 * Get all users
	 * Ordered alphabetically
	 * 
	 * @param firstUser database row of the first user
	 * @param nbrUsers number of users get by the request
	 * @return user list
	 */
	@SuppressWarnings("unchecked")
    public List<User> getUsers(final int firstUser, final int nbrUsers) {
		final Criteria criteria =  createCriteria().setFirstResult(0)
			                                .setMaxResults(100)
			                                .addOrder(Order.asc("login"))
			                                .setComment("commantaire");
		
		return (List<User>) criteria.list();
	}

	/**
	 * Create a new user
	 * 
	 * @param login user name
	 * @param password user password
	 * @param email user email
	 * @param roles user profiles
	 */
	public CreateUserResult createUser(final String login, 
			   			               final String password,
			   			               final String email,
			   			               final String uuidInscription,
			   			               final boolean enabled,
			   			               final Set<String> roles) {
		final CreateUserResult result;
	    
	    final Session session = getSession();
		
		// Vérifie si l'utilisateur existe déjà
		int nbrUsers = (Integer) session.createCriteria(User.class)
		        .add(Restrictions.eq(User._login, login))
		        .setProjection(Projections.count(User._login))
		        .uniqueResult();
		
		if(nbrUsers == 0) {
            final Set<Profile> profiles = new HashSet<Profile>();
            
            final User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            user.setUuidInscription(uuidInscription);
            
            if(roles != null) {
                for(final String role : roles) {
                    final Profile profile = (Profile) session.createCriteria(Profile.class)
                        .add(Restrictions.eq(Profile._nom, role))
                        .uniqueResult();    
                    
                    if(profile != null) {
                        profiles.add(profile);  
                    }
                }
            }
            
            user.setProfiles(profiles);
            
            session.persist(user);
            
            result = CreateUserResult.SUCCESS;
		}
		else {
		    result = CreateUserResult.ALREADY_EXISTING_USER;
		}
		
		return result;
	}
	
	/**
	 * Modify an existing user
	 * 
	 * @param userId user database identifier
	 * @param username user name
	 * @param password user password
	 * @param email user email
	 * @param roles user profiles
	 */
	public void modifyUser(final Long userId,
						   final String username, 
						   final String password,
						   final String email,
						   final Set<String> roles) {
		final Session session = getSession();
		final Set<Profile> profiles = new HashSet<Profile>();
		
		final User user = (User) session.get(persistentClass, userId);
		user.setLogin(username);
		user.setPassword(password);
		user.setEmail(email);
		
		if(roles != null) {
    		for(final String role : roles) {
    		    final Profile profile = (Profile) session.createCriteria(Profile.class)
    				.add(Restrictions.eq(Profile._nom, role))
    				.uniqueResult();	
    			
    			if(profile != null) {
    				profiles.add(profile);	
    			}
    		}
		}

		user.setProfiles(profiles);
		
		session.save(user);
	}
	
	/**
     * Modify an existing user
     * 
     * @param user user
     */
    public void modifyUser(final User user) {        
        getSession().save(user);
    }

	/**
	 * Remove a user from database
	 * 
	 * @param username user name
	 */
	public void removeUser(final Long userId) {
	    final Session session = getSession();
		
	    final User user = (User) session.createCriteria(User.class)
			.add(Restrictions.eq(User._id, userId))
			.uniqueResult();
		
		session.delete(user);
	}
}
