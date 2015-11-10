package com.common.action;

import java.util.Arrays;
import java.util.Enumeration;

import com.base.action.CoreAction;
import com.base.utils.Constants;
import com.base.utils.CoreMap;
import com.base.utils.EncryptUtil;
import com.common.dao.KVDao;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class WechatAction extends CoreAction {

	public CoreMap index(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if (isGet()) {
			out = index_GET(inMap);
		} else if (isPost()) {
			//out = base_POST(inMap);
		} else {
			out.setOutRender("/global/base");
		}
		return out;
	}
	

	public CoreMap index_GET(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		KVDao kvDao = new KVDao();
		String token = kvDao.queryKVOption("wechat_token");
		if (token == null) {
			return null;
		}
		// 微信加密签名
		String signature = inMap.getString("signature");
		// 随机字符串
		String echostr = inMap.getString("echostr");
		// 时间戳
		String timestamp = inMap.getString("timestamp");
		// 随机数
		String nonce = inMap.getString("nonce");
		String[] str = { token, timestamp, nonce };
		if(1==1){
			String tmp = "";
	        Enumeration it = super.getRequest().getParameterNames();
	        while (it.hasMoreElements()) {
	            String key = String.valueOf(it.nextElement());
	            String value = super.getRequest().getParameter(key);
				super.getResponse().getWriter().print("key : " + key + " - val : " + value);
				tmp += "&" + key + "=" + value;
	            //System.out.println("key : " + key + " - val : " + value);
	            //outMap.put(key, value);
	        }
	        CoreMap inm = new CoreMap();
	        inm.put("name", System.currentTimeMillis() + "");
	        inm.put("value", tmp);
	        inm.put("type", "temp");
	        inm.put("user_id", "0");
	        kvDao.insert(inm);
			return null;
		}
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		// String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
		String digest = EncryptUtil.SHA(bigStr);
		// 确认请求来至微信
		if (digest.equals(signature)) {
			super.getResponse().getWriter().print(echostr);
		}
		out.setOutType(Constants.OUT_TYPE__NONE);
		return out;
	}

}
