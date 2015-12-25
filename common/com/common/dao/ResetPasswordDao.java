package com.common.dao;

import com.base.dao.CoreDao;
import com.base.db.DBHepler;
import com.base.utils.CoreMap;
import com.base.utils.DateUtils;
import com.base.utils.StrUtils;

public class ResetPasswordDao extends CoreDao {
	public int insert(CoreMap inMap) throws Exception {
//		long valid = DateUtils.getTime() + 60 * 60;
//		String sql = "insert into ya_reset_password (user_id, token, valid_time, create_ip) values (?, ?, ?, ?)";
//		int result = DBHepler.insert(null, inMap.getString("user_id"), inMap.getString("token"), valid, inMap.getString("create_ip"));
//		return result;
		return 0;
	}

	public int updateStatus(CoreMap inMap) throws Exception {
		String sql = "update ya_reset_password set reset_ip = ?, status = 1 where id = ?";
		int result = DBHepler.update(sql, inMap.getString("reset_id"), inMap.getLong("id"));
		return result;
	}

	public CoreMap checkResetToken(CoreMap inMap) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select rp.* from ya_reset_password rp ");
		sql.append("left join sys_user u on u.id = rp.user_id ");
		sql.append("where u.email = ? and rp.token = ? ");
		CoreMap out = DBHepler.querySingle(sql.toString(), inMap.getString("email"), inMap.getString("token"));
		return out;
	}
}
