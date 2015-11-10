package com.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.base.action.CoreAction;
import com.base.db.DBHepler;
import com.base.db.DBQuery;
import com.base.utils.CaptchaUtils;
import com.base.utils.CoreMap;
import com.base.utils.Constants;
import com.common.dao.TopicDao;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class ActionAction extends CoreAction {
    private final static String CACHE_REGION = "session";
    private final static String COOKIE_NAME = "_reg_key_";
    private static int WIDTH = 120;
    private static int HEIGHT = 40;
    private static int LENGTH = 5;
    private final static Random random = new Random();

    public CoreMap index(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        DBQuery q = new DBQuery();
        q.select("ft.id", "ft.title", "fn.no as node_no", "fn.name as node_name", "su.nickname");
        q.from("forum_topic ft");
        q.join("forum_node fn", "ft.node_id = fn.id");
        q.join("sys_user su", "ft.user_id = su.id");
        q.where("ft.type = ?", 1).order("ft.created", DBQuery.SORT_DESC);
        TopicDao topicDao = new TopicDao();
        List list = topicDao.queryList(q);
        out.put("topics", list);
        return out;
    }

    public CoreMap captcha(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        getResponse().setContentType("image/png");// 设置相应类型,告诉浏览器输出的内容为图片
        getResponse().setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        getResponse().setHeader("Cache-Control", "no-cache");
        getResponse().setDateHeader("Expire", 0);
        CaptchaUtils.get(getRequest(), getResponse(), getSession(), getCookies());
        out.setOutType(Constants.OUT_TYPE__CAPTCHA);
        return out;
    }

}
