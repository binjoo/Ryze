package com.base.cache1;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CacheManager {
    private final static Log log = LogFactory.getLog(CacheManager.class);
    private static CacheProvider provider;
 
    static {
        initCacheProvider("com.base.cache.EhCacheProvider");
    }
     
    private static void initCacheProvider(String prv_name){
        try{
            CacheManager.provider = (CacheProvider) Class.forName(prv_name).newInstance();
            CacheManager.provider.start();
            log.info("Using CacheProvider : " + provider.getClass().getName());
        }catch(Exception e){
            log.fatal("Unabled to initialize cache provider:" + prv_name + ", using ehcache default.", e);
            CacheManager.provider = new EhCacheProvider();
        }
    }
 
    private final static Cache getCache(String cache_name, boolean autoCreate) {
        if(provider == null){
            provider = new EhCacheProvider();
        }
        return provider.buildCache(cache_name, autoCreate);
    }
 
    /**
     * 获取缓存中的数据
     * @param name
     * @param key
     * @return
     */
    public final static Object get(String name, Serializable key){
        log.debug("get Object : " + name + " => " + key);
        if(name != null && key != null)
            return getCache(name, true).get(key);
        return null;
    }
     
    /**
     * 获取缓存中的数据
     * @param <T>
     * @param resultClass
     * @param name
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public final static <T> T get(Class<T> resultClass, String name, Serializable key){
        log.debug("get <T> : " + name + " => " + key);
        if(name != null && key != null)
            return (T)getCache(name, true).get(key);
        return null;
    }
     
    /**
     * 写入缓存
     * @param name
     * @param key
     * @param value
     */
    public final static void set(String name, Serializable key, Serializable value){
        log.debug("set : " + name + " => " + key + "=" + value);
        if(name != null && key != null && value != null)
            getCache(name, true).put(key, value);      
    }
     
    /**
     * 清除缓冲中的某个数据
     * @param name
     * @param key
     */
    public final static void evict(String name, Serializable key){
        if(name != null && key != null)
            getCache(name, true).remove(key);      
    }
 
    /**
     * 清除缓冲中的某个数据
     * @param name
     * @param key
     */
    public final static void remove(String name, Serializable key){
        if(name != null && key != null){
            Cache cache = getCache(name, false);
            if(cache != null)
                cache.remove(key);
        }
    }
 
}