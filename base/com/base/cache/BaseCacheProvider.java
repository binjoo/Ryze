package com.base.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.base.utils.CoreMap;

public class BaseCacheProvider implements CacheProvider {
	private static final Log log = LogFactory.getLog(BaseCacheProvider.class);
	private CoreMap cacheManager;

	public Cache buildCache(String region, boolean autoCreate) throws Exception {
		BaseCache baseCache = (BaseCache) cacheManager.get(region);
		if (baseCache == null && autoCreate) {
			synchronized (cacheManager) {
				baseCache = new BaseCache();
				cacheManager.put(region, baseCache);
				return baseCache;
			}
		}
		return baseCache;
	}

	public void start() throws Exception {
		cacheManager = new CoreMap();
	}

	public void stop() throws Exception {
		if (cacheManager != null)
			cacheManager = null;
	}

}