package com.staker.main.view.util;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.staker.main.application.MyApplication;
import com.staker.master.R;


public class ToastUtil {

	static Toast toast;
	public static void showToastInCenter(Context context, String content, int time) {
		if(context==null){
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.layout_toast, null);
		TextView tv = (TextView) view.findViewById(R.id.txt_tips);
		tv.setText(content);
		//防止多次点击按钮出现很多toast一直不消失
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setView(view);
		toast.setDuration(time);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showToast(String info){
		showToast(info, MyApplication.getContext(),0);
	}






	public static void showToast(String info, Activity context, int time){
		if(context==null){
			return;
		}
		LayoutInflater inflater = context.getLayoutInflater();
		View view=inflater.inflate(R.layout.layout_toast, null);
		TextView txt=(TextView) view.findViewById(R.id.txt_tips);//
		txt.setText(info+"");
		//防止多次点击按钮出现很多toast一直不消失
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setView(view);
		toast.setDuration(time);
		toast.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.BOTTOM, 0, 120);
		toast.show();
	}
	public static void showToast(int stringId, Activity context, int time){
		if(context==null){
			return;
		}
		LayoutInflater inflater = context.getLayoutInflater();
		View view=inflater.inflate(R.layout.layout_toast, null);
		TextView txt=(TextView) view.findViewById(R.id.txt_tips);
		txt.setText(stringId);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setView(view);
		toast.setDuration(time);
		toast.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.BOTTOM, 0, 120);
		toast.show();
	}
	
	
	
	
	public static void showToast(int stringId, Context context, int time){
		if(context==null){
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View view=inflater.inflate(R.layout.layout_toast, null);
		TextView txt=(TextView) view.findViewById(R.id.txt_tips);
		txt.setText(stringId);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setView(view);
		toast.setDuration(time);
		toast.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.BOTTOM, 0, 120);
		toast.show();	
	}
	public static void showToast(String info, Context context, int time){
		if(context==null){
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View view=inflater.inflate(R.layout.layout_toast, null);
		TextView txt=(TextView) view.findViewById(R.id.txt_tips);
		txt.setText(info+"");
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setView(view);
		toast.setDuration(time);
		toast.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.BOTTOM, 0, 120);
		toast.show();
	}
}
