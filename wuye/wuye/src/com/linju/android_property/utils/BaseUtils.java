package com.linju.android_property.utils;

import java.util.Locale;


/**
 * 一些获取系统信息的工具类  
 * @author Administrator
 *
 */
public class BaseUtils {

	/**
	 * 获取系统语言
	 *  语言 中文为zh，英文为en，日文为ko
	 * @return
	 */
	public static String getDefaultLanguage(){
		String language = null;
		
		language = Locale.getDefault().getLanguage();
		
		return language;
	}
	
}
