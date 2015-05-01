package jkt.centralisateur.storage.buisiness;

import java.util.Date;

import jkt.centralisateur.storage.model.News;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

public class TestArcService {
	static final Logger log = Logger.getLogger(TestArcService.class);
	
	private SessionFactory sessionFactory;

	public void enregistrerUnTruc() {
		log.error("Je suis là maintenant 1");
		log.error("Je suis là maintenant 2");
		News news = new News();
		news.setHorodatage(new Date());
		news.setText("Coucou" + new Date().toString());
		
		log.error("Je suis là maintenant 3");
		Session session = sessionFactory.getCurrentSession();
		
		log.error("Je suis là maintenant 4");
		session.saveOrUpdate(news);
		
		log.error("Je suis là maintenant 5");
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
