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
        String[] caa = getClassAndAction();

        out = execute(caa);
//        if(out.getOutType() != null && out.getOutType().equals(Constants.OUT_TYPE__REDIRECT)){
//        	out.setOutRender("/admin" + out.getOutRender());
//        }

        out.putAll(getMenu(caa));
        return out;
    }

    public CoreMap notFound(CoreMap inMap) throws Exception {
        return index(inMap);
    }

    public CoreMap gotoUrl(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        String url = inMap.getString("url");
        if(url.indexOf("/admin/") != -1){
        	url = url.substring(url.indexOf("admin") + 6, url.length());
        	String[] menu = url.split("/");
        	out.putAll(getMenu(menu));
        }
        out.put("url", inMap.getString("url"));
		out.setOutRender("/admin/goto");
        return out;
    }

    private String[] getClassAndAction() {
        String top = "global";
        String sidebar = "index";

        if (getParts().length >= 2 && MenuConfig.isExists(getParts()[1])) {
            top = getParts()[1];
        }

        if (getParts().length >= 3) {
            sidebar = getParts()[2];
        } else {
            List list = (List) MenuConfig.get(top).get(0);
            CoreMap map = (CoreMap) list.get(0);
            sidebar = map.getString("key");
        }
        return new String[] { top, sidebar };
    }

    private CoreMap getMenu(String[] caa) {
        CoreMap out = new CoreMap();
        out.put("menu_active", caa[0]);
        out.put("sidebar_active", caa[1]);
        out.put("menu", MenuConfig.getAll());
        out.put("sidebar_menu", MenuConfig.get(caa[0]));
        return out;
    }

    /**
     * 从Admin源码包里查找Class和Action
     * @param Class And Action
     * @return
     */
    private CoreMap execute(String[] caa) throws Exception  {
        CoreMap out = new CoreMap();
        String actionName = caa[0];
        String methodName = caa[1];
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

        CoreMap inMap = RequestUtils.getInMap(getRequest());
        inMap.put("parts", getParts());
        out =  (CoreMap) method.invoke(action, inMap);
        
        return out;
    }

}
