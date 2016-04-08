package com.base.utils;

public class UrlRewriteRule {
	@Override
	public String toString() {
		return "Rule [from=" + from + ", to=" + to + "]";
	}

	private String from;
	private String to;

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTo() {
		return to;
	}

	public String dealWithUrl(String url, Boolean isHandler) {
		boolean isMatches = url.matches(from);
		if (isMatches) {
			isHandler = true;
			url = url.replaceAll(from, to);
			return url;
		} else {
			return url;
		}

	}
}
