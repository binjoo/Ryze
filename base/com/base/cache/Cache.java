package com.base.cache;

public interface Cache {
	/**
	 * 缓存数量
	 * 
	 * @return
	 */
	public int size() throws Exception;

	/**
	 * 缓存有效时间
	 * 
	 * @return
	 */
	public long expire() throws Exception;

	/**
	 * 添加缓存，默认时间
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) throws Exception;

	/**
	 * 添加缓存，自定义时间
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void put(String key, Object value, long expire) throws Exception;

	/**
	 * 获得缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) throws Exception;

	/**
	 * 移除缓存
	 * 
	 * @param key
	 */
	public void remove(String key) throws Exception;

	/**
	 * 清空缓存
	 */
	public void clear() throws Exception;

	/**
	 * 判断缓存是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean isExist(String key) throws Exception;

	/**
	 * 判断缓存是否为空
	 * 
	 * @param key
	 * @return
	 */
	public boolean isEmpty(String key) throws Exception;
}
