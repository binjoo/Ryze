package com.common.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.base.dao.CoreDao;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;
import com.base.utils.StrUtils;

@SuppressWarnings({ "unused", "rawtypes" })
public class InviteDao extends CoreDao {
	private static final Logger log = Logger.getLogger(InviteDao.class);

	public String make(int userId) throws Exception {
//		String no = StrUtils.GUID_16();
//		String sql = "insert into sys_invite (no, created_user_id, created) values (?, ?, UNIX_TIMESTAMP())";
//		DBHepler.insert(null, no, userId);
		return null;
	}
	
	public List queryList(DBQuery query) throws Exception {
    	List list = DBHepler.queryList(query.build(), query.getParams());
    	return list;
	}

	public CoreMap isValid(String no) throws Exception {
		String sql = "select * from sys_invite where no = ?";
		CoreMap out = DBHepler.querySingle(sql, no);
		return out;
	}
	
	public int activation(String no, String userId) throws Exception{
		String sql = "update sys_invite set register_user_id = ?, is_valid = 1 where no = ?";
		return DBHepler.update(sql, userId, no);
	}
}
