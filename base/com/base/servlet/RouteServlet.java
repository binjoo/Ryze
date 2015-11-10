package com.base.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.base.db.DBManager;

@SuppressWarnings("serial")
public class RouteServlet extends HttpServlet {
    private List<String> actionPackages = null;

    public void init() throws ServletException {
        String packages = getInitParameter("packages");
        actionPackages = Arrays.asList(StringUtils.split(packages, ','));

        System.out.println("route servlet init......................");
    }

    public void destroy() {
        System.out.println("route servlet destroy......................");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("route get");
        process(req, res, false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("route post");
        process(req, res, true);
    }

    @SuppressWarnings("rawtypes")
    private void process(HttpServletRequest req, HttpServletResponse res,
            boolean is_post) {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpSession session = request.getSession(false);
//        Map<String, Cookie> cookies = new HashMap<String, Cookie>();
//        Cookie[] cks = request.getCookies();
//        if (cks != null) {
//            for (Cookie ck : cks) {
//                cookies.put(ck.getName(), ck);
//            }
//        }

        System.out.println(req.getRequestURI());
        
        try {
            String uri = req.getRequestURI();
            String[] parts = StringUtils.split(uri, "/");
            if (parts.length < 1) {
                throw new Exception("参数错误！");
            }

            Object action = this.loadAction(parts[0], req, res);
            if(action == null){
                throw new Exception("找不到类！");
            }
            
            String actionMethodName = (parts.length > 1) ? parts[1] : "index";
            Method method = this.loadActionMethod(action, actionMethodName);
            if(method == null){
                throw new Exception("找不到方法！");
            }
            
            HashMap out = null;

            int methodParam = method.getParameterTypes().length;
            switch (methodParam) {
            case 0:
                out = (HashMap) method.invoke(action);
                break;
            default:
                break;
            }
            
            if(out != null){
                String type = ""; //跳转类型
                String render = ""; //传递页面  type = page
                HashMap data = (HashMap) out.get("data"); //传递页面  type = page
                
                //Template temp = cfg.getTemplate("test.ftl");
            }
            
            

        } catch (Exception e) {
            System.out.println("最终出错的地方，找到了没！");
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }
    }

    /**
     * 加载Action类
     * 
     * @param actionName
     * @return
     */
    private Object loadAction(String actionName, HttpServletRequest req, HttpServletResponse res) throws Exception {
        Object action = actions.get(actionName);
        if (action == null) {
            for (String pkg : actionPackages) {
                String cls = pkg + '.' + StringUtils.capitalize(actionName) + "Action";
                action = loadActionOfFullname(actionName, cls, req, res);
                if (action != null) {
                    break;
                }
            }
        }
        return action;
    }

    private Object loadActionOfFullname(String actionName, String cls, HttpServletRequest req, HttpServletResponse res)
            throws IllegalAccessException, InstantiationException {
        Object action = null;
        try {
            action = Class.forName(cls).newInstance();
            try {
                Method initMethod = action.getClass().getMethod("init", HttpServletRequest.class, HttpServletResponse.class);
                initMethod.invoke(action, req, res);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException excp) {
                excp.printStackTrace();
            }
            if (!actions.containsKey(actionName)) {
                synchronized (actions) {
                    actions.put(actionName, action);
                }
            }
        } catch (ClassNotFoundException excp) {
        }
        return action;
    }
    
    /**
     * 获取名为{method}的方法
     * @param action
     * @param method
     * @return
     */
    @SuppressWarnings("unused")
    private Method loadActionMethod(Object action, String method) {
        String key = action.getClass().getSimpleName() + '.' + method;
        Method m = methods.get(key);
        if(m != null) return m;
        for(Method mt : action.getClass().getMethods()){
            if(mt.getModifiers() == Modifier.PUBLIC && mt.getName().equals(method)){
                synchronized(methods){
                    methods.put(key, mt);
                }
                return mt ;
            }
        }
        return null;
    }

    private final static HashMap<String, Object> actions = new HashMap<String, Object>();
    private final static HashMap<String, Method> methods = new HashMap<String, Method>();
}
