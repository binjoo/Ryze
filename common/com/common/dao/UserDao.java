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
public class UserDao extends CoreDao {
    private static final Logger log = Logger.getLogger(UserDao.class);

    public int insert(CoreMap inMap) throws Exception {
    	CoreMap rows = new CoreMap();
    	rows.put("email", inMap.getString("email"));
    	rows.put("nickname", inMap.getString("nickname"));
    	rows.put("password", inMap.getString("password"));
    	rows.put("created", DateUtils.getTime());

    	DBQuery q = new DBQuery();
    	q.insert().from("sys_user").rows(rows);
    	int id = DBHepler.insert(q.build(), q.getParams());
        return id;
    }

    public int update(CoreMap inMap) throws Exception {
    	CoreMap rows = new CoreMap();
    	rows.put("password", inMap.getString("password"));

    	DBQuery q = new DBQuery();
    	q.update().from("sys_user").rows(rows);
    	q.where("id = ?", inMap.getString("user_id"));
    	int result = DBHepler.update(q.build(), q.getParams());
        return result;
    }
    
    public CoreMap checkLogin(CoreMap inMap) throws Exception {
    	DBQuery q = new DBQuery();
    	q.select().from("sys_user");
    	q.where("email = ?", inMap.getString("email"));
    	q.where("password = ?", inMap.getString("password"));
        CoreMap out = DBHepler.querySingle(q.build(), q.getParams());
        return out;
    }
    
    public CoreMap checkNickname(String nickname) throws Exception {
    	DBQuery q = new DBQuery();
    	q.select().from("sys_user");
    	q.where("nickname = ?", nickname);
        CoreMap out = DBHepler.querySingle(q.build(), q.getParams());
        return out;
    }
    
    public CoreMap checkEmail(String email) throws Exception {
    	DBQuery q = new DBQuery();
    	q.select().from("sys_user");
    	q.where("email = ?", email);
        CoreMap out = DBHepler.querySingle(q.build(), q.getParams());
        return out;
    }
    
    public CoreMap checkUserId(String userId) throws Exception {
    	DBQuery q = new DBQuery();
    	q.select().from("sys_user");
    	q.where("id = ?", userId);
        CoreMap out = DBHepler.querySingle(q.build(), q.getParams());
        return out;
    }
    
    public List queryList(DBQuery query) throws Exception {
    	List list = DBHepler.queryList(query.build(), query.getParams());
    	return list;
    }

}
