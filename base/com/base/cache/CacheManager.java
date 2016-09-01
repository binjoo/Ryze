package com.base.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.base.utils.AppConfig;

public class CacheManager {
	private final static Log log = LogFactory.getLog(CacheManager.class);
	private static CacheProvider provider;
	private static String defaultRegion = "default";

	static {
		String cacheProvider = AppConfig.getPro("cacheProvider");
		if (cacheProvider == null || cacheProvider.equals("")) {
			cacheProvider = "com.base.cache.BaseCacheProvider";
			log.info("Unabled to initialize cache provider: " + cacheProvider + ", using BaseCacheProvider default.");
		}
		initCacheProvider(cacheProvider);
	}

	private static void initCacheProvider(String cacheClass) {
		try {
			CacheManager.provider = (CacheProvider) Class.forName(cacheClass).newInstance();
			CacheManager.provider.start();
			log.info("Using CacheProvider : " + provider.getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final static Cache getCache(String region, boolean autoCreate) throws Exception {
		if (provider == null) {
			provider = new BaseCacheProvider();
		}
		return provider.buildCache(region, autoCreate);
	}

	/**
	 * 获得缓存内容，返回<T>数据类型
	 * 
	 * @param resultClass
	 * @param name
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final static <T> T get(Class<T> resultClass, String name, String key) throws Exception {
		log.debug("get " + resultClass + " : " + name + " => " + key);
		if (name != null && key != null)
			return (T) getCache(name, true).get(key);
		return null;
	}

	/**
	 * 获得默认空间缓存
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final static Object get(String key) throws Exception {
		return get(defaultRegion, key);
	}

	/**
	 * 获得缓存
	 * 
	 * @param name
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final static Object get(String name, String key) throws Exception {
		return get(Object.class, name, key);
	}

	/**
	 * 写入缓存到默认空间
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public final static void set(String key, Object value) throws Exception {
		set(defaultRegion, key, value);
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public final static void set(String name, String key, Object value) throws Exception {
		log.debug("set cache : " + name + " => " + key + "=" + value);
		if (name != null && key != null && value != null)
			getCache(name, true).put(key, value);
	}

	/**
	 * 移除默认空间缓存
	 * 
	 * @param key
	 * @throws Exception
	 */
	public final static void remove(String key) throws Exception {
		remove(defaultRegion, key);
	}

	/**
	 * 移除缓存
	 * 
	 * @param key
	 * @throws Exception
	 */
	public final static void remove(String name, String key) throws Exception {
		log.debug("remove cache : " + name + " => " + key);
		if (name != null && key != null) {
			Cache cache = getCache(name, false);
			if (cache != null)
				cache.remove(key);
		}
	}

	/**
	 * 清空默认空间缓存
	 * 
	 * @param key
	 * @throws Exception
	 */
	public final static void clear() throws Exception {
		clear(defaultRegion);
	}

	/**
	 * 清空缓存
	 * 
	 * @param key
	 * @throws Exception
	 */
	public final static void clear(String name) throws Exception {
		log.debug("clear cache : " + name);
		if (name != null) {
			Cache cache = getCache(name, false);
			if (cache != null)
				cache.clear();
		}
	}

	/**
	 * 获得默认空间缓存大小
	 * 
	 * @return
	 * @throws Exception
	 */
	public final static int size() throws Exception {
		return size(defaultRegion);
	}

	/**
	 * 获得缓存大小
	 * 
	 * @return
	 * @throws Exception
	 */
	public final static int size(String name) throws Exception {
		if (name != null) {
			return getCache(name, true).size();
		}
		return 0;
	}

}
