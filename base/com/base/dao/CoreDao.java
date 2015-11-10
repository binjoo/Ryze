package com.base.dao;

import java.util.List;

import com.base.db.DBHepler;
import com.base.db.DBQuery;

@SuppressWarnings("rawtypes")
public abstract class CoreDao {
	public List queryList(DBQuery query) throws Exception {
		List list = DBHepler.queryList(query.build(), query.getParams());
		return list;
	}
}
