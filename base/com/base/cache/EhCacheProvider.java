package com.base.cache;

import java.util.Hashtable;

import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.base.exception.CacheException;

public class EhCacheProvider implements CacheProvider {

    private static final Log log = LogFactory.getLog(EhCacheProvider.class);

    private CacheManager manager;
    private Hashtable<String, EhCache> cacheManager;

    /**
     * Builds a Cache.
     * <p>
     * Even though this method provides properties, they are not used.
     * Properties for EHCache are specified in the ehcache.xml file.
     * Configuration will be read from ehcache.xml for a cache declaration where
     * the name attribute matches the name parameter in this builder.
     * 
     * @param name the name of the cache. Must match a cache configured in ehcache.xml
     * @param properties not used
     * @return a newly built cache will be built and initialised
     * @throws CacheException inter alia, if a cache of the same name already exists
     */
    public EhCache buildCache(String name, boolean autoCreate) throws CacheException {
        EhCache ehcache = cacheManager.get(name);
        if (ehcache == null && autoCreate) {
            try {
                net.sf.ehcache.Cache cache = manager.getCache(name);
                if (cache == null) {
                    log.warn("Could not find configuration [" + name + "]; using defaults.");
                    manager.addCache(name);
                    cache = manager.getCache(name);
                    log.debug("started EHCache region: " + name);
                }
                synchronized (cacheManager) {
                    ehcache = new EhCache(cache);
                    cacheManager.put(name, ehcache);
                    return ehcache;
                }
            } catch (net.sf.ehcache.CacheException e) {
                throw new CacheException(e);
            }
        }
        return ehcache;
    }

    /**
     * Callback to perform any necessary initialization of the underlying cache
     * implementation during SessionFactory construction.
     * 
     * @param properties
     *            current configuration settings.
     */
    public void start() throws CacheException {
        if (manager != null) {
            log.warn("Attempt to restart an already started EhCacheProvider. Use sessionFactory.close() "
                    + " between repeated calls to buildSessionFactory. Using previously created EhCacheProvider."
                    + " If this behaviour is required, consider using net.sf.ehcache.hibernate.SingletonEhCacheProvider.");
            return;
        }
        //manager = new CacheManager();
        manager = CacheManager.getInstance();
//        String names[] = manager.getCacheNames();
//        System.out.println("----all cache names----");
//        for (int i = 0; i < names.length; i++) {
//            System.out.println(names[i]);
//        }
//        System.out.println("----all cache names----");
        cacheManager = new Hashtable<String, EhCache>();
    }

    /**
     * Callback to perform any necessary cleanup of the underlying cache
     * implementation during SessionFactory.close().
     */
    public void stop() {
        if (manager != null) {
            manager.shutdown();
            manager = null;
        }
    }

}