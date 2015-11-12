package com.common.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang.StringUtils;

import com.base.action.CoreAction;
import com.base.db.DBHepler;
import com.base.function.SendMail;
import com.base.utils.ActionUtils;
import com.base.utils.CaptchaUtils;
import com.base.utils.CoreMap;
import com.base.utils.DateUtils;
import com.base.utils.EncryptUtil;
import com.base.utils.Constants;
import com.base.utils.RequestUtils;
import com.base.utils.StrUtils;
import com.common.dao.GroupDao;
import com.common.dao.InviteDao;
import com.common.dao.KVDao;
import com.common.dao.ResetPasswordDao;
import com.common.dao.UserDao;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserAction extends CoreAction {

	public CoreMap index(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();

		List<CoreMap> a = DBHepler.queryList("select * from ya_invite order by id asc");
		for (int i = 0; i < a.size(); i++) {
			CoreMap map = a.get(i);
			System.out.println(map.get("id") + " \t " + map.get("no"));
		}
		
		out.put("data", a);
        out.put("message", "我这是用户首页的数据。。。");
        out.setOutRender("user/index");
		return out;
	}
	
	public CoreMap notFound(CoreMap inMap) throws Exception {
	    CoreMap out = new CoreMap();
	    if(StrUtils.isInt(getMethod())){
	        CoreMap user = new UserDao().checkUserId(getMethod());
	        if(user != null){
	            out.setOutRender("user/index");
	            System.out.println("有这个人 。。。。。。");
	            return out;
	        }
	    }
	    return super.notFound(inMap);
	}
	
	public CoreMap register(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
    	KVDao kvDao = new KVDao();
    	List list = new ArrayList();
    	String site_invite = kvDao.queryKVOption("site_invite");
    	String site_reg_captcha = kvDao.queryKVOption("site_reg_captcha");
        if(isAjax()){
            out.setOutType(Constants.OUT_TYPE__JSON);
        }
	    if(isPost()){
			CoreMap param = new CoreMap();
            
	    	String email = inMap.getString("email");
            if(email != null && !email.equals("")){
				if(StrUtils.isNotEmail(email) || email.length() > 128){
                	list.add("email#邮箱格式不正确！");
				}
				CoreMap u = new UserDao().checkEmail(email);
				if(u != null){
                	list.add("email#邮箱已经存在！");
				}
            }else{
            	list.add("email#邮箱不能为空！");
            }
            param.put("email", email);

	    	String nickname = inMap.getString("nickname");
            if(nickname != null && !nickname.equals("")){
				Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5a-zA-Z0-9_]{2,16}$"); 
				Matcher matcher = pattern.matcher(nickname);
				if(!matcher.matches()){
                	list.add("nickname#昵称格式不正确！");
				}
				CoreMap u = new UserDao().checkNickname(nickname);
				if(u != null){
                	list.add("nickname#昵称已经存在！");
				}
            }else{
            	list.add("nickname#昵称不能为空！");
            }
            param.put("nickname", nickname);

            String password = inMap.getString("password");
            if(password != null && !password.equals("")){
            	if(password.length() > 32 || password.length() < 6){
                	list.add("password#密码长度不正确！");
            	}
            	password = EncryptUtil.MD5(password, 2);
            }else{
            	list.add("password#密码不能为空！");
            }

            String confirm_password = inMap.getString("confirm_password");
            confirm_password = EncryptUtil.MD5(confirm_password, 2);
            if(!password.equals(confirm_password)){
            	list.add("confirm_password#两次密码输入不一致！");
            }
            param.put("password", password);
            
            String invite = inMap.getString("invite");
            if(site_invite.equals("1")){
                if(invite != null && !invite.equals("")){
                	if(invite.length() != 16){
                    	list.add("invite#激活码不正确！");
                	}else{
                    	CoreMap map = new InviteDao().isValid(invite);
                    	if(map != null){
                    		if(map.getInteger("is_valid") != null && map.getInt("is_valid") != 0){
                            	list.add("invite#激活码已被使用！");
                    		}
                    	}else{
                        	list.add("invite#激活码不存在！");
                    	}
                	}
                }else{
                	list.add("invite#激活码不能为空！");
                }
            }
            
            String captcha = inMap.getString("captcha");
            if(site_reg_captcha.equals("1")){
	            if(captcha != null && !captcha.equals("")){
	                if(!CaptchaUtils.validate(getRequest(), getSession())){
	                    list.add("captcha#验证码不正确！");
	                }
	            }else{
	                list.add("captcha#验证码不能为空！");
	            }
            }
            
            if(list.size() > 0){
            	out.put("message", list);
            }else{
                //新增用户
                int userId = new UserDao().insert(param);
                if(userId > 0){
                    if(site_invite.equals("1")){
                    	int result = new InviteDao().activation(invite, userId);
                    }
                    CoreMap user = new UserDao().checkUserId(String.valueOf(userId));
                    getSession().setAttribute("login", user);
					CoreMap allow = new GroupDao().queryGroup(user.getInt("group_id"));
					getSession().setAttribute("allow", allow);
                    out.put("status", 1);
                    out.put("message", "注册成功，请牢记您的账号和密码！");
                    out.setCallback(inMap.getString("callback"));
                    return out;
                }
            }
	    }else if(isGet()){
	    	String email = inMap.getString("email");
	    	if(StringUtils.isNotEmpty(email)){
	    		out.put("email", email);
	    	}
	    	out.put("siteInvite", site_invite);
	    	out.put("siteRegCaptcha", site_reg_captcha);
	    }
	    out.setOutRender("user/register");
	    return out;
	}

	public CoreMap login(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		KVDao kvDao = new KVDao();
		List list = new ArrayList();
    	String site_login_captcha = kvDao.queryKVOption("site_login_captcha");
        if(isAjax()){
            out.setOutType(Constants.OUT_TYPE__JSON);
        }
		if (isPost()) { // 处理登录
			String email = inMap.getString("email");
            if(email != null && !email.equals("")){
                if(StrUtils.isNotEmail(email) || email.length() > 128){
                	list.add("email#邮箱格式不正确！");
                }
            }else{
            	list.add("email#邮箱不能为空！");
            }
            
            String password = inMap.getString("password");
            if(password == null || password.equals("")){
            	list.add("password#密码不能为空！");
            }else{
                password = EncryptUtil.MD5(password, 2);
            }
            
            String captcha = inMap.getString("captcha");
            if(site_login_captcha.equals("1")){
	            if(captcha != null && !captcha.equals("")){
	                if(!CaptchaUtils.validate(getRequest(), getSession())){
	                    list.add("captcha#验证码不正确！");
	                }
	            }else{
	                list.add("captcha#验证码不能为空！");
	            }
            }
            
			if(list.size() > 0){
				out.put("message", list);
			}else{
				CoreMap param = new CoreMap();
				param.put("email", email);
				param.put("password", password);
				CoreMap user = new UserDao().checkLogin(param);
				if (user != null) {
					getSession().setAttribute("login", user);
					CoreMap allow = new GroupDao().queryGroup(user.getInt("group_id"));
					getSession().setAttribute("allow", allow);
                    out.put("status", 1);
                    out.put("message", "登录成功！");
					out.setCallback(inMap.getString("callback"));
				    return out;
				} else {
                    out.put("status", 0);
                    out.put("message", "用户名或密码不正确，请重新输入！");
				}
			}
		}else if(isGet()){
            if(inMap.getString("callback") != null){
                out.put("callback", inMap.getString("callback"));
            }
	    	out.put("siteLoginCaptcha", site_login_captcha);
		}
        out.setOutRender("user/login");
		return out;
	}
	
	public CoreMap logout(CoreMap inMap) throws Exception{
		CoreMap out = new CoreMap();
		String goUrl = (String) inMap.get("go");
		if (isLogin()) {
		    getSession().removeAttribute("login");
		    if(goUrl != null && !goUrl.equals("")){
		        out.setOutType(Constants.OUT_TYPE__REDIRECT);
		        out.setOutRender(goUrl);
		    }
		}else{
			out.put("message", "没有登录！");
		}
		return out;
	}
	
	@SuppressWarnings("unused")
    public CoreMap reset(CoreMap inMap) throws Exception{
		CoreMap out = new CoreMap();
		List list = new ArrayList();
        if(isAjax()){
            out.setOutType(Constants.OUT_TYPE__JSON);
        }
        if(isPost()){
        	if(inMap.getString("step").equals("first")){
                out.putAll(resetFirst(inMap));
        	}else if(inMap.getString("step").equals("second")){
                out.putAll(resetSecond(inMap));
        	}
        }else if(isGet()){
        	String[] parts = (String[]) inMap.get("parts");
        	if(parts.length > 3){
        		out.put("token", parts[2]);
        		out.put("email", parts[3]);
                out.setOutRender("user/reset-step-2");
        	}else{
                out.setOutRender("user/reset-step-1");
        	}
        }
		return out;
	}

    /**
     * 重置密码第一步
     * @param inMap
     * @return
     * @throws Exception
     */
    public CoreMap resetFirst(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        List list = new ArrayList();
        String email = inMap.getString("email");
        if(email != null && !email.equals("")){
            if(StrUtils.isNotEmail(email) || email.length() > 128){
                list.add("email#邮箱格式不正确！");
            }
        }else{
            list.add("email#邮箱不能为空！");
        }
        
        String captcha = inMap.getString("captcha");
        if(captcha != null && !captcha.equals("")){
            if(!CaptchaUtils.validate(getRequest(), getSession())){
                list.add("captcha#验证码不正确！");
            }
        }else{
            list.add("captcha#验证码不能为空！");
        }
        
        if(list.size() > 0){
            out.put("message", list);
        }else{
            CoreMap param = new CoreMap();
            param.put("email", email);
            CoreMap user = new UserDao().checkEmail(email);
            if (user != null) {
                param.put("user_id", user.getString("id"));
                param.put("token", StrUtils.GUID_32());
                param.put("create_ip", RequestUtils.getRemoteAddr(getRequest()));
                int result = new ResetPasswordDao().insert(param);
                if(result > 0){
                    SendMail mail = new SendMail();
                    mail.setTitle("重置密码邮件");
                    mail.setContent("<a href=\"http://127.0.0.1:8080/user/reset/" + param.getString("token") + "/" + param.getString("email") + "\">点击重置密码</a>");
                    mail.setToEmail(email);
                    try {
                        //mail.send();
                    } catch (Exception e) {
                        out.put("status", 0);
                        out.put("message", "重置密码的邮件发送失败，请联系管理员！");
                    }
                    out.put("status", 1);
                    out.put("message", "重置密码的邮件已经发送到您的邮箱，请注意查收！");
                }else{
                    out.put("status", 0);
                    out.put("message", "重置密码的邮件发送失败，请联系管理员！");
                }
            } else {
                list.add("email#此邮箱尚未注册，<a href=\"/user/register?email=" + email + "\">现在注册</a>？");
                out.put("message", list);
            }
        }
        return out;
    }
    
    /**
     * 重置密码第二步
     * @param inMap
     * @return
     * @throws Exception
     */
    public CoreMap resetSecond(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        List list = new ArrayList();

        String password = inMap.getString("password");
        if(password != null && !password.equals("")){
        	if(password.length() > 32 || password.length() < 6){
            	list.add("password#密码长度不正确！");
        	}
        	password = EncryptUtil.MD5(password, 2);
        }else{
        	list.add("password#密码不能为空！");
        }

        String confirm_password = inMap.getString("confirm_password");
        confirm_password = EncryptUtil.MD5(confirm_password, 2);
        if(!password.equals(confirm_password)){
        	list.add("confirm_password#两次密码输入不一致！");
        }
        
        String captcha = inMap.getString("captcha");
        if(captcha != null && !captcha.equals("")){
            if(!CaptchaUtils.validate(getRequest(), getSession())){
                list.add("captcha#验证码不正确！");
            }
        }else{
            list.add("captcha#验证码不能为空！");
        }

        if(list.size() > 0){
            out.put("message", list);
        }else{
            String email = inMap.getString("email");
            String token = inMap.getString("token");
            if(email != null && !email.equals("") && token != null && !token.equals("")){
                CoreMap reset = new ResetPasswordDao().checkResetToken(inMap);
                if(reset == null){
                    out.put("status", 0);
                    out.put("message", "无效请求参数，点<a href=\"/user/reset\">这里</a>重新请求密码重置。");
                    return out;
                }
                long valid = reset.getLong("valid_time");
                if(valid < DateUtils.getTime() || reset.getInt("status") == 1){
                    out.put("status", 0);
                    out.put("message", "请求参数已过期，点<a href=\"/user/reset\">这里</a>重新请求密码重置。");
                    return out;
                }
                
                reset.put("password", password);
                int result = new UserDao().update(reset);
                if(result > 0){
                	reset.put("reset_id", RequestUtils.getRemoteAddr(getRequest()));
                	result = new ResetPasswordDao().updateStatus(reset);
                    out.put("status", 1);
                    out.put("message", "修改成功！");
                }else{
                    out.put("status", 0);
                    out.put("message", "修改失败！");
                }
            }else{
                out.put("status", 0);
                out.put("message", "无效请求参数，点<a href=\"/user/reset\">这里</a>重新请求密码重置。");
            }
        }
        return out;
    }

	public CoreMap admin(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		out.setOutRender("user/admin-index");
		return out;
	}
}
