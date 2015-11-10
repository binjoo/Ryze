package com.admin.action;

import java.util.List;

import com.base.action.CoreAction;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CoreMap;
import com.common.dao.GroupDao;
import com.common.dao.InviteDao;
import com.common.dao.UserDao;

public class ExtendAction extends CoreAction {
	public CoreMap index(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        System.out.println("extend index");
        return out;
	}

	public CoreMap invite(CoreMap inMap) throws Exception {
	    CoreMap out = new CoreMap();
	    int page = 1;	//当前页数
	    if(getParts().length >= 4){
	    	page = Integer.parseInt(getParts()[3]);
	    }
	    InviteDao iDao = new InviteDao();
	    inMap.put("page", page);
	    inMap.put("size", 10);

    	DBQuery q = new DBQuery();
    	q.select().from("sys_invite");
    	if(inMap.containsKey("page") && inMap.containsKey("size")){
    		q.page(inMap.getInt("page"), inMap.getInt("size"));
    	}
//    	if(inMap.containsKey("email") && !inMap.getString("email").equals("")){
//    		q.where("email like ?", "%" + inMap.getString("email") + "%");
//    	}
//    	if(inMap.containsKey("nickname") && !inMap.getString("nickname").equals("")){
//    		q.where("nickname like ?", "%" + inMap.getString("nickname") + "%");
//    	}
        q.order("id", DBQuery.SORT_ASC);
	    List list = iDao.queryList(q);
	    out.put("invites", list);

	    long count = DBHepler.stat(q.buildCount(), q.getParams());	//结果总数
	    out.put("count", count);
	    out.put("page", inMap.getInt("page"));
	    out.put("size", inMap.getInt("size"));
	    out.setOutRender("/extend/invite");
		return out;
	}

	public CoreMap group(CoreMap inMap) throws Exception {
	    CoreMap out = new CoreMap();
	    if(getParts().length >= 5){
	    	if(getParts()[3].equals("config")){
	    		return groupConfig(inMap);
	    	}
	    }

    	DBQuery q = new DBQuery();
    	q.select().from("sys_group");
    	q.order("manage", DBQuery.SORT_DESC);
    	
    	GroupDao gd = new GroupDao();
	    List list = gd.queryList(q);
	    
	    out.put("groups", list);
	    out.setOutRender("/user/group");

		return out;
	}

	private CoreMap groupConfig(CoreMap inMap) throws Exception {
	    CoreMap out = new CoreMap();
	    GroupDao gd = new GroupDao();
	    int id = Integer.valueOf(getParts()[4]).intValue();
	    CoreMap group = gd.queryGroup(id);
	    out.put("group", group);
	    out.setOutRender("/user/group_config");
		return out;
	}
}
