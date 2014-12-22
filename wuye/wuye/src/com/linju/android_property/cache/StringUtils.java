package com.linju.android_property.cache;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public final static String EMPTY = "";

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 * @return 例如2011-3-24
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATE_PATTERN);
	}

	/**
	 * 获取当前时间 格式为yyyy-MM-dd 例如2011-07-08
	 * 
	 * @return
	 */
	public static String getDate() {
		return formatDate(new Date(), DEFAULT_DATE_PATTERN);
	}

	/**
	 * 格式化时间 格式为hh:mm:ss 例如：16:06:54
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return formatDate(date, DEFAULT_TIME_PATTERN);
	}

	/**
	 * 获取当前时间 格式为yyyy-MM-dd hh:mm:ss 例如2011-11-30 16:06:54
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return formatDate(new Date(), DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * 格式化日期时间字符串
	 * 
	 * @param date
	 * @return 例如2011-11-30 16:06:54
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DEFAULT_DATETIME_PATTERN);
	}

	public static String join(final ArrayList<String> array, String separator) {
		StringBuffer result = new StringBuffer();
		if (array != null && array.size() > 0) {
			for (String str : array) {
				result.append(str);
				result.append(separator);
			}
			result.delete(result.length() - 1, result.length());
		}
		return result.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 1. 处理特殊字符 2. 去除后缀名带来的文件浏览器的视图凌乱(特别是图片更需要如此类似处理，否则有的手机打开图库，全是我们的缓存图片)
	 * 
	 * @param url
	 * @return
	 */
	public static String replaceUrlWithPlus(String url) {
		if (url != null) {
			return url.replaceAll("http://(.)*?/", "")
					.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
		}
		return null;
	}

	/**
	 * 验证手机号码 
	 * @param mobiles 手机号码， 移动、联通、电信运营商的号码段
	 * <p><b>移动号段：</b>134、135、136、137、138、139、147、150、151、152、157、158、159、182、183、187、188</p>
	 * <p><b>联通号段：</b>130、131、132、145、155、156、185、186 </p>
	 * <p><b>电信号段：</b>133、153、180、181、189</p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[^4,\\D]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
     * 验证固定电话号码
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     * <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *  数字之后是空格分隔的国家（地区）代码。</p>
     * <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     * <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkPhone(String phone) { 
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$"; 
        return Pattern.matches(regex, phone); 
    } 
	
	/**
	 * 验证邮箱地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	
	public  static String toMd5(String plainText) { 
		StringBuffer buf = null;
		try { 
		MessageDigest md = MessageDigest.getInstance("MD5"); 
		md.update(plainText.getBytes()); 
		byte b[] = md.digest(); 

		int i; 

		buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) { 
		i = b[offset]; 
		if(i<0) i+= 256; 
		if(i<16) 
		buf.append("0"); 
		buf.append(Integer.toHexString(i)); 
		} 

		System.out.println("result: " + buf.toString());//32位的加密 

		System.out.println("result: " + buf.toString().substring(8,24));//16位的加密 

		
		} catch (Exception e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		}
		return buf.toString(); 
		
		
		} 

	// public static String trim(String str) {
	// if (IsUtil.isNullOrEmpty(str)) {
	// return "";
	// }
	// return str.trim();
	// }
	//
	// /** 将中文转换成unicode编码 */
	// public static String gbEncoding(final String gbString) {
	// char[] utfBytes = gbString.toCharArray();
	// String unicodeBytes = "";
	// for (char utfByte : utfBytes) {
	// String hexB = Integer.toHexString(utfByte);
	// if (hexB.length() <= 2) {
	// hexB = "00" + hexB;
	// }
	// unicodeBytes = unicodeBytes + "\\u" + hexB;
	// }
	// //System.out.println("unicodeBytes is: " + unicodeBytes);
	// return unicodeBytes;
	// }
	//
	// /** 将unicode编码转换成中�?*/
	// public static String decodeUnicode(final String dataStr) {
	// int start = 0;
	// int end = 0;
	// final StringBuffer buffer = new StringBuffer();
	// while (start > -1) {
	// end = dataStr.indexOf("\\u", start + 2);
	// String charStr = "";
	// if (end == -1) {
	// charStr = dataStr.substring(start + 2, dataStr.length());
	// } else {
	// charStr = dataStr.substring(start + 2, end);
	// }
	// char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串�?
	// buffer.append(new Character(letter).toString());
	// start = end;
	// }
	// //System.out.println(buffer.toString());
	// return buffer.toString();
	// }

}
