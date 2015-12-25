package com.common.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.base.dao.CoreDao;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;
import com.base.utils.DateUtils;
import com.base.utils.StrUtils;

@SuppressWarnings({ "unused", "rawtypes" })
public class NoteDao extends CoreDao {
	private static final Logger log = Logger.getLogger(NoteDao.class);

    public String insert(DBQuery query) throws Exception {
    	String id = DBHepler.insert(query);
        return id;
    }
    
    public int update(DBQuery query) throws Exception {
    	int id = DBHepler.update(query.build(), query.getParams());
        return id;
    }

    public String update(CoreMap inMap) throws Exception {
    	CoreMap rows = new CoreMap();
    	rows.put("parent_id", inMap.getString("parent_id"));
    	rows.put("user_id", inMap.getString("user_id"));
    	rows.put("type", inMap.getString("type"));
    	rows.put("title", inMap.getString("title"));
    	rows.put("content", inMap.getString("content"));
    	rows.put("syntax", inMap.getString("syntax"));
    	rows.put("status", inMap.getString("status"));
    	rows.put("created", DateUtils.getTime());
    	String id = null;
    	DBQuery query = new DBQuery();
    	query.from("note_content").rows(rows);
    	if(inMap.getString("id") != null && !inMap.getString("id").equals("")){
    		id = inMap.getString("id");
    		query.update().where("id = ?", id);
        	DBHepler.update(query.build(), query.getParams());
    	}else{
    		query.insert();
    		id = DBHepler.insert(query);
    	}
        return id;
    }
    
    public List queryList(DBQuery query) throws Exception {
    	List list = DBHepler.queryList(query.build(), query.getParams());
    	return list;
    }
    
    public CoreMap querySingle(DBQuery query) throws Exception {
        CoreMap out = DBHepler.querySingle(query.build(), query.getParams());
        return out;
    }
}
