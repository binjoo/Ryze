package com.common.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.base.dao.CoreDao;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;
import com.base.utils.DateUtils;

@SuppressWarnings({ "unused", "rawtypes" })
public class GroupDao extends CoreDao {
    private static final Logger log = Logger.getLogger(GroupDao.class);
    
    public List queryList(DBQuery query) throws Exception {
    	List list = DBHepler.queryList(query.build(), query.getParams());
    	return list;
    }
    
    public CoreMap queryGroup(int id) throws Exception {
    	DBQuery q = new DBQuery();
    	q.select().from("sys_group").where("id = ?", id);
        CoreMap out = DBHepler.querySingle(q.build(), q.getParams());
        return out;
    }
}
