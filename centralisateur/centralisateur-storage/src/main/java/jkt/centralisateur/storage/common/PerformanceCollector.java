package jkt.centralisateur.storage.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.SessionStatistics;


public class PerformanceCollector implements  MethodInterceptor {
	private SessionFactory sessionFactory;
	
	/** Données collectées sur les différentes méthodes transactionnelles exécutées */
	private Map<PerformanceDataKey, PerformanceData> data = new HashMap<PerformanceDataKey, PerformanceData>();

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {		
		String methodName = method.getMethod().getName();
		Class<?> clazz = method.getMethod().getDeclaringClass();
		
		// Collecte d'informations sur l'ex�cution
		Date begin = new Date();		
		
		// Exécution de la méthode cible
		Object result = method.proceed();
		
		// Calcul du temps d'exécution
		Date end = new Date();
		long executionTime = end.getTime() - begin.getTime();
		
		// Collecte d'informations sur la session
		Session session = sessionFactory.getCurrentSession();
		SessionStatistics statistics = session.getStatistics();
		int collectionCount = statistics.getCollectionCount();
		int entityCount = statistics.getEntityCount();
		
		// Mise à jour des données de performance de la méthode
		PerformanceData perf = getPerfData(clazz, methodName);
		perf.update(executionTime,
					collectionCount,
					entityCount);
		
		return result;
	}
	
	private PerformanceData getPerfData(Class<?> clazz, String methodName) {
		PerformanceDataKey key = new PerformanceDataKey();
		key.setPaquetage(clazz.getPackage().getName());
		key.setClasse(clazz.getSimpleName());
		key.setMethode(methodName);
		
		PerformanceData perf = data.get(key);
		
		if(perf == null) {
			perf = new PerformanceData();
			data.put(key, perf);
		}
		
		return perf;
	}
	
	public Map<PerformanceDataKey, PerformanceData> getData() {
		return data;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
