package com.admin.action;

import java.util.List;

import com.base.action.CoreAction;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;
import com.common.dao.NodeDao;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ForumAction extends CoreAction {
	NodeDao nodeDao = new NodeDao();
	public CoreMap index(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		System.out.println("user index");
		return out;
	}
	
	public CoreMap nodeEdit (CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if(isPost()){
			CoreMap node = new CoreMap();
			node.put("parent_id", inMap.getString("parent_id"));
			node.put("no", inMap.getString("no"));
			node.put("name", inMap.getString("name"));
			node.put("description", inMap.getString("description"));
			DBQuery q = new DBQuery();
			q.insert().from("forum_node").rows(node);
			nodeDao.insert(q);
			out.put("message", "保存成功！");
		}
		return node(out);
	}

	public CoreMap node(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		
		if(inMap.containsKey("message")){
			out.put("message", inMap.getString("message"));
		}
		
		DBQuery q = new DBQuery();
		q.select().from("forum_node").order("created", DBQuery.SORT_DESC);
		List list = nodeDao.queryList(q);
	    out.put("nodes", list);

		DBQuery parent = new DBQuery();
		parent.select().from("forum_node").where("parent_id = ''").order("created", DBQuery.SORT_DESC);
		List parentList = nodeDao.queryList(q);
	    out.put("parent", parentList);
		out.setOutRender("/admin/forum/node_list");
		return out;
	}

	public CoreMap topic(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
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
