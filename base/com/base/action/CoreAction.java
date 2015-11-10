package com.base.action;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.base.utils.CoreMap;
import com.base.utils.Constants;

public abstract class CoreAction {
    private String action; // 类名称
    private String method; // 方法名
    private String[] parts;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Map<String, Cookie> cookies;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String[] getParts() {
        return parts;
    }

    public void setParts(String[] parts) {
        this.parts = parts;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpSession getSession() {
        if (session == null) {
            return request.getSession(true);
        }
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public Map<String, Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, Cookie> cookies) {
        this.cookies = cookies;
    }

    public abstract CoreMap index(CoreMap inMap) throws Exception;

    public CoreMap notFound(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        out.setOutType(Constants.OUT_TYPE__REDIRECT);
        return out;
    }

    /**
     * 客户端是否为GET请求方式
     * 
     * @return
     */
    protected boolean isGet() {
        if (this.getRequest().getMethod().equals("GET")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 客户端是否为POST请求方式
     * 
     * @return
     */
    protected boolean isPost() {
        if (this.getRequest().getMethod().equals("POST")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否Ajax请求
     * 
     * @return
     */
    protected boolean isAjax() {
        if (this.getRequest().getHeader("X-Requested-With") != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用户是否登陆状态
     * 
     * @return
     */
    protected boolean isLogin() {
        if (this.getSession().getAttribute("login") != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否有权限
     * @param name
     * @return
     */
    protected boolean isAllow(String name) {
    	CoreMap allow = (CoreMap) this.getSession().getAttribute("allow");
        if (allow != null) {
        	if(allow.getBool("allow_" + name)){
                return true;
        	}else{
        		return false;
        	}
        } else {
            return false;
        }
	}

    /**
     * 返回登陆用户信息
     * 
     * @return
     */
    protected CoreMap getLoginInfo() {
        if (isLogin()) {
            return (CoreMap) getSession().getAttribute("login");
        }
        return null;
    }

    /**
     * 跳转到登陆页面。
     * 
     * @return
     */
    protected CoreMap goToLogin() {
        CoreMap map = new CoreMap();
        String callbackURL = getRequest().getRequestURL().toString();
        if (getRequest().getQueryString() != null) {
            callbackURL += "?" + getRequest().getQueryString();
        }
        map.setOutType(Constants.OUT_TYPE__REDIRECT);
        map.setOutRender("/user/login?callback=" + callbackURL);
        return map;
    }
}
