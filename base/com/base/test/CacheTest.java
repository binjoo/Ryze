package com.base.test;

import java.util.Map;

import com.base.cache.CacheManager;
import com.base.utils.CoreMap;

public class CacheTest {
	public static void main(String[] args) {
		try {
			String region = "d";
			CoreMap map = new CoreMap();
			map.put("username", "liangj");
			map.put("password", "123789");
			map.put("age", "28");
			map.put("sex", "男");
			map.put("address", "福田区岗厦村东二坊95栋601");
			CacheManager.set(region, "user", map);
			System.out.println(CacheManager.size(region));
			CoreMap tmp = (CoreMap) CacheManager.get(region, "user");

			for (Map.Entry entry : tmp.entrySet()) {
                System.out.println((String) entry.getKey() + " => " + ((String) entry.getValue()));
			}
			
			CacheManager.remove(region, "user");
			System.out.println(CacheManager.size(region));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
