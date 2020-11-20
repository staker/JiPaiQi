package com.staker.main.view;


import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.staker.master.R;


/**
 * 
 * @author staker
 * 通用的加载提示对话框，用于显示正在加载数据
 * 04-06-26
 */
public class LoadingDialog {
	private Dialog dialog;
	private static LoadingDialog singleInstance;
	private ImageView imgSuccess;
	private TextView txtContent;
	private ProgressBar barRefreshing;
	private String sText;


	private LoadingDialog() {
	}
	public static LoadingDialog getInstance() {
		if (singleInstance == null) {
			singleInstance = new LoadingDialog();
		}
		return singleInstance;
	}

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(sText==null){
					txtContent.setVisibility(View.GONE);
				}else{
					txtContent.setText(sText);
					txtContent.setVisibility(View.VISIBLE);
				}
			} catch (Exception e) {
			}
		};
	};
	/**
	 * 弹出一个刷新的 dialog
	 * @param isShow 弹出或者隐藏
	 * @param context 上下文
	 * @param showText 如果为null，则不显示下方的文字，不为空则显示为shottext文字
	 */
	public void showDialogLoading(boolean isShow, Activity context, String showText) {
		if (dialog != null && dialog.isShowing()) {
			barRefreshing.setVisibility(View.GONE);
			imgSuccess.setVisibility(View.VISIBLE);

			if(txtContent!=null){
				txtContent.setVisibility(View.GONE);
			}
			dialog.dismiss();
			dialog = null;
		}
		if (isShow) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.dialog_loading_view, null);
			if(showText!=null){
				txtContent=(TextView) v.findViewById(R.id.txt_content);
				txtContent.setText(showText);
				txtContent.setVisibility(View.VISIBLE);
			}
			imgSuccess=(ImageView) v.findViewById(R.id.img_success);
			barRefreshing=(ProgressBar) v.findViewById(R.id.bar_refreshing);
			dialog = new Dialog(context, R.style.help_dialog);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setContentView(v);
			if(context.isFinishing()){
				dialog = null;
				return;
			}
			dialog.show();				
		}
	}



















	/**
	 * 此方法可以在线程里面调用，给提示dialog添加文字修饰
	 * 当text为null的时候，则隐藏文字
	 * @param text
	 * 此方法设置text会立即执行
	 */
	public void setLoadingText(String text){
		sText=text;
		handler.sendEmptyMessage(0);		
	}
	
	/**
	 * 此方法可以在线程里面调用，给提示dialog添加文字修饰
	 * 当text为null的时候，则隐藏文字
	 * 此方法会在指定的延迟时间之后才执行
	 * @param text
	 */
	public void setLoadingTextDelay(String text, long delayTime){
		sText=text;
		handler.sendEmptyMessageDelayed(0, delayTime);	
	}
}
