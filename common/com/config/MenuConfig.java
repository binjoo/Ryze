package com.config;

import java.util.ArrayList;
import java.util.List;

import com.base.utils.CoreMap;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuConfig {
    public final static List menuList = new ArrayList();
    public final static CoreMap menuMap = new CoreMap();
    
    static {
        menuList.add(add("global", "全局", "/global"));
        menuMap.put("global", initGlobal("/global"));
        
        menuList.add(add("user", "用户", "/user"));
        menuMap.put("user", initUser("/user"));
        
        menuList.add(add("extend", "论坛", "/forum"));
        menuMap.put("forum", initForum("/forum"));
        
        menuList.add(add("wechat", "微信", "/wechat"));
        menuMap.put("wechat", initWechat("/wechat"));
        
        menuList.add(add("extend", "运营", "/extend"));
        menuMap.put("extend", initExtend("/extend"));
    }
    
    private static List initGlobal(String prefix){
        List tmp = new ArrayList();
        
        List tmp_a = new ArrayList();
        tmp_a.add(add("index", "后台首页", prefix + "/index"));
        tmp_a.add(add("base", "站点信息", prefix + "/base"));
        tmp_a.add(add("access", "注册控制", prefix + "/access"));
        tmp_a.add(add("qiniu", "七牛云存储", prefix + "/qiniu"));
        tmp.add(tmp_a);
        
        List tmp_b = new ArrayList();
        tmp_b = new ArrayList();
        tmp_b.add(add("email", "邮件服务", prefix + "/email"));
        tmp.add(tmp_b);
        
        return tmp;
    }

	private static Object initForum(String prefix) {
        List tmp = new ArrayList();

        List tmp_a = new ArrayList();
        tmp_a.add(add("node", "节点管理", prefix + "/node"));
        tmp_a.add(add("topic", "主题管理", prefix + "/topic"));
        tmp_a.add(add("recycle", "回收站", prefix + "/recycle"));
        tmp.add(tmp_a);
		return tmp;
	}

	private static Object initUser(String prefix) {
        List tmp = new ArrayList();

        List tmp_a = new ArrayList();
        tmp_a.add(add("manage", "用户管理", prefix + "/manage"));
        tmp_a.add(add("group", "用户组", prefix + "/group"));
        tmp.add(tmp_a);
        
        List tmp_b = new ArrayList();
        tmp_b.add(add("sendmsg", "发送通知", prefix + "/sendmsg"));
        tmp.add(tmp_b);
        
        List tmp_c = new ArrayList();
        tmp_c.add(add("ipblack", "用户黑名单", prefix + "/ipblack"));
        tmp_c.add(add("userblack", "IP黑名单", prefix + "/userblack"));
        tmp.add(tmp_c);
		return tmp;
	}

    private static Object initWechat(String prefix) {
        List tmp = new ArrayList();

        List tmp_a = new ArrayList();
        tmp_a.add(add("base", "基础设置", prefix + "/base"));
        tmp_a.add(add("third", "第三方平台", prefix + "/third"));
        tmp.add(tmp_a);
		return tmp;
	}

	private static Object initExtend(String prefix) {
        List tmp = new ArrayList();

        List tmp_a = new ArrayList();
        tmp_a.add(add("invite", "邀请码", prefix + "/invite"));
        tmp.add(tmp_a);
		return tmp;
	}


	private static CoreMap add(String key, String nane, String url) {
        CoreMap menu = new CoreMap();
        menu.put("key", key);
        menu.put("name", nane);
        menu.put("url", url);
        return menu;
    }
    
    public static boolean isExists(String key){
        if(menuMap.containsKey(key)){
            return true;
        }else{
            return false;
        }
    }

    public static List get(String key) {
        return menuMap.getList(key);
    }

    public static List getAll() {
        return menuList;
    }
}
