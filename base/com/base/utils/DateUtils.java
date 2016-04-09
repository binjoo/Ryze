package com.base.utils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * 获得当前系统时间戳（秒）
	 * @return
	 */
    public static String getTimeToString() {
        return String.valueOf(getTimes() / 1000);
    }
    
	/**
	 * 获得当前系统时间戳（秒）
	 * @return
	 */
    public static long getTime() {
        return getTimes() / 1000;
    }
    
	/**
	 * 获得当前系统时间戳（毫秒）
	 * @return
	 */
    public static String getTimesToString() {
        return String.valueOf(getTimes());
    }
    
	/**
	 * 获得当前系统时间戳（毫秒）
	 * @return
	 */
    public static long getTimes() {
        return System.currentTimeMillis();
    }

    public static Date getDate(String date) {
        Date d = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
    
    public static String friendlyTime(Date time) {
        if(time == null) {
        	return "unknown";
        }
        int ct = (int) ((System.currentTimeMillis() - time.getTime()) / 1000);
        if(ct < 3600)
            return MessageFormat.format("{0} 分钟前", Math.max(ct / 60, 1));
        if(ct >= 3600 && ct < 86400)
            return MessageFormat.format("{0} 小时前", ct / 3600);
        if(ct >= 86400 && ct < 2592000){ //86400 * 30
            int day = ct / 86400 ;
            if(day > 1){
                return MessageFormat.format("{0} 天前", ct / 3600);
            }else{
                return MessageFormat.format("{0} 小时前", ct / 3600);
            }
        }
        if(ct >= 2592000 && ct < 31104000) {//86400 * 30
            return MessageFormat.format("{0} 个月前", ct / 2592000);
        }
        return MessageFormat.format("{0} 年前", ct / 31104000);
    }
    
    public static void main(String[] args) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
		try {
			date = sdf.parse("2014-12-16 16:20:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(DateUtils.friendlyTime(date));
	}
}
