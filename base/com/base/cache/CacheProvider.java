package com.base.cache;

public interface CacheProvider {

	public Cache buildCache(String regionName, boolean autoCreate) throws Exception;

	public void start() throws Exception;

	public void stop() throws Exception;

}