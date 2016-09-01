package com.base.cache;

import java.util.List;

import com.base.utils.CoreMap;

public class BaseCache implements Cache {

	private CoreMap cache;

	public BaseCache() {
		cache = new CoreMap();
	}

	public int size() throws Exception {
		return cache.size();
	}

	public long expire() throws Exception {
		return 0;
	}

	public void put(String key, Object value) throws Exception {
		cache.put(key, value);
	}

	public void put(String key, Object value, long expire) throws Exception {
		cache.put(key, value);
	}

	public Object get(String key) throws Exception {
		return cache.get(key);
	}

	public String getString(String key) throws Exception {
		return cache.getString(key);
	}

	public int getInt(String key) throws Exception {
		return cache.getInt(key);
	}

	public Integer getInteger(String key) throws Exception {
		return cache.getInteger(key);
	}

	public List getList(String key) throws Exception {
		return cache.getList(key);
	}

	public void remove(String key) throws Exception {
		cache.remove(key);
	}

	public void clear() throws Exception {
		cache.clear();
	}

	public boolean isExist(String key) throws Exception {
		return cache.containsKey(key);
	}

	public boolean isEmpty(String key) throws Exception {
		return cache.containsKey(key) && cache.get(key) != null && !cache.get(key).equals("") ? true : false;
	}

}
