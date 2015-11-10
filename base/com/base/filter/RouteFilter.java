package com.base.filter;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.base.action.CoreAction;
import com.base.db.DBManager;
import com.base.utils.AppConfig;
import com.base.utils.CharsetUtils;
import com.base.utils.CoreMap;
import com.base.utils.Constants;
import com.base.utils.RequestUtils;

import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings({ "unused" })
public class RouteFilter implements Filter {
	private static final Logger log = Logger.getLogger(RouteFilter.class);

	private List<String> actionPackages = null;
	private ServletContext context;
	private Configuration cfg;

	private HashMap<String, Configuration> templates = new HashMap<String, Configuration>();
	private List<String> ignoreURIs = new ArrayList<String>();
	private List<String> ignoreExts = new ArrayList<String>();

	private final static HashMap<String, Object> actions = new HashMap<String, Object>();
	private final static HashMap<String, Method> methods = new HashMap<String, Method>();

	public void init(FilterConfig cfg) throws ServletException {
		try {
			freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.context = cfg.getServletContext();
		/**
		 * 初始化前台引擎模版
		 */
		String pathPrefix = AppConfig.getPro("templatePathPrefix");
		if (pathPrefix == null || pathPrefix.equals("")) {
			pathPrefix = "default";
		}
		this.cfg = new Configuration();
		this.cfg.setDefaultEncoding(CharsetUtils.UTF_8);
		this.cfg.setClassForTemplateLoading(this.getClass(), "/../" + pathPrefix);
		this.cfg.setObjectWrapper(new DefaultObjectWrapper());

		/**
		 * 获得ACTION包
		 */
		String packages = cfg.getInitParameter("packages");
		this.actionPackages = Arrays.asList(StringUtils.split(packages, ','));

		String ignores = cfg.getInitParameter("ignore");
		if (ignores != null) {
			for (String ig : StringUtils.split(ignores, ',')) {
				ignoreURIs.add(ig.trim());
			}
		}

		ignores = cfg.getInitParameter("ignoreExts");
		if (ignores != null) {
			for (String ig : StringUtils.split(ignores, ',')) {
				ignoreExts.add('.' + ig.trim());
			}
		}
	}

	public void destroy() {
		System.out.println("filter destroy");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		request.setCharacterEncoding(CharsetUtils.UTF_8);
		response.setCharacterEncoding(CharsetUtils.UTF_8);

		String reqUri = request.getRequestURI();
		CoreMap outMap = new CoreMap();

		for (String ignoreExt : ignoreExts) {
			if (reqUri.endsWith(ignoreExt)) {
				chain.doFilter(request, response);
				return;
			}
		}
		if (reqUri.startsWith("/assets")) {
			chain.doFilter(request, response);
			return;
		}

		try {
			String uri = request.getRequestURI();
			String[] parts = StringUtils.split(uri, "/");
			if (parts.length < 1) {
				parts = new String[] { "action" };
			}

			//this.cfg = templates.get("view");

			CoreAction action = (CoreAction) this.loadAction(parts[0]);

			if (action == null) {
				log.info("找不到 " + parts[0] + "Action...");
				action = (CoreAction) this.loadAction("action");
			} else {
				action.setAction(parts[0]);
				action.setParts(parts);
				action.setRequest(request);
				action.setResponse(response);
				action.setSession(request.getSession(false));
				Map<String, Cookie> cookies = new HashMap<String, Cookie>();
				Cookie[] cks = request.getCookies();
				if (cks != null) {
					for (Cookie ck : cks) {
						cookies.put(ck.getName(), ck);
					}
				}
				action.setCookies(cookies);

				/* 执行类对象方法 */
				String actionMethodName = (parts.length > 1) ? parts[1] : "index";
				action.setMethod(actionMethodName);
				Method method = this.loadActionMethod(action, actionMethodName);
				if (method == null) {
					throw new Exception("找不到方法！");
				}

				CoreMap inMap = RequestUtils.getInMap(request);
				inMap.put("parts", parts);
				outMap = (CoreMap) method.invoke(action, inMap);
			}

			if (outMap != null) {
				String type = outMap.getOutType();
				String render = outMap.getOutRender();
				if (!outMap.containsKey("callback")) {
					outMap.put("callback", outMap.getCallback());
				}

				if (type.equals(Constants.OUT_TYPE__PAGE)) {
					Template temp = this.cfg.getTemplate(render + ".ftl");
					Writer out = response.getWriter();
					try {
						CoreMap data = outMap; // 传递数据
						data.put("request", request);
						data.put("response", response);
						data.put("session", new HttpSessionHashModel(request.getSession(), cfg.getObjectWrapper()));
						data.put("ms", System.currentTimeMillis());
						temp.process(data, out);
					} catch (TemplateException e) {
						e.printStackTrace();
					}
					out.flush();
					out.close();
				} else if (type.equals(Constants.OUT_TYPE__REDIRECT)) {
//					Iterator<String> iter = map.keySet().iterator();
//					while (iter.hasNext()) {
//					    key = iter.next();
//					    value = map.get(key);
//					}
					response.sendRedirect(render);
				} else if (type.equals(Constants.OUT_TYPE__FORWARD)) {
					// request.getRequestDispatcher("/").forward(req, res);
				} else if (type.equals(Constants.OUT_TYPE__JSON)) {
					response.setContentType("text/json; charset=" + CharsetUtils.UTF_8);
					response.getWriter().print(JSONObject.fromObject(outMap));
				} else if (type.equals(Constants.OUT_TYPE__CAPTCHA)) {
				}
			}
			DBManager.commit();
		} catch (Exception e) {
			DBManager.rollback();
			e.printStackTrace();
			response.setStatus(500);
			this.exceptionHandle(response, e);
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
	private Object loadAction(String actionName) throws Exception {
		Object action = actions.get(actionName);
		if (action == null) {
			for (String pkg : actionPackages) {
				String cls = pkg + '.' + StringUtils.capitalize(actionName) + "Action";
				try {
					action = Class.forName(cls).newInstance();
				} catch (ClassNotFoundException excp) {
				}
				if (action != null && !actions.containsKey(actionName)) {
					synchronized (actions) {
						actions.put(actionName, action);
					}
					break;
				}
			}
		}
		return action;
	}

	/**
	 * 获取名为{method}的方法
	 * 
	 * @param action
	 * @param method
	 * @return
	 */
	private Method loadActionMethod(Object action, String methodName) {
		String key = action.getClass().getSimpleName() + '.' + methodName;
		Method method = methods.get(key);
		if (method != null)
			return method;

		try {
			method = action.getClass().getMethod(methodName, CoreMap.class);
		} catch (NoSuchMethodException e) {
			try {
				method = action.getClass().getMethod("notFound", CoreMap.class);
			} catch (NoSuchMethodException e2) {
				e2.printStackTrace();
			}
		}
		if (method != null && !methods.containsKey(key)) {
			synchronized (methods) {
				methods.put(key, method);
			}
		}
		return method;
	}

	private void exceptionHandle(HttpServletResponse response, Throwable e) {
		try {
			response.getWriter().println(e.toString());
			StackTraceElement[] messages = e.getStackTrace();
			int length = messages.length;
			for (int i = 0; i < length; i++) {
				response.getWriter().println("\tat " + messages[i].toString());
			}
			if (e.getCause() != null) {
				response.getWriter().print("Caused by: ");
				this.exceptionHandle(response, e.getCause());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
