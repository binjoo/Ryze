package com.base.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
	public static String GUID_32() {
		return GUID();
	}

	public static String GUID_16() {
		String val = GUID().substring(8, 24); // 取16位
		return val;
	}

	private static String GUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
    
    public static boolean isInt(String val){
        Pattern pattern = Pattern.compile("^(-)?\\d+$"); 
        Matcher matcher = pattern.matcher(val);
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean isNotEmail(String val){
    	return !isEmail(val);
    }
    
    public static boolean isEmail(String val){
        Pattern pattern = Pattern.compile("\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b"); 
        Matcher matcher = pattern.matcher(val);
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean isUrl(String val){
        Pattern pattern = Pattern.compile("^((http|https)://){1}([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"); 
        Matcher matcher = pattern.matcher(val);
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isNotUrl(String val){
    	return !isUrl(val);
    }
    
    public static String lpad(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }
    
    /**
     * 数组元素组合为一个字符串
     * @param separator 规定数组元素之间放置的内容。
     * @param array 要结合为字符串的数组。
     * @return
     */
    public static String implode(String separator, Object[] array) {
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			if(i == 0 || separator == null){
				out.append(array[i]);
			}else{
				out.append(separator + array[i]);
			}
		}
		return out.toString();
	}
    
    /**
     * 替换下划线并且相邻的后一位字母大写
     * @param source 字符串
     * @param titleCase 是否转换首字母为大写
     * @return
     */
    public static String replaceUnderlineAndFirstLetterToUpper(String source, boolean titleCase){
    	int index = source.indexOf("_");
    	while (index != -1) {
    		source = source.replaceFirst("_", "");
			if(index != source.length()){
				char[] letter = source.substring(index, index + 1).toCharArray();
		    	source = source.substring(0, index) + Character.toTitleCase(letter[0]) + source.substring(index + 1, source.length());
			}
			index = source.indexOf("_");
		}
    	
        final char firstChar = source.charAt(0);
    	if(titleCase && !Character.isTitleCase(firstChar)){
    		source = Character.toTitleCase(firstChar) + source.substring(1);
    	}
    	
    	return source;
    }
    
    /**
     * 替换下划线并且相邻的后一位字母大写
     * @param source 字符串
     * @return
     */
    public static String replaceUnderlineAndFirstLetterToUpper(String source){
    	return StrUtils.replaceUnderlineAndFirstLetterToUpper(source, true);
    }
}
