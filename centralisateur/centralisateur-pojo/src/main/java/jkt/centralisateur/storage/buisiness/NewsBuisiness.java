package jkt.centralisateur.storage.buisiness;

import java.util.Date;
import java.util.List;

import jkt.centralisateur.storage.dao.impl.GenericDaoImpl;
import jkt.centralisateur.storage.model.News;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class NewsBuisiness extends GenericDaoImpl<News, Long> {

    public NewsBuisiness() {
        super(News.class);
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
    public List<News> getNews(final int firstUser, final int nbrUsers) {
		final Criteria criteria = createCriteria()
			.setFirstResult(0)
			.setMaxResults(100)
			.addOrder(Order.desc(News._horodatage));
		
		return (List<News>) criteria.list();
	}

	/**
	 * Create a new user
	 * 
	 * @param username user name
	 * @param password user password
	 * @param email user email
	 * @param roles user profiles
	 */
	public void createNews(final Date date, final String text) {
		final News news = new News();
		news.setHorodatage(date);
		news.setText(text);
		
		getSession().persist(news);
	}
}
