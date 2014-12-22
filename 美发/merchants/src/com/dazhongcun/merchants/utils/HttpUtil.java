package com.dazhongcun.merchants.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Http���󹤾���
 * @author gonglei
 *
 */
public class HttpUtil {

	/**ʵ��AsyncHttpClient���ɿ�Դ��AsyncHttpClient�ṩ*/
	private static AsyncHttpClient sHttpUtil = new AsyncHttpClient();

	public static void get(String url, TextHttpResponseHandler handler) {
		sHttpUtil.get(url, handler);
	}

	public static void get(String url, RequestParams params,
			TextHttpResponseHandler handler) {
		sHttpUtil.get(url, params, handler);
	}

	public static void get(String url, FileAsyncHttpResponseHandler handler) {
		sHttpUtil.get(url, handler);
	}
}
