package com.staker.main.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 这是一个和广播相关的工具类，能快速发送广播和注册广播 
 * @author 小木桩（staker） 
 */
public class BroadcastUtil {
	/**
	 * 发送一个广播 
	 * @param action
	 *            广播字符串
	 */
	public static void sendBroadcast(Context context, String action) {
		if(context==null){
			return;
		}
		Intent intent = new Intent();
		intent.setAction(action);
		context.sendBroadcast(intent);
	}
	/**
	 * 发送一个广播 顺带传一个字符串数据
	 * @param data01
	 * 		要传的值，key也是data01
	 * @param action
	 *            广播字符串
	 */
	public static void sendBroadcast1(Context context, String data01, String action) {
		if(context==null){
			return;
		}
		Intent intent = new Intent();
		intent.setAction(action);
		intent.putExtra("data01", data01);
		context.sendBroadcast(intent);
	}


	/**
	 * 这个方法是通过广播传送一个自定义的对象类
	 * @param context
	 * @param object  在广播接收的时候需要强转成对应的类型
	 * @param action
	 */
	public static void sendBroadcastByImg(Context context, Object object, String action) {
		if(context==null){
			return;
		}
		Intent intent = new Intent();
		intent.setAction(action);
		intent.putExtra("dateImg",(Serializable) object);
		context.sendBroadcast(intent);
	}
	
	/**
	 * 发送一个广播 顺带传2个字符串数据,字符串的key就是传进来的字符串的变量名字
	 * @param data01
	 * 		要传的值，key是data01
	 * @param data02
	 * 		要传的值，key是data02
	 * @param action
	 *            广播字符串
	 */
	public static void sendBroadcast2(Context context, String data01, String data02, String action) {
		if(context==null){
			return;
		}
		Intent intent = new Intent();
		intent.setAction(action);
		intent.putExtra("data01", data01);
		intent.putExtra("data02", data02);
		context.sendBroadcast(intent);
	}
	
	/**
	 * 动态注册1个广播
	 * @param context 上下文
	 * @param action 广播字符串
	 * @param receiver 广播实体类
	 */
	public static void registerReceiver1(Context context, BroadcastReceiver receiver, String action){
		if(context==null||receiver==null){
			return;
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(action);
		
		context.registerReceiver(receiver, filter);
	}
	/**
	 * 动态注册2个广播
	 * @param context
	 * @param receiver 广播实体类
	 * @param action1
	 * @param action2
	 */
	public static void registerReceiver2(Context context, BroadcastReceiver receiver, String action1, String action2){
		if(context==null||receiver==null){
			return;
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(action1);
		filter.addAction(action2);
		context.registerReceiver(receiver, filter);
	}
	/**
	 * 动态注册3个广播
	 * @param context
	 * @param receiver 广播实体类
	 * @param action1
	 * @param action2
	 * @param action3
	 */
	public static void registerReceiver3(Context context, BroadcastReceiver receiver, String action1, String action2, String action3){
		if(context==null||receiver==null){
			return;
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(action1);
		filter.addAction(action2);
		filter.addAction(action3);
		context.registerReceiver(receiver, filter);
	}
	/**
	 * 动态注册4个广播
	 * @param context
	 * @param receiver 广播实体类
	 * @param action1
	 * @param action2
	 * @param action3
	 * @param action4
	 */
	public static void registerReceiver4(Context context, BroadcastReceiver receiver, String action1, String action2, String action3, String action4){
		if(context==null||receiver==null){
			return;
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(action1);
		filter.addAction(action2);
		filter.addAction(action3);
		filter.addAction(action4);
		context.registerReceiver(receiver, filter);
	}
	
	/**
	 * 注册多个广播
	 * @param context
	 * @param receiver
	 * @param listAction  广播数组
	 */
	public static void registerReceiverList(Context context, BroadcastReceiver receiver, ArrayList<String> listAction){
		if(context==null||receiver==null){
			return;
		}
		IntentFilter filter = new IntentFilter();
		for (int i = 0; i < listAction.size(); i++) {
			filter.addAction(listAction.get(i));
		}
		context.registerReceiver(receiver, filter);
	}
	
	
}
