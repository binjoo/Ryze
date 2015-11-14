package com.base.test;

import net.sf.json.JSONObject;

import com.base.function.HttpClient;

public class XiaoMiShouHuan {
	public static void main(String[] args) {
		HttpClient hc = new HttpClient();
		hc.setQuery("callid", String.valueOf(System.currentTimeMillis()));
		hc.setQuery("country", "CN");
		hc.setQuery("timezone", "Asia/Shanghai");
//		hc.setQuery("channel", "Normal");
//		hc.setQuery("device_type", "android_phone");
		hc.setQuery("source", "0");
		hc.setQuery("userid", "117077");
		hc.setQuery("deviceid", "88478CE400000263");
		hc.setQuery("uuid", "ffffffff-dfff-ff5e-0000-000061fbe3bf");
		JSONObject o = new JSONObject();
		o.put("date", "2015-11-15");
		//o.put("data", "");
		JSONObject summary = new JSONObject();
		summary.put("v", 5);
		JSONObject slp = new JSONObject();
		slp.put("st", 1447430040);
		slp.put("st", 1447459560);
		slp.put("dp", 25);
		slp.put("lt", 334);
		slp.put("wk", 133);
		summary.put("slp", slp);
		JSONObject stp = new JSONObject();
		o.put("ttl", 98800);	//步数
		o.put("dis", 798);
		o.put("cal", 62);
		o.put("wk", 32);
		o.put("rn", 0);
		o.put("runDist", 133);
		o.put("runCal", 5);
		o.put("goal", 6000);
		summary.put("stp", stp);
		o.put("summary", summary);
		JSONObject indexs = new JSONObject();
		indexs.put("startIndex", 0);
		indexs.put("stopIndex", 4176);
		o.put("indexs", indexs);
		o.put("source", 0);
		o.put("type", 0);
		System.out.println(o.toString());
		hc.setQuery("data_len", String.valueOf(o.toString().length()));
		hc.setQuery("data_json", o.toString());
		hc.setQuery("security", "00b9d41a3bebc2d43b0af6c132f24f9a");
//		hc.setQuery("cv", "1595_1.7.521");
//		hc.setQuery("c", "1.0");
		hc.setQuery("appid", "2882303761517154077");
//		hc.setQuery("data_type", "0");
//		hc.setQuery("lang", "zh");
//		hc.setQuery("device", "android_22");
		hc.send("https://hm.xiaomi.com/huami.health.receiveData.json");
		System.err.println(hc.getResponseBody());
	}
}
