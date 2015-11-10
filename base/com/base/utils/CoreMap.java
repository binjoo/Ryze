package com.base.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class CoreMap extends HashMap implements Serializable {
	private String outType;
	private String outRender;
	private String callback;

	public CoreMap() {
	}

	public CoreMap(Map map) {
		if (map != null) {
			putAll(map);
		} else {
			this.clear();
		}
	}

	public String getOutType() {
		return outType == null ? Constants.OUT_TYPE__PAGE : outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getOutRender() {
		return outRender == null ? "index" : outRender;
	}

	public void setOutRender(String outRender) {
		this.outRender = outRender;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		if (callback != null && !callback.equals("")) {
			this.callback = callback;
		} else {
			this.callback = "/";
		}
	}

	public Object put(Object key, Object value) {
		String k = String.valueOf(key);
		return super.put(k, value);
	}

	public Object get(Object key) {
		return super.get(key);
	}

	public String getString(String key) {
		String v = null;
		if (containsKey(key)) {
			v = String.valueOf(get(key));
		}
		return v;
	}

	public Double getDouble(String key) {
		if (containsKey(key)) {
			String v = getString(key);
			Double d = Double.valueOf(Double.parseDouble(v));
			return d;
		}
		return null;
	}

	public int getInt(String key) {
		return getInt(key, 0);
	}

	public int getInt(String key, int defaultValue) {
		Integer v = getInteger(key);
		if (v == null) {
			return defaultValue;
		}
		return v.intValue();
	}

	public Integer getInteger(String key) {
		if (containsKey(key)) {
			String v = getString(key);
			int i = Integer.parseInt(v);
			return Integer.valueOf(i);
		}
		return null;
	}

	public Long getLong(String key) {
		if (containsKey(key)) {
			String v = getString(key);
			long l = Long.parseLong(v);
			return Long.valueOf(l);
		}
		return null;
	}

	public BigDecimal getBigDecimal(String key) {
		BigDecimal b = null;
		if (containsKey(key)) {
			String v = getString(key);
			if ((v != null) && (!"".equals(v)) && (!v.equalsIgnoreCase("null"))) {
				b = new BigDecimal(v);
			}
		}
		return b;
	}

	public Date getDate(String key) {
		if (containsKey(key)) {
			String v = getString(key);
			return DateUtils.getDate(v);
		}
		return null;
	}

	public List getList(String key) {
		Object obj = get(key);
		return (List) obj;
	}

	public boolean getBool(String key) {
		return getBool(key, false);
	}

	public boolean getBool(String key, boolean defaultValue) {
		Boolean b = getBoolean(key);
		if (b == null) {
			return defaultValue;
		}
		return b.booleanValue();
	}

	public Boolean getBoolean(String key) {
		if (containsKey(key)) {
			String v = getString(key);
			boolean b = Boolean.parseBoolean(v);
			return Boolean.valueOf(b);
		}
		return null;
	}
}
