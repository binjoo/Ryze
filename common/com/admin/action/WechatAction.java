package com.admin.action;

import com.base.action.CoreAction;
import com.base.utils.Constants;
import com.base.utils.CoreMap;
import com.common.dao.KVDao;

@SuppressWarnings("unchecked")
public class WechatAction extends CoreAction {
	public CoreMap index(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        System.out.println("wechat index");
		out.setOutRender("/wechat/base");
        return out;
	}

	public CoreMap base(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if (isGet()) {
			out = base_GET(inMap);
		} else if (isPost()) {
			out = base_POST(inMap);
		} else {
			out.setOutRender("/wechat/base");
		}
		return out;
	}

	private CoreMap base_GET(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		KVDao od = new KVDao();
		out.putAll(od.queryKVOption());
		out.setOutRender("/wechat/base");
		return out;
	}

	private CoreMap base_POST(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		CoreMap options = new CoreMap();
		options.put("wechat_switch", inMap.get("wechat_switch"));
		options.put("wechat_token", inMap.get("wechat_token"));
		options.put("wechat_aeskey", inMap.get("wechat_aeskey"));
		options.put("wechat_encoding", inMap.get("wechat_encoding"));

		KVDao od = new KVDao();
		od.updateMap(options);
		
		out.setOutType(Constants.OUT_TYPE__REDIRECT);
		out.setOutRender("/wechat/base");
		return out;
	}
}
