package jkt.centralisateur.controller.debug;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.stat.CollectionStatistics;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SessionFactoryController {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    private void addPair(final List<Object[]> liste, final String name, final Object value) {
        final Object[] pair = new Object[2];
        pair[0] = name;
        pair[1] = value;
        liste.add(pair);
    }

    /**
     * Liste les types de statistiques disponibles dans la session factory. Ces types sont :
     * <li>statistiques générales</li>
     * <li>statistiques sur les collections</li>
     * <li>statistiques sur les entités</li>
     * <li>statistiques sur les requêtes</li>
     * <li>statistiques sur les régions</li>
     * 
     * @return
     */
    @RequestMapping("/debug/sessionfactory/list.do")
    public ModelAndView list() {
        final Statistics statistics = sessionFactory.getStatistics(); 
        final Boolean activated = statistics.isStatisticsEnabled();
        final ModelAndView mav = new ModelAndView("sessionFactory", "activated", activated);
        
        return mav;
    }
    
    /**
     * Active / désactive les statistiques de la session factory
     */
    @RequestMapping("/debug/sessionfactory/activate.do")
    public String activate() {
        final Statistics statistics = sessionFactory.getStatistics();
        final boolean activated = statistics.isStatisticsEnabled();
        statistics.setStatisticsEnabled(!activated);
        
        return "redirect-debug-sessionfactory-list";
    }
    
    /**
     * Réinitialisation des statistiques de la session factory
     */
    @RequestMapping("/debug/sessionfactory/clear.do")
    public String clear() {
        final Statistics statistics = sessionFactory.getStatistics();
        statistics.clear();
        
        return "redirect-debug-sessionfactory-list";
    }

    @RequestMapping("/debug/sessionfactory/general.do")
    public ModelAndView general() {
        final Statistics statistics = sessionFactory.getStatistics();        
        final ModelAndView mav = new ModelAndView("sessionFactory-general");

        final List<Object[]> general = new ArrayList<Object[]>();

        // General statistics
        addPair(general, "CloseStatementCount", statistics.getCloseStatementCount());
        addPair(general, "CollectionFetchCount", statistics.getCollectionFetchCount());
        addPair(general, "CollectionLoadCount", statistics.getCollectionLoadCount());
        addPair(general, "CollectionRecreateCount", statistics.getCollectionRecreateCount());
        addPair(general, "CollectionRemoveCount", statistics.getCollectionRemoveCount());
        addPair(general, "CollectionUpdateCount", statistics.getCollectionUpdateCount());
        addPair(general, "ConnectCount", statistics.getConnectCount());
        addPair(general, "EntityDeleteCount", statistics.getEntityDeleteCount());
        addPair(general, "EntityFetchCount", statistics.getEntityFetchCount());
        addPair(general, "EntityInsertCount", statistics.getEntityInsertCount());
        addPair(general, "EntityLoadCount", statistics.getEntityLoadCount());
        addPair(general, "EntityUpdateCount", statistics.getEntityUpdateCount());
        addPair(general, "FlushCount", statistics.getFlushCount());
        addPair(general, "OperationThreshold", statistics.getOperationThreshold());
        addPair(general, "OptimisticFailureCount", statistics.getOptimisticFailureCount());
        addPair(general, "PrepareStatementCount", statistics.getPrepareStatementCount());
        addPair(general, "QueryCacheHitCount", statistics.getQueryCacheHitCount());
        addPair(general, "QueryCacheMissCount", statistics.getQueryCacheMissCount());
        addPair(general, "QueryCachePutCount", statistics.getQueryCachePutCount());
        addPair(general, "QueryExecutionCount", statistics.getQueryExecutionCount());
        addPair(general, "QueryExecutionMaxTime", statistics.getQueryExecutionMaxTime());
        addPair(general, "QueryExecutionMaxTimeQueryString", statistics.getQueryExecutionMaxTimeQueryString());
        addPair(general, "SecondLevelCacheHitCount", statistics.getSecondLevelCacheHitCount());
        addPair(general, "SecondLevelCacheMissCount", statistics.getSecondLevelCacheMissCount());
        addPair(general, "SecondLevelCachePutCount", statistics.getSecondLevelCachePutCount());
        addPair(general, "SessionCloseCount", statistics.getSessionCloseCount());
        addPair(general, "SessionOpenCount", statistics.getSessionOpenCount());
        addPair(general, "StartTime", statistics.getStartTime());
        addPair(general, "SuccessfulTransactionCount", statistics.getSuccessfulTransactionCount());
        addPair(general, "TransactionCount", statistics.getTransactionCount());

        mav.addObject("general", general);

        return mav;
    }

    @RequestMapping("/debug/sessionfactory/collections.do")
    public ModelAndView collections() {
        final Statistics statistics = sessionFactory.getStatistics();        
        final ModelAndView mav = new ModelAndView("sessionFactory-autre");
        
        final List<List<Object>> collectionRoles = new ArrayList<List<Object>>();

        final List<Object> titles = new ArrayList<Object>();
        titles.add("Role");
        titles.add("CategoryName");
        titles.add("FetchCount");
        titles.add("LoadCount");
        titles.add("RecreateCount");
        titles.add("RemoveCount");
        titles.add("UpdateCount");

        collectionRoles.add(titles);

        for(final String role : statistics.getCollectionRoleNames()) {
            final CollectionStatistics stat = statistics.getCollectionStatistics(role);

            List<Object> statList = new ArrayList<Object>();
            statList.add(role);
            statList.add(stat.getCategoryName());
            statList.add(stat.getFetchCount());
            statList.add(stat.getLoadCount());
            statList.add(stat.getRecreateCount());
            statList.add(stat.getRemoveCount());
            statList.add(stat.getUpdateCount());

            collectionRoles.add(statList);
        }

        mav.addObject("title", "Collections");
        mav.addObject("items", collectionRoles);

        return mav;
    }

    @RequestMapping("/debug/sessionfactory/entities.do")
    public ModelAndView entities() {
        final Statistics statistics = sessionFactory.getStatistics();        
        final ModelAndView mav = new ModelAndView("sessionFactory-autre");
        
        final List<List<Object>> entities = new ArrayList<List<Object>>();

        final List<Object> titles = new ArrayList<Object>();
        titles.add("Entité");
        titles.add("CategoryName");
        titles.add("DeleteCount");
        titles.add("FetchCount");
        titles.add("InsertCount");
        titles.add("LoadCount");
        titles.add("OptimisticFailureCount");
        titles.add("UpdateCount");

        entities.add(titles);


        for(final String entityName : statistics.getEntityNames()) {
            final EntityStatistics stat = statistics.getEntityStatistics(entityName);

            final List<Object> statList = new ArrayList<Object>();
            statList.add(entityName);
            statList.add(stat.getCategoryName());
            statList.add(stat.getDeleteCount());
            statList.add(stat.getFetchCount());
            statList.add(stat.getInsertCount());
            statList.add(stat.getLoadCount());
            statList.add(stat.getOptimisticFailureCount());
            statList.add(stat.getUpdateCount());

            entities.add(statList);   
        }

        mav.addObject("title", "Entités");
        mav.addObject("items", entities);

        return mav;
    }

    @RequestMapping("/debug/sessionfactory/queries.do")
    public ModelAndView queries() {
        final Statistics statistics = sessionFactory.getStatistics();        
        final ModelAndView mav = new ModelAndView("sessionFactory-autre");
        
        final List<List<Object>> queries = new ArrayList<List<Object>>();

        final List<Object> titles = new ArrayList<Object>();
        titles.add("Requ�te");
        titles.add("CacheHitCount");
        titles.add("CacheMissCount");
        titles.add("CachePutCount");
        titles.add("CategoryName");
        titles.add("ExecutionAvgTime");
        titles.add("ExecutionCount");
        titles.add("ExecutionMaxTime");
        titles.add("ExecutionMinTime");
        titles.add("ExecutionRowCount");

        queries.add(titles);

        for(final String query : statistics.getQueries()) {
            final QueryStatistics stat = statistics.getQueryStatistics(query);

            final List<Object> statList = new ArrayList<Object>();
            statList.add(query);
            statList.add(stat.getCacheHitCount());
            statList.add(stat.getCacheMissCount());
            statList.add(stat.getCachePutCount());
            statList.add(stat.getCategoryName());
            statList.add(stat.getExecutionAvgTime());
            statList.add(stat.getExecutionCount());
            statList.add(stat.getExecutionMaxTime());
            statList.add(stat.getExecutionMinTime());
            statList.add(stat.getExecutionRowCount());

            queries.add(statList);
        }

        mav.addObject("title", "Requêtes");
        mav.addObject("items", queries);

        return mav;
    }


    @RequestMapping("/debug/sessionfactory/regions.do")
    public ModelAndView regions() {
        final Statistics statistics = sessionFactory.getStatistics();        
        final ModelAndView mav = new ModelAndView("sessionFactory-autre");
        
        final List<List<Object>> regions = new ArrayList<List<Object>>();

        final List<Object> titles = new ArrayList<Object>();
        titles.add("Région");
        titles.add("CategoryName");
        titles.add("ElementCountInMemory");
        titles.add("ElementCountOnDisk");
        titles.add("HitCount");
        titles.add("MissCount");
        titles.add("PutCount");
        titles.add("SizeInMemory");

        regions.add(titles);

        for(final String regionName : statistics.getSecondLevelCacheRegionNames()) {
            final SecondLevelCacheStatistics stat = statistics.getSecondLevelCacheStatistics(regionName);

            final List<Object> statList = new ArrayList<Object>();
            statList.add(regionName);
            statList.add(stat.getCategoryName());
            statList.add(stat.getElementCountInMemory());
            statList.add(stat.getElementCountOnDisk());
            //stat.getEntries();
            statList.add(stat.getHitCount());
            statList.add(stat.getMissCount());
            statList.add(stat.getPutCount());
            statList.add(stat.getSizeInMemory());

            regions.add(statList);
        }

        mav.addObject("title", "Régions");
        mav.addObject("items", regions);

        return mav;
    }        
}
