package com.common.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.base.action.CoreAction;
import com.base.bean.Menu;
import com.base.utils.Constants;
import com.base.utils.CoreMap;
import com.base.utils.StrUtils;
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
        
        if(inMap.containsKey("action")){
        	if(!inMap.containsKey("method")){
        		String action = inMap.getString("action");
        		String method = "";
        		List<Menu> menuList = (List<Menu>) MenuConfig.getMenuMap().get(action);
        		if(menuList != null){
        			method = menuList.get(0).getAction();
        		}
        		inMap.put("method", method);
        	}
            return execute(inMap);
        }else{
            out.put("admin_menu", MenuConfig.getMenuList());
        	out.setOutRender("/admin/index");
        }
        return out;
    }
    
    public CoreMap notFound(CoreMap inMap) throws Exception {
        return index(inMap);
    }

    public CoreMap gotoUrl(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        String action = inMap.getString("action");
        String method = inMap.getString("method");
        out.put("url", "/admin?action=" + action + "&method=" + method);
		out.setOutRender("/admin/goto");
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
        methodName = StrUtils.replaceUnderlineAndFirstLetterToUpper(methodName, false);

        CoreAction action = (CoreAction) actions.get(actionName);
        String cls = "com.admin.action." + StrUtils.replaceUnderlineAndFirstLetterToUpper(actionName) + "Action";
        
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
