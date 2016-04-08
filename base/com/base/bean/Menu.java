package com.base.bean;

import java.util.List;

public class Menu {
	private String key;
	private String name;
	private String action;
	private List<Menu> child;

	public Menu(String key, String name) {
		this.key = key;
		this.name = name;
		this.action = key;
	}

	public Menu(String key, String name, String action) {
		this.key = key;
		this.name = name;
		this.action = action;
	}

	public Menu(String key, String name, List<Menu> child) {
		this.key = key;
		this.name = name;
		this.action = key;
		this.setChild(child);
	}

	public Menu(String key, String name, String action, List<Menu> child) {
		this.key = key;
		this.name = name;
		this.action = action;
		this.setChild(child);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Menu> getChild() {
		return child;
	}

	public void setChild(List<Menu> child) {
		this.child = child;
	}

}
