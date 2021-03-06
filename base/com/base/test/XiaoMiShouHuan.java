package com.base.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import com.base.function.HttpClient;

public class XiaoMiShouHuan {
	public static void main(String[] args) throws ParseException {
		Date now = new Date();
		HttpClient hc = new HttpClient();
		hc.setQuery("callid", String.valueOf(System.currentTimeMillis()));
		hc.setQuery("country", "CN");
		hc.setQuery("timezone", "Asia/Shanghai");
		hc.setQuery("channel", "Normal");	//-
		hc.setQuery("device_type", "android_phone");	//-
		hc.setQuery("source", "0");
		hc.setQuery("userid", "117077");
		hc.setQuery("deviceid", "88478CE400000263");
		hc.setQuery("uuid", "ffffffff-dfff-ff5e-0000-000061fbe3bf");
		JSONObject o = new JSONObject();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		o.put("date", sdf1.format(now));
		o.put("data", "ACkAEBMAAAAAAAAAAAAAAAIAAAAAAAAAAAAAAAAAAAAABAAABQEABQAABQAABQAABQAABQAABQAABQAABQAACwAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAEABAAABA4ABAoABBoABQAABA4ABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABBkABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQIABQAABQAABQAABQAABQAABQAABQAABQAABQAABCkABQQABQAABA8ABAwABBUABBsABQAABQMABQUABQIABQQABDYKBQUABAkABA4ABQUABCAABQEABQUABAsABQEABQAABBAABQMABA8ABQMABQEABAwABQEABQAABQAABQMABQAABAsABAcABQEABQUABAcABB4ABAgABAcABQMABQMABQMABQAABQAABQAABQQABQAABQEABQQABQEABQAABQAABQAABQAABQAABQEABA0ABQAABQAABAcABCAKBQIABCsABCUABAcABEEKBQMABQEABQMABQAABQEABBIABQAABQEABQAABQAABCMABEAABEoaDFMKAU0QACQAABIAACIAASARAAMAAAAAAA8AAB0AAAEAABAAAA4AABsAAAIAAB8AAAkAAAEAAAAAAAIAAAAAAAAAAAAAAAAAAAAAAAAABAAABQIABQAABQAABQAABQAABQAABQAABQAABQAABQIACwAAACAAAS0SACwNAB8AABoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABAoABCgABCkABBMABQQABQMABQAABAoABQAABC8TBCMABB8ABB0ABQAABBgABBoABA0ABQAABQAABQAABCgABA4ABCAABCEABQAABQAABQAABQAABCcABQMABBgABQUABCAABQEABBIABQAABAgABQIABQAABQIABQIABQAABAwABQAABQEABBcABQUABAYABQAABQUABQAABQAABAkABQAABQAABQAABAkABQAABQAABA0ABQAABQQABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABQAABBMABCgABBUABQIABQQABQAABQAABQEABA4ABQAABAYABBwABAkABQUABAcABQMABAsABBgABQAABQEABCUABQAABQIABQAABBoABCIABAkABBAABAwABAYABAgABCUABQUABBkABDkOBAYABBUABQIABBcABBQABAwABCQABQQABBwABQAABQAABQEABQAABQAABQEABQAABCEABBkABBEABAwABCcABBAABQAABQAABQAABQAABAYABQAABQAABQAABQAABQAABAcABQMABQAABQEABQAABQAABQIABAgABQAABQAABQAABQEABQAABQAABQAABQAABQAABQAABQAABQAABQAABCwABQQABQAABBIABQAABQAABQAABQAABAsABQQABQIABQMABQAABQAABQUABQAABQQABQAABBoABQEABBkABBAABQIABQQABQAABBQABDwAABgAAAAAAA0AADEAAAIAAAMAAAgAAAAAAAAAAAAAABoAAAQAAAMAAAwAAAEAAAMAAAAAAAUAAAYAAAAAABUAAAcAAAYAAAAAAAAAACYAAAoAAAgAACMAAAQAACYAACAAAB0AABcAAAEAAAMAAAMAABQAAAAAAAAAABYAAAAAAAAAACoAAB8AABIAABgAAAUAACYAABMAAD8KDFoXAEcJAB0AABgAABsAABwAABoAABsAACgAABwAABIAABYAACcAAFIKAEAAAEcAAGEOADkAAB8AAB0AABgAAE8MAFYAAF0PAFMAAD0AAFkOAVwoABEAAC4AAD8AAEUMQYxzEXhYAXlsAWxlEXhbAYNZAZJuAYVvAYZlAX9mAYBuAYNwAYdlAX9jAX9cAX5kAXthAX9nAYhnAYZhAXtTACkNACoAAVMhAEQMABAAABkAAA8AAA0AAA8AAFIAABwAABkAABgAABIAABMAABEAAAgAABoAAAAAACgAAA4AACIAABEAABEAAAsAACMAABsAABgAACAAAAwAABAAABcAAAQAABAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAAfgAA");
		JSONObject summary = new JSONObject();
		summary.put("v", 5);
		JSONObject slp = new JSONObject();
		Date st = sdf2.parse(sdf1.format(now) + " 00:00:00");
		slp.put("st", 1447701300);
		Date ed = sdf2.parse(sdf1.format(now) + " 23:59:59");
		slp.put("ed", 1447715700);
		slp.put("dp", 27);
		slp.put("lt", 193);
		slp.put("wk", 20);
		summary.put("slp", slp);
		JSONObject stp = new JSONObject();
		stp.put("ttl", 2830);	//步数
		stp.put("dis", 1737);
		stp.put("cal", 116);
		stp.put("wk", 30);
		stp.put("rn", 0);
		stp.put("runDist", 86);
		stp.put("runCal", 5);
		stp.put("goal", 6000);
		summary.put("stp", stp);
		o.put("summary", summary);
		JSONObject indexs = new JSONObject();
		indexs.put("startIndex", 0);
		indexs.put("stopIndex", 2188);
		o.put("indexs", indexs);
		o.put("source", 0);
		o.put("type", 0);
		hc.setQuery("data_len", String.valueOf(o.toString().length() + 2));
		hc.setQuery("data_json", "[" + o.toString() + "]");
		hc.setQuery("security", "00b9d41a3bebc2d43b0af6c132f24f9a");
		hc.setQuery("cv", "1595_1.7.521");	//-
		hc.setQuery("v", "1.0");	//-
		hc.setQuery("appid", "2882303761517154077");
		hc.setQuery("data_type", "0");	//-
		hc.setQuery("lang", "zh");	//-
		hc.setQuery("device", "android_22");	//-
		hc.setMethod(HttpClient.HTTP_CLIENT_METHOD_POST);
		hc.send("https://hm.xiaomi.com/huami.health.receiveData.json");
		System.err.println(hc.getResponseBody());
	}
}
