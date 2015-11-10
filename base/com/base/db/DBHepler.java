package com.base.db;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.ArrayUtils;

import com.base.cache.CacheManager;
import com.base.utils.CoreMap;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class DBHepler {
	private final static QueryRunner queryRunner = new QueryRunner();

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() throws Exception {
		return DBManager.getConnection();
	}

	/**
	 * 查询一条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static CoreMap querySingle(String sql, Object... params)
			throws Exception {
		Map<String, Object> map = queryRunner.query(getConnection(), sql, new MapHandler(), params);
		if (map != null) {
			return new CoreMap(map);
		} else {
			return null;
		}
	}

	public static CoreMap querySingleCache(String name, String key, String sql, Object... params) throws Exception {
		CoreMap out = (CoreMap) CacheManager.get(name, key);
		if (out == null) {
			out = querySingle(sql, params);
			CacheManager.set(name, key, (Serializable) out);
		}
		return out;
	}

	public static List<CoreMap> queryList(String sql, Object... params)
			throws Exception {
		List<Map<String, Object>> list = queryRunner.query(getConnection(), sql, new MapListHandler(), params);
		if (list != null) {
			List<CoreMap> out = new ArrayList<CoreMap>();
			for (int i = 0; i < list.size(); i++) {
				out.add(new CoreMap(list.get(i)));
			}
			return out;
		} else {
			return null;
		}
	}

	public static List<CoreMap> queryListCache(String name, String key, String sql, Object... params) throws Exception {
		List<CoreMap> list = (List<CoreMap>) CacheManager.get(name, key);
		if (list == null) {
			list = queryList(sql, params);
			CacheManager.set(name, key, (Serializable) list);
		}
		return list;
	}
	
	public static long stat(String sql, Object... params) throws Exception{
		CoreMap result = querySingle(sql, params);
		if(result != null){
			return result.getLong("stat");
		}else{
			return 0L;
		}
	}
	
	public static long statCache(String name, String key, String sql, Object... params) throws Exception{
		Long out = (Long) CacheManager.get(name, key);
		if (out == null) {
			out = stat(sql, params);
			CacheManager.set(name, key, (Serializable) out);
		}
		return out;
	}

	public static int insert(String sql, Object... params) throws Exception {
		Map<String, Object> map = queryRunner.insert(getConnection(), sql, new MapHandler(), params);
		if (map != null) {
			return new CoreMap(map).getInt("GENERATED_KEY");
		} else {
			return 0;
		}
	}

	public static int update(String sql, Object... params) throws Exception {
		return queryRunner.update(getConnection(), sql, params);
	}

    public static CoreMap querySysOptions() throws Exception {
        return queryOptions(null, "0");
    }

    public static CoreMap queryOptions(String name) throws Exception {
        return queryOptions(name, null);
    }
    
    public static CoreMap queryOptions(String name, String userId) throws Exception {
    	CoreMap out = (CoreMap) CacheManager.get("SysOptions", "user_id_" + userId);
        List<Object> list = new ArrayList<Object>();
        if(out == null){
            String sql = "select * from sys_options where 1 = 1";
            if(name != null){
                sql += " and name = ?";
                list.add(name);
            }
            if(userId != null){
                sql += " and user_id = ?";
                list.add(userId);
            }
            Object[] params = list.toArray();
            List<CoreMap> result = queryList(sql, params);
            out = optionsListToMap(result);
            CacheManager.set("SysOptions", "user_id_" + userId, (Serializable) out);
        }
        return out;
    }
	

	private static CoreMap optionsListToMap(List list) {
		CoreMap out = new CoreMap();
		for (int i = 0; i < list.size(); i++) {
			CoreMap tmp = (CoreMap) list.get(i);
			out.put(tmp.getString("name"), tmp.getString("value"));
		}
		return out;
	}
}