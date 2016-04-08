package com.admin.action;

import com.base.action.CoreAction;
import com.base.utils.CoreMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ToolsAction extends CoreAction {
	@Override
	public CoreMap index(CoreMap inMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public CoreMap updateCache(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if(this.isPost()){
			if(inMap.containsKey("menu")){
				System.out.println("刷新后台菜单缓存");
			}
			if(inMap.containsKey("cache")){
				System.out.println("刷新全站数据缓存");
			}
		}
		out.setOutRender("/admin/tools/update_cache");
		return out;
	}
}
