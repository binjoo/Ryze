package com.base.utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ActionUtils {
	public static List makeBreadcrumb(String... params) throws Exception {
		List list = new ArrayList();
		int length = params.length;
		if (params.length % 2 == 0) {
			throw new Exception("参数错误！");
		}
		int forNum = length / 2 + 1;

		for (int i = 0; i < forNum; i++) {
			CoreMap map = new CoreMap();
			map.put("name", params[i * 2]);
			if (i + 1 != forNum) {
				map.put("url", params[i * 2 + 1]);
			}
			list.add(map);
		}
		return list;
	}
}
