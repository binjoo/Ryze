package com.base.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class HttpUtils {
	private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0";

	public static String doGet(String url) throws Exception {
		return doGet(url, null);
	}
	public static String doGet(String url, CoreMap param) throws Exception {
		StringBuffer buffer = new StringBuffer(); // 用来拼接参数
		StringBuffer result = new StringBuffer(); // 用来接受返回值
		URL httpUrl = null; // HTTP URL类 用这个类来创建连接
		URLConnection connection = null; // 创建的http连接
		BufferedReader bufferedReader = null; // 接受连接受的参数

		if (param != null && param.size() > 0) {
			Iterator it = param.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				String key = (String) e.getKey();
				String value = URLEncoder.encode((String) e.getValue(), "utf-8");
				buffer.append(key).append("=").append(value);
				// 如果不是最后一个参数，不需要添加&
				if (it.hasNext()) {
					buffer.append("&");
				}
			}
			url = url + "?" + buffer.toString();
		}
		// 创建URL
		httpUrl = new URL(url);
		connection = httpUrl.openConnection();
		connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		connection.setRequestProperty("connection", "keep-alive");
		connection.setRequestProperty("user-agent", USER_AGENT);
		connection.connect();
		// 接受连接返回参数
		bufferedReader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
		bufferedReader.close();
		return result.toString();
	}

	public static String doPost(String url, CoreMap param) throws Exception {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        URL httpUrl = null; //HTTP URL类 用这个类来创建连接
        URLConnection connection = null; //创建的http连接
        PrintWriter printWriter = null;
        BufferedReader bufferedReader; //接受连接受的参数
        //创建URL
        httpUrl = new URL(url);
        //建立连接
        connection = httpUrl.openConnection();
        connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("user-agent", USER_AGENT);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        printWriter = new PrintWriter(connection.getOutputStream());
		if (param != null && param.size() > 0) {
			Iterator it = param.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				String key = (String) e.getKey();
				String value = URLEncoder.encode((String) e.getValue(), "utf-8");
				buffer.append(key).append("=").append(value);
				// 如果不是最后一个参数，不需要添加&
				if (it.hasNext()) {
					buffer.append("&");
				}
			}
		}
        printWriter.print(buffer.toString());
        printWriter.flush();
        connection.connect();
        //接受连接返回参数
        bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        return result.toString();
	}

	public static void main(String[] args) {
		CoreMap param = new CoreMap();
		param.put("id", "10086");
		param.put("username", "liangj");
		param.put("password", "123456");
		try {
			//System.out.println(HttpUtils.doGet("http://www.baidu.com", param));
			String str = "%E5%BB%BA%E8%AE%BE%E7%94%A8%E5%9C%B0%E9%A1%B9%E7%9B%AE%E9%A2%84%E5%AE%A1";
			System.out.println(URLDecoder.decode(str));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
