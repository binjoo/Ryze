package com.base.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.rule.Rule;

import com.base.db.DBManager;

public class UrlRewrite {
	private static final Logger log = Logger.getLogger(UrlRewrite.class);
	private static String configPath = "/urlrewrite.xml";

	private UrlRewriteRule[] rules;

	/**
	 * 初始化伪静态规则
	 */
	public UrlRewrite() {
		URL url = DBManager.class.getResource(configPath);
		if (url == null) {
			throw new RuntimeException("UrlRewrite所需的配置文件urlrewrite.xml配置文件不存在");
		}
		String configURLPath = DBManager.class.getResource(configPath).getPath();

		ArrayList<UrlRewriteRule> roleUrlList = getAllRileList(configURLPath);
		int size = roleUrlList.size();
		rules = new UrlRewriteRule[size];
		if (size != 0) {
			for (int i = 0; i < size; i++) {
				roleUrlList.get(i).toString();
				rules[i] = roleUrlList.get(i);
			}
		}
	}

	public ArrayList<UrlRewriteRule> getAllRileList(String filePath) {
		ArrayList<UrlRewriteRule> roleUrlList = null;
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(filePath));
			Element root = document.getRootElement();
			roleUrlList = new ArrayList<UrlRewriteRule>();
			for (Iterator<Rule> iterator = root.elementIterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				UrlRewriteRule rule = new UrlRewriteRule();
				rule.setFrom(element.elementText("from"));
				rule.setTo(element.elementText("to"));
				if (Constants.devMode) {
					log.info(rule.toString());
				}
				roleUrlList.add(rule);
			}
		} catch (DocumentException e) {
			log.error(e);
		}

		return roleUrlList;
	}

    public boolean dealWithUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = request.getServletPath();
        String url_copy = url;
        if (rules.length != 0) {
            int rulesLength = rules.length;
            Boolean isHandler = false;
            for (int i = 0; i < rulesLength; i++) {
                url = rules[i].dealWithUrl(url, isHandler);
 
                if (url != null && !"".equals(url) && (!url_copy.equals(url))) {
                    if (Constants.devMode) {
                        System.out.println("重写后的Url:" + url);
                    }
                    if (url.indexOf('?') != -1) {
                        String url_chang = url;// 修改后的链接
                        int index = url_chang.indexOf('?');
                        url = url_chang.substring(0, url.indexOf('?'));
                        // 支持参数连接匹配
                        if (index != -1) {
                            String queryString = url_chang.substring(index + 1);
                            if (queryString != null) {
                                System.out.println(queryString);
                                String[] param = queryString.split("\\^");
                                if (param.length != 0) {
                                    int length = param.length;
                                    for (int j = 0; j < length; j++) {
                                        try {
                                            if (Constants.devMode) {
                                                System.out.println(param[j].split("=")[0] + "--->" + param[j].split("=")[1]);
                                            }
                                            request.setAttribute(param[j].split("=")[0], param[j].split("=")[1]);
                                        } catch (Exception e) {
                                            if (Constants.devMode) {
                                                log.error(e);
                                            }
                                            throw new RuntimeException("urlrewrite配置文件配置出错" + e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 重定向
                    request.getRequestDispatcher(url).forward(request, response);
                    return true;
                }
            }
        }
        return false;
    }
}
