package com.staker.main.logic;

import android.app.Activity;
import android.content.Intent;


public class JumpActivityUtil {


	/**
	 * 跳转到普通的Activity不加任何的数据传递
	 * @param activity
	 * @param t
	 */
	public static void showNormalActivity(Activity activity, Class<?> t){
		Intent i = new Intent(activity, t);
		activity.startActivity(i);
	}
	/**
	 * 跳转到普通的Activity不加任何的数据传递  并且finish掉当前的activity
	 * @param activity
	 * @param t
	 */
	public static void showNormalActivityFinishSelf(Activity activity, Class<?> t){
		Intent i = new Intent(activity, t);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(i);		
//		activity.overridePendingTransition(R.anim.screen_2_1,R.anim.screen_1_0);加跳转动画
		activity.finish();
	}


	public static void showPackageActivity(Activity activity,String packageName,String activityName){
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClassName(packageName, activityName);
		activity.startActivity(intent);
	}




}
