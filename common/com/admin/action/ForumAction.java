package com.admin.action;

import java.util.List;

import com.base.action.CoreAction;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;
import com.common.dao.NodeDao;
import com.common.dao.UserDao;

public class ForumAction extends CoreAction {
	public CoreMap index(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		System.out.println("user index");
		return out;
	}

	public CoreMap node(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		NodeDao nodeDao = new NodeDao();
		DBQuery q = new DBQuery();
		q.select().from("forum_node").order("created", DBQuery.SORT_DESC);
		List list = nodeDao.queryList(q);
	    out.put("nodes", list);
		out.setOutRender("/admin/forum/node");
		return out;
	}

	public CoreMap topic(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		NodeDao nodeDao = new NodeDao();
	    int page = 1;	//当前页数
	    if(getParts().length >= 4){
	    	page = Integer.parseInt(getParts()[3]);
	    }
	    inMap.put("page", page);
	    inMap.put("size", 10);
	    
		DBQuery q = new DBQuery();
		q.select("ft.*, fn.name").from("forum_topic ft").join("forum_node fn", "ft.node_id = fn.id").order("ft.created", DBQuery.SORT_DESC);
    	if(inMap.containsKey("page") && inMap.containsKey("size")){
    		q.page(inMap.getInt("page"), inMap.getInt("size"));
    	}
		List list = nodeDao.queryList(q);
	    out.put("topics", list);
	    

	    long count = DBHepler.stat(q.buildCount(), q.getParams());	//结果总数
	    out.put("count", count);
	    out.put("page", inMap.getInt("page"));
	    out.put("size", inMap.getInt("size"));
		out.setOutRender("/admin/forum/topic");
		return out;
	}
	

}
