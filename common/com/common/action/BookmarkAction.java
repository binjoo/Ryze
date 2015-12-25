package com.common.action;

import java.util.ArrayList;
import java.util.List;

import com.base.action.CoreAction;
import com.base.utils.ActionUtils;
import com.base.utils.CoreMap;
import com.base.utils.Constants;
import com.base.utils.StrUtils;
import com.common.dao.BookmarkDao;
import com.common.dao.InviteDao;
import com.common.dao.MetaDao;
import com.common.dao.UserDao;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BookmarkAction extends CoreAction {

    @Override
    public CoreMap index(CoreMap inMap) throws Exception {
	    if(!isLogin()){
	        return goToLogin();
	    }
	    CoreMap tmp = new CoreMap();
	    tmp.put("user_id", getLoginInfo().getInt("id"));
	    List list = new BookmarkDao().query(tmp);
	    
	    for (int i = 0; i < list.size(); i++) {
			CoreMap itmp = (CoreMap) list.get(i);
			List tags = new BookmarkDao().queryTag(itmp.getInt("id"));
			if(tags != null && tags.size() > 0){
				itmp.put("tags", tags);
				list.set(i, itmp);
			}
		}
	    
    	CoreMap out = new CoreMap();
		out.put("breadcrumb", ActionUtils.makeBreadcrumb("书签"));
		out.put("list", list);
    	out.setOutRender("/bookmark/index");
        return out;
    }

    public CoreMap add(CoreMap inMap) throws Exception {
    	CoreMap out = new CoreMap();
		List list = new ArrayList();
	    if(!isLogin()){
	        return goToLogin();
	    }
    	if(isAjax()){
            out.setOutType(Constants.OUT_TYPE__JSON);
    	}
    	if(isPost()){
    		CoreMap param = new CoreMap();
	    	String url = inMap.getString("url");
            if(url != null && !url.equals("")){
				if(StrUtils.isNotUrl(url)){
                	list.add("url#网址格式不正确！");
				}
            }else{
            	list.add("url#网址不能为空！");
            }
            param.put("url", url);
            
	    	String title = inMap.getString("title");
            if(title == null || title.equals("")){
            	list.add("title#网址不能为空！");
            }
            param.put("title", title);
            
            String tags = inMap.getString("tags");
            String description = inMap.getString("description");
            param.put("description", description);

            if(list.size() > 0){
            	out.put("message", list);
            	return out;
            }
            
            //校验通过，进入数据库操作
        	param.put("user_id", getLoginInfo().getString("id"));
        	int bid = new BookmarkDao().insert(param);
        	if(tags != null && !tags.equals("")){
        		tags = tags.replaceAll("，", ",");
        		tags = tags.replaceAll("；", ",");
        		tags = tags.replaceAll("、", ",");
        		tags = tags.replaceAll(";", ",");
        		String[] tagarray = tags.split(",");
        		for (int i = 0; i < tagarray.length; i++) {
					String tagId = new MetaDao().getTagId(tagarray[i]);
					CoreMap tmp = new CoreMap();
					tmp.put("bookmark_id", bid);
					tmp.put("meta_id", tagId);
					new BookmarkDao().insertRel(tmp);
				}
        	}
    	}else if(isGet()){
    		out.put("breadcrumb", ActionUtils.makeBreadcrumb("书签", "/bookmark", "新增"));
    	}
    	out.setOutRender("/bookmark/add");
        return out;
    }

}
