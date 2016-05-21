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
		if(isAjax()){
			if(inMap.containsKey("id")){
				out.put("node", nodeDao.queryNodeObjectById(inMap.getString("id")));
			}
			return out.RenderJson();
		}else if(isPost()){
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

		DBQuery parents = new DBQuery();
		parents.select().from("forum_node").where("parent_id = ?", "").order("turn", DBQuery.SORT_ASC);
		List parentsList = nodeDao.queryList(parents);
	    out.put("parents", parentsList);

		DBQuery nodes = new DBQuery();
		nodes.select("fn.*, fnp.name as parent_name, fnp.turn as parent_turn").from("forum_node fn").join("forum_node fnp", "fn.parent_id = fnp.id");
		nodes.where("fn.parent_id <> ?", "").order("fnp.turn", DBQuery.SORT_ASC).order("fn.turn", DBQuery.SORT_ASC);
		List nodesList = nodeDao.queryList(nodes);
	    out.put("nodes", nodesList);

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
