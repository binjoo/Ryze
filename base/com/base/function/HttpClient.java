package com.base.function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.utils.CharsetUtils;
import com.base.utils.CoreMap;


public class HttpClient {
	public static final String HTTP_CLIENT_METHOD_GET = "GET";
	public static final String HTTP_CLIENT_METHOD_POST = "POST";
	
	//方法名
	private String method = HTTP_CLIENT_METHOD_GET;
	//连接超时
	private int connectTimeOut = 5000;
	//读取数据超时
	private int readTimeOut = 10000;
	//Cookies
	private CoreMap<String, String> cookies = new CoreMap<String, String>();
	//传递参数
	private CoreMap<String, String> query = new CoreMap<String, String>();
	//需要在body中传递的值
	private CoreMap<String, String> data = new CoreMap<String, String>();
	//头信息参数
	private CoreMap<String, String> header = new CoreMap<String, String>();
    //回执头部信息
	private Map<String, List<String>> responseHeader;
    //回执代码
	private int responseStatus;
    //回执身体
	private String responseBody;

	/**
	 * 设置请求方式
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 设置链接超时
	 * @param connectTimeOut
	 */
	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	/**
	 * 设置读取超时
	 * @param readTimeOut
	 */
	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	/**
	 * 设置指定的Cookie值
	 * @param key
	 * @param value
	 */
	public void setCookies(String key, String value) {
		this.cookies.put(key, value);
	}

	/**
	 * 设置传递参数
	 * @param key
	 * @param value
	 */
	public void setQuery(String key, String value) {
		this.query.put(key, value);
	}

	/**
	 * 设置POST传递参数
	 * @param key
	 * @param value
	 */
	public void setData(String key, String value) {
		this.data.put(key, value);
		this.method = HttpClient.HTTP_CLIENT_METHOD_POST;
	}

	/**
	 * 设置头信息参数
	 * @param key
	 * @param value
	 */
	public void setHeader(String key, String value) {
		this.header.put(key, value);
	}

	/**
	 * 获取回执的头部信息
	 * @param key
	 * @return
	 */
	public List<String> getResponseHeader(String key) {
		return responseHeader.containsKey(key) ? responseHeader.get(key) : null;
	}

	/**
	 * 获取回执状态
	 * @return
	 */
	public int getResponseStatus() {
		return responseStatus;
	}

	/**
	 * 获取回执内容
	 * @return
	 */
	public String getResponseBody() {
		return responseBody;
	}
	
	/**
	 * 发送请求
	 * @param url	请求地址
	 */
	public void send(String url) {
        URL httpUrl = null;
        HttpURLConnection con = null;
        InputStream in = null;
        try {
            String queryUri = "";
            if(query != null && query.size() > 0){
            	int length = query.size(), i = 0;
				for (Map.Entry<String, String> entry : query.entrySet()) {
					queryUri += entry.getKey() + "=" + entry.getValue();
                    i++;
                    if(i != length){
                    	queryUri += "&";
                    }
				}
				if(url.indexOf("?") >= 0){
					url += "&" + queryUri;
				}else{
					url += "?" + queryUri;
				}
            }
            
            System.out.println(url);
        	httpUrl = new URL(url);
            con = (HttpURLConnection) httpUrl.openConnection();
            con.setConnectTimeout(connectTimeOut);  //连接主机的超时时间
            con.setReadTimeout(readTimeOut); //从主机读取数据的超时时间
            con.setRequestMethod(method);
            con.setDoOutput(true);
            con.setUseCaches(false);

            if(header != null && header.size() > 0){
				for (Map.Entry<String, String> entry : header.entrySet()) {
                    con.setRequestProperty(entry.getKey(), entry.getValue());
				}
            }

            if(cookies != null && cookies.size() > 0){
            	String cookie = "";
				for (Map.Entry<String, String> entry : cookies.entrySet()) {
                    cookie += entry.getKey() + "=" + entry.getValue() + "; ";
				}
	            con.setRequestProperty("Cookie", cookie);
            }

            String dataUri = "";
            if(method.equals(HttpClient.HTTP_CLIENT_METHOD_POST)){
                if(data != null && data.size() > 0){
                	int length = data.size(), i = 0;
    				for (Map.Entry<String, String> entry : data.entrySet()) {
    					dataUri += entry.getKey() + "=" + entry.getValue();
                        i++;
                        if(i != length){
                        	dataUri += "&";
                        }
    				}
    				if(url.indexOf("?") >= 0){
    					url += "&" + dataUri;
    				}else{
    					url += "?" + dataUri;
    				}
                }
				byte[] b = dataUri.getBytes();
				con.getOutputStream().write(b, 0, b.length);
				con.getOutputStream().flush();
				con.getOutputStream().close();
            }
            
            in = con.getInputStream();
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream  byteOutput = new ByteArrayOutputStream();
            int idex = -1;
            while ((idex = in.read(bytes)) !=-1) {
                byteOutput.write(bytes, 0, idex);
            }

        	responseHeader = con.getHeaderFields();
        	responseStatus = con.getResponseCode();
        	responseBody = new String(byteOutput.toByteArray(), CharsetUtils.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null){
					in.close();
				}
				if(con != null){
					con.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		HttpClient hc = new HttpClient();
		String url = "http://127.0.0.1:9080/jieyang/data?u=admin";
		hc.setQuery("module", "before");
		hc.setQuery("service", "TransGoodsLand");
		hc.setQuery("method", "testRyze");

		hc.setData("arg0", "arg00");
		hc.setData("arg1", "arg11");
		hc.setData("arg2", "arg22");

		hc.setCookies("arg5", "arg55");
		hc.setCookies("arg6", "arg66");
		hc.setCookies("arg7", "arg77");
		//hc.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
		hc.send(url);
		System.out.println(hc.getResponseBody());
	}
}
