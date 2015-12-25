package com.common.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.base.dao.CoreDao;
import com.base.db.DBHepler;
import com.base.utils.CoreMap;

@SuppressWarnings({ "unused", "rawtypes" })
public class BookmarkDao extends CoreDao {
	private static final Logger log = Logger.getLogger(BookmarkDao.class);

	public int insert(CoreMap inMap) throws Exception {
//		String sql = "insert into ya_bookmark (user_id, title, long_url, description, created) values (?, ?, ?, ?, UNIX_TIMESTAMP())";
//		int id = DBHepler.insert(null, inMap.getLong("user_id"), inMap.getString("title"), inMap.getString("url"), inMap.getString("description"));
//		return id;
		return 0;
	}

	public int insertRel(CoreMap inMap) throws Exception {
//		String sql = "insert into ya_bookmark_meta_rel (bookmark_id, meta_id, created) values (?, ?, UNIX_TIMESTAMP())";
//		int id = DBHepler.insert(null, inMap.getInt("bookmark_id"), inMap.getInt("meta_id"));
//		return id;
		return 0;
	}

	public List query(CoreMap inMap) throws Exception {
		String sql = "select * from ya_bookmark where user_id = ? order by created desc";
		List list = DBHepler.queryListCache("BookmarkList", "user_id_" + inMap.getString("user_id"), sql, inMap.getLong("user_id"));
		return list;
	}

	public List queryTag(int bookmarkId) throws Exception {
		String sql = "select m.name from ya_bookmark_meta_rel bmr left join ya_meta m on m.id = bmr.meta_id and m.type = 'tag' where bmr.bookmark_id = ? order by bmr.created desc";
		List list = DBHepler.queryList(sql, bookmarkId);
		return list;
	}
}
