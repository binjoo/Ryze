package com.base.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.base.db.DBManager;

public class AppConfig {
	private static Properties appConfig;
    private final static Log log = LogFactory.getLog(AppConfig.class);

	public static String getPro(String key) {
		return getPro(key, null);
	}

	public static String getPro(String key, String val) {
		if (appConfig == null) {
			try {
				InputStream in = DBManager.class.getResourceAsStream("/appConfig.properties");
				appConfig = new Properties();
				appConfig.load(in);
			} catch (Exception ex) {
				log.error(ex);
			}
		}
		if (appConfig.containsKey(key)) {
			String value = appConfig.getProperty(key);
			if (StringUtils.isNotEmpty(value)) {
				value = StringUtils.trim(value);
			}
			return value;
		} else {
			return val == null ? null : val;
		}
	}
}
