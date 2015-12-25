package com.common.dao;

import org.apache.log4j.Logger;

import com.base.dao.CoreDao;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;
import com.base.utils.StrUtils;

@SuppressWarnings({ "unused", "rawtypes" })
public class MetaDao extends CoreDao {
	private static final Logger log = Logger.getLogger(MetaDao.class);

	public String insertTag(CoreMap inMap) throws Exception {
		String sql = "insert into ya_meta (name, type) values (?, 'tag')";
		//int id = DBHepler.insert(null, inMap.getString("name"));
		String id = DBHepler.insert(null);
		return id;
	}

	public CoreMap queryTag(CoreMap inMap) throws Exception {
		String sql = "select * from ya_meta where type = 'tag' and name = ?";
		CoreMap out = DBHepler.querySingle(sql, inMap.getString("name"));
		return out;
	}
	
	public String getTagId(String name) throws Exception{
		CoreMap inMap = new CoreMap();
		inMap.put("name", name);
		CoreMap tag = queryTag(inMap);
		if(tag != null){
			return tag.getString("id");
		}else{
			return insertTag(inMap);
		}
	}
}
