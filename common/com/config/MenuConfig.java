package com.config;

import java.util.ArrayList;
import java.util.List;

import com.base.utils.CoreMap;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuConfig {
	private final static int TYPE_HEADER_MENU = 0x01;
	private final static int TYPE_SIDEBAR_MENU = 0x02;
	private final static int TYPE_SIDEBAR_MENU_HR = 0x03;
	
    public final static List headerMenuList = new ArrayList();
    public final static CoreMap sidebarMenuMap = new CoreMap();

    private static List tmpMenuList = new ArrayList();
    
    static {
        tmpMenuList.add(addHeaderMenu("global", "全局"));
        tmpMenuList.add(addSidebarMenu("index", "后台首页", "global"));
        tmpMenuList.add(addSidebarMenu("base", "站点信息", "global"));
        tmpMenuList.add(addSidebarMenu("access", "注册控制", "global"));
        tmpMenuList.add(addSidebarMenu("qiniu", "七牛云存储", "global"));
        tmpMenuList.add(addSidebarMenuHr("global"));
        tmpMenuList.add(addSidebarMenu("email", "邮件服务", "global"));
        
        tmpMenuList.add(addHeaderMenu("user", "用户"));
        tmpMenuList.add(addSidebarMenu("manage", "用户管理", "user"));
        tmpMenuList.add(addSidebarMenu("group", "用户组", "user"));
        tmpMenuList.add(addSidebarMenuHr("user"));
        tmpMenuList.add(addSidebarMenu("sendmsg", "发送通知", "user"));
        tmpMenuList.add(addSidebarMenuHr("user"));
        tmpMenuList.add(addSidebarMenu("ipblack", "用户黑名单", "user"));
        tmpMenuList.add(addSidebarMenu("userblack", "IP黑名单", "user"));
        
        tmpMenuList.add(addHeaderMenu("forum", "论坛"));
        tmpMenuList.add(addSidebarMenu("node", "节点管理", "forum"));
        tmpMenuList.add(addSidebarMenu("topic", "主题管理", "forum"));
        tmpMenuList.add(addSidebarMenu("recycle", "回收站", "forum"));
    	initMenu();	//开始初始化后台菜单
    }
    
    private static void initMenu(){
    	for (int i = 0; i < tmpMenuList.size(); i++) {
			CoreMap tmpMenu = (CoreMap) tmpMenuList.get(i);
			String key = tmpMenu.getString("key");
			String action = tmpMenu.getString("action");
			int type = tmpMenu.getInt("type");
			if(type == TYPE_HEADER_MENU){
				headerMenuList.add(tmpMenu);
			}else{
				List tmpSidebar = new ArrayList();
				if(sidebarMenuMap.containsKey(action)){
					tmpSidebar = sidebarMenuMap.getList(action);
				}
				if(type == TYPE_SIDEBAR_MENU){
					tmpSidebar.add(tmpMenu);
				}else{
					tmpSidebar.add(null);
				}
				sidebarMenuMap.put(action, tmpSidebar);
			}
		}
    }
    
	private static CoreMap addHeaderMenu(String key, String name) {
        return addHeaderMenu(key, name, key);
    }
	private static CoreMap addHeaderMenu(String key, String name, String action) {
        CoreMap menu = new CoreMap();
        menu.put("type", TYPE_HEADER_MENU);
        menu.put("key", key);
        menu.put("name", name);
        menu.put("url", "?action=" + action);
        return menu;
    }
	private static CoreMap addSidebarMenu(String key, String name, String action) {
		return addSidebarMenu(key, name, action, key);
	}
	private static CoreMap addSidebarMenu(String key, String name, String action, String method) {
        CoreMap menu = new CoreMap();
        menu.put("type", TYPE_SIDEBAR_MENU);
        menu.put("key", key);
        menu.put("name", name);
        menu.put("action", action);
        menu.put("url", "?action=" + action + "&method=" + method);
        return menu;
    }
	private static CoreMap addSidebarMenuHr(String action) {
        CoreMap menu = new CoreMap();
        menu.put("type", TYPE_SIDEBAR_MENU_HR);
        menu.put("action", action);
		return menu;
	}
    
    public static boolean isExists(String key){
        if(sidebarMenuMap.containsKey(key)){
            return true;
        }else{
            return false;
        }
    }

    public static List get(String key) {
        return sidebarMenuMap.getList(key);
    }

    public static List getAll() {
        return headerMenuList;
    }
}
