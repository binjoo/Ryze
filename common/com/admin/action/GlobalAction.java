package com.admin.action;

import java.util.Properties;

import com.base.action.CoreAction;
import com.base.cache.CacheManager;
import com.base.db.DBHepler;
import com.base.utils.CoreMap;
import com.base.utils.Constants;
import com.common.dao.KVDao;

@SuppressWarnings("unchecked")
public class GlobalAction extends CoreAction {
	/**
	 * 后台首页
	 */
	public CoreMap index(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		int mb = 1024 * 1024;
		Properties props = System.getProperties();
		Runtime r = Runtime.getRuntime();
		out.put("os_info", props.getProperty("os.name") + " " + props.getProperty("os.arch") + " " + props.getProperty("os.version"));
		out.put("java_version", props.getProperty("java.version"));
		out.put("java_home", props.getProperty("java.home"));
		out.put("jvm_info", (r.freeMemory() / mb) + " M " + " / " + (r.totalMemory() / mb) + " M");
		out.put("jvm_info_free", r.freeMemory() / mb);
		out.put("jvm_info_total", r.totalMemory() / mb);
		out.put("java_tmpdir", props.getProperty("java.io.tmpdir"));
		out.put("user_dir", props.getProperty("user.dir"));
		out.setOutRender("/admin/global/index");
		return out;
	}

	/**
	 * 站点信息
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	public CoreMap base(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if (isGet()) {
			out = queryOption_GET(inMap);
			out.setOutRender("/admin/global/base");
		} else if (isPost()) {
			out = base_POST(inMap);
		} else {
			out.setOutRender("/admin/global/base");
		}
		return out;
	}

	private CoreMap queryOption_GET(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		KVDao od = new KVDao();
		out.putAll(od.queryKVOption());
		return out;
	}

	private CoreMap base_POST(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		CoreMap options = new CoreMap();
		options.put("site_title", inMap.getString("site_title"));
		options.put("site_description", inMap.getString("site_description"));
		options.put("site_url", inMap.getString("site_url"));
		options.put("site_email", inMap.getString("site_email"));
		options.put("site_switch", inMap.getString("site_switch"));

		KVDao od = new KVDao();
		od.updateMap(options);
		
		out.setOutType(Constants.OUT_TYPE__REDIRECT);
		out.setOutRender("/admin/gotoUrl?url=/admin/global/base");
		return out;
	}
	
	/**
	 * 注册控制
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	public CoreMap access(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if (isGet()) {
			out = queryOption_GET(inMap);
			out.setOutRender("/admin/global/access");
		} else if (isPost()) {
			out = access_POST(inMap);
		} else {
			out.setOutRender("/admin/global/access");
		}
		return out;
	}

	private CoreMap access_POST(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		CoreMap options = new CoreMap();
		options.put("site_register", inMap.getString("site_register"));
		options.put("site_invite", inMap.getString("site_invite"));

		KVDao od = new KVDao();
		od.updateMap(options);
		
		out.setOutType(Constants.OUT_TYPE__REDIRECT);
		out.setOutRender("/admin/gotoUrl?url=/admin/global/access");
		return out;
	}
	
	/**
	 * 七牛云存储
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	public CoreMap qiniu(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		return out;
	}
}
