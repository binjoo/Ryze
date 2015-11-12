package com.common.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.base.action.CoreAction;
import com.base.filter.RouteFilter;
import com.base.utils.CoreMap;
import com.base.utils.Constants;
import com.base.utils.RequestUtils;
import com.config.MenuConfig;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class AdminAction extends CoreAction {
    private static final Logger log = Logger.getLogger(AdminAction.class);
    private final static String CACHE_REGION = "session";
    private final static String COOKIE_NAME = "_reg_key_";
    private static int WIDTH = 120;
    private static int HEIGHT = 40;
    private static int LENGTH = 5;
    private final static Random random = new Random();
    
    private final static HashMap<String, Object> actions = new HashMap<String, Object>();
    private final static HashMap<String, Method> methods = new HashMap<String, Method>();

    public CoreMap index(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        if (!isLogin()) {
            return goToLogin();
        }
        if (!isAllow("manage")){
        	log.debug("没有manage权限...");
        	out.setOutType(Constants.OUT_TYPE__REDIRECT);
        	out.setOutRender("/");
        	return out;
        }
        //初始化菜单栏参数
        inMap = getActionAndMethod(inMap);
        out = execute(inMap);

        out.putAll(getMenu(inMap));
        return out;
    }
    public CoreMap notFound(CoreMap inMap) throws Exception {
        return index(inMap);
    }

    public CoreMap gotoUrl(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        String action = inMap.getString("action");
        String method = inMap.getString("method");
        if(action != null && method != null){
        	out.putAll(getMenu(inMap));
        }else{
        	action = MenuConfig.DEFAULT_HEADER_MENU_KEY;
        	method = MenuConfig.DEFAULT_SIDEBAR_MENU_KEY;
        }
        
        out.put("url", "/admin?action=" + action + "&method=" + method);
		out.setOutRender("/admin/goto");
        return out;
    }

    /**
     * 获得菜单栏参数，初始化默认值
     * @param inMap
     * @return
     */
    private CoreMap getActionAndMethod(CoreMap inMap) throws Exception {
        String actionName = inMap.getString("action") != null ? inMap.getString("action") : "global";
        String methodName = null;
        if(MenuConfig.isExists(actionName)){
        	methodName = inMap.getString("method") != null ? inMap.getString("method") : ((CoreMap) MenuConfig.get(actionName).get(0)).getString("key");
        }else{
        	actionName = MenuConfig.DEFAULT_HEADER_MENU_KEY;
        	methodName = MenuConfig.DEFAULT_SIDEBAR_MENU_KEY;
        }
        inMap.put("action", actionName);
        inMap.put("method", methodName);
        return inMap;
    }

    private CoreMap getMenu(CoreMap inMap) throws Exception  {
        CoreMap out = new CoreMap();
        String actionName = inMap.getString("action");
        String methodName = inMap.getString("method");
        
        out.put("menu_active", actionName);
        out.put("sidebar_active", methodName);
        out.put("header_menu", MenuConfig.getAll());
        out.put("sidebar_menu", MenuConfig.get(actionName));
        return out;
    }
    
    /**
     * 从Admin源码包里查找Class和Action
     * @param Class And Action
     * @return
     */
    private CoreMap execute(CoreMap inMap) throws Exception  {
        CoreMap out = new CoreMap();
        String actionName = inMap.getString("action");
        String methodName = inMap.getString("method");

        CoreAction action = (CoreAction) actions.get(actionName);
        String cls = "com.admin.action." + StringUtils.capitalize(actionName) + "Action";
        
        if(action == null){
            try {
                action = (CoreAction) Class.forName(cls).newInstance();
            } catch (ClassNotFoundException excp) {
            }
            if (action != null && !actions.containsKey(actionName)) {
                synchronized (actions) {
                    actions.put(actionName, action);
                }
            }else{
                throw new Exception("Class Not Found " + cls);
            }
        }
        
        action.setAction(getAction());
        action.setParts(getParts());
        action.setRequest(getRequest());
        action.setResponse(getResponse());
        action.setSession(getSession());
        action.setCookies(getCookies());
        
        String key = action.getClass().getSimpleName() + '.' + methodName;
        Method method = methods.get(key);
        if(method == null){
            try {
                method = action.getClass().getMethod(methodName, CoreMap.class);
            } catch (NoSuchMethodException e) {
                throw new Exception("Method Not Found " + key);
            }
            if (method != null && !methods.containsKey(key)) {
                synchronized (methods) {
                    methods.put(key, method);
                }
            }
        }

        out =  (CoreMap) method.invoke(action, inMap);
        
        return out;
    }

}
