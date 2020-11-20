package com.staker.main.util;

import android.util.Log;


/**
 * @author 小木桩（staker）
 * 简单的打印数据封装类
 */
public class Logg {
	private static final String TAG = "staker";
	private static boolean isDebug=true;//是否打印信息
	public static void d(String msg) {
		if(isDebug){
			Log.d(TAG, msg);
		}	
	}
	public static void e(String msg) {
		if(isDebug){			
			Log.e(TAG, msg);
		}
	}
	public static void e(String msg, Exception e) {
		if(isDebug){
			Log.e(TAG, msg,e);
		}
	}
	public static void w(String msg) {
		if(isDebug){
			Log.w(TAG, msg);
		}
	}
}
