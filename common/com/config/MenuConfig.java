package com.config;

import java.util.ArrayList;
import java.util.List;

import com.base.bean.Menu;
import com.base.utils.CoreMap;

public class MenuConfig {
	private static List<Menu> menuList = new ArrayList<Menu>();
	private static CoreMap menuMap = new CoreMap();

	static {
		List<Menu> global = new ArrayList<Menu>();
		global.add(new Menu("index", "后台首页"));
		global.add(new Menu("base", "站点信息"));
		global.add(new Menu("access", "注册登录控制"));
		global.add(new Menu("qiniu", "七牛云存储"));
		global.add(new Menu("email", "邮件服务"));
		menuMap.put("global", global);
		menuList.add(new Menu("global", "全局", global));

		List<Menu> content = new ArrayList<Menu>();
		content.add(new Menu("article", "文章管理"));
		menuMap.put("content", content);
		menuList.add(new Menu("content", "内容", content));

		List<Menu> user = new ArrayList<Menu>();
		user.add(new Menu("user_manage", "用户管理"));
		user.add(new Menu("manage_group", "管理组"));
		user.add(new Menu("member_group", "会员组"));
		user.add(new Menu("sendmsg", "发送通知"));
		user.add(new Menu("ipblack", "用户黑名单"));
		user.add(new Menu("userblack", "IP黑名单"));
		menuMap.put("user", user);
		menuList.add(new Menu("user", "用户", user));

		List<Menu> forum = new ArrayList<Menu>();
		forum.add(new Menu("node", "节点管理"));
		forum.add(new Menu("topic", "主题管理"));
		forum.add(new Menu("recycle", "回收站"));
		menuMap.put("forum", forum);
		menuList.add(new Menu("forum", "论坛", forum));

		List<Menu> tools = new ArrayList<Menu>();
		tools.add(new Menu("update_cache", "更新缓存"));
		tools.add(new Menu("update_count", "更新统计"));
		tools.add(new Menu("logs", "运行记录"));
		menuMap.put("tools", tools);
		menuList.add(new Menu("tools", "工具", tools));
	}

	public static List<Menu> getMenuList() {
		return menuList;
	}

	public static CoreMap getMenuMap() {
		return menuMap;
	}
}
