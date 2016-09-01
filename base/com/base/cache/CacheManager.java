package com.base.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.base.utils.AppConfig;

public class CacheManager {
	private final static Log log = LogFactory.getLog(CacheManager.class);
	private static CacheProvider provider;
	
	static {
		String cacheProvider = AppConfig.getPro("cacheProvider");
		if(cacheProvider == null || cacheProvider.equals("")){
			cacheProvider = "com.base.cache.BaseCacheProvider";
			log.info("Unabled to initialize cache provider: " + cacheProvider + ", using BaseCacheProvider default.");
		}
		initCacheProvider(cacheProvider);
	}

	private static void initCacheProvider(String prv_name) {
		try {
			CacheManager.provider = (CacheProvider) Class.forName(prv_name).newInstance();
			CacheManager.provider.start();
			log.info("Using CacheProvider : " + provider.getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
    private final static Cache getCache(String region, boolean autoCreate) throws Exception{
        if(provider == null){
            provider = new BaseCacheProvider();
        }
        return provider.buildCache(region, autoCreate);
    }
    
    public final static Object get(String name, String key) throws Exception{
        log.debug("get Object : " + name + " => " + key);
        if(name != null && key != null)
            return getCache(name, true).get(key);
        return null;
    }
    
    public final static void set(String name, String key, Object value) throws Exception{
        log.debug("set : " + name + " => " + key + "=" + value);
        if(name != null && key != null && value != null)
            getCache(name, true).put(key, value);      
    }

    public final static void remove(String name, String key) throws Exception{
        if(name != null && key != null){
            Cache cache = getCache(name, false);
            if(cache != null)
                cache.remove(key);
        }
    }

    public final static void clear(String name) throws Exception{
        if(name != null){
            Cache cache = getCache(name, false);
            if(cache != null)
                cache.clear();
        }
    }

    public final static int size(String name) throws Exception{
        if(name != null){
            return getCache(name, true).size();
        }
        return 0;
    }
    
}
