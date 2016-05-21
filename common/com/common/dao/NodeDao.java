package com.common.dao;

import com.base.dao.CoreDao;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;

public class NodeDao extends CoreDao {
	public CoreMap queryNodeObjectById(String id) throws Exception {
		DBQuery q = new DBQuery();
		q.select().from("forum_node").where("id = ?", id);
		return DBHepler.querySingle(q);
	}
}
