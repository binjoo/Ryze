package com.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.druid.stat.TableStat.Name;
import com.base.cache.CacheManager;
import com.base.dao.CoreDao;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;
import com.base.utils.DateUtils;

@SuppressWarnings("unused")
public class KVDao extends CoreDao {
    private static final Logger log = Logger.getLogger(KVDao.class);

    public String insert(CoreMap inMap) throws Exception {
    	CoreMap rows = new CoreMap();
    	rows.put("name", inMap.getString("name"));
    	rows.put("value", inMap.getString("value"));
    	rows.put("type", inMap.getString("type"));
    	rows.put("user_id", inMap.getString("user_id"));

    	DBQuery query = new DBQuery();
    	query.insert().from("sys_kv").rows(rows);
    	//System.out.println(q.build());
    	String id = DBHepler.insert(query);
        return id;
    }


    public int updateMap(CoreMap map) throws Exception {
    	return updateMap(map, "option");
    }

    public int updateMap(CoreMap map, String type) throws Exception {
    	return updateMap(map, type, "0");
    }
    
    public int updateMap(CoreMap map, String type, String user_id) throws Exception {
    	int result = 0;
    	if(map != null){
			CacheManager.remove("kv_" + type, "user_id_" + user_id);
    		for (Object key : map.keySet()) {
    			String name = key.toString();
    			String value = map.getString(name);
    			if(user_id != null && !user_id.equals("")){
    				update(name, value, type, user_id);
    			}else{
    				update(name, value, type);
    			}
    			result++;
    		}
    	}
    	return result;
    }
    
    public int update(String name, String value) throws Exception {
        return update(name, value, "option", "0");
    }
    
    public int update(String name, String value, String type) throws Exception {
        return update(name, value, type, "0");
    }

    public int update(String name, String value, String type, String user_id) throws Exception {
        String sql = "update sys_kv set value = ? where name = ? and type = ? and user_id = ?";
        int result = DBHepler.update(sql, value, name, type, user_id);
        return result;
    }
    
    public CoreMap queryKVOption() throws Exception {
    	return queryKV("option", "0");
    }
    
    public String queryKVOption(String name) throws Exception {
    	CoreMap kvMap = queryKV("option", "0");
    	if(kvMap != null){
    		return kvMap.getString(name);
    	}
    	return null;
    }
    
    /**
     * 根据设置项名称和用户获得设置项
     * @param name
     * @param userId
     * @return
     * @throws Exception
     */
    public CoreMap queryKV(String type, String user_id) throws Exception {
    	CoreMap out = (CoreMap) CacheManager.get("kv_" + type, "user_id_" + user_id);
        List<Object> list = new ArrayList<Object>();
        if(out == null){
            String sql = "select * from sys_kv where 1 = 1";
            if(type != null){
                sql += " and type = ?";
                list.add(type);
            }
            if(user_id != null){
                sql += " and user_id = ?";
                list.add(user_id);
            }
            Object[] params = list.toArray();
            List<CoreMap> result = DBHepler.queryList(sql, params);
            out = optionsListToMap(result);
            CacheManager.set("kv_" + type, "user_id_" + user_id, (Serializable) out);
        }
        return out;
    }
    
    private CoreMap optionsListToMap(List list){
    	CoreMap out = new CoreMap();
    	for (int i = 0; i < list.size(); i++) {
			CoreMap tmp = (CoreMap) list.get(i);
			out.put(tmp.getString("name"), tmp.getString("value"));
		}
    	return out;
    }

}
