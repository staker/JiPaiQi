/**
 * 
 */
package com.staker.main.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class StakerUtil {

	/**
	 * 隐藏键盘
	 * 
	 * @param activity
	 *            上下文
	 * @param edt
	 *            对应的输入框控件
	 */
	public static void hidenKeyboard(Activity activity, EditText edt) {
		InputMethodManager inputManeger = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
		if (inputManeger.isActive()) {
			inputManeger.hideSoftInputFromWindow(edt.getWindowToken(), 0);
		}
	}

	/**
	 * 弹出键盘
	 * 
	 * @param activity
	 *            上下文
	 * @param edt
	 *            对应的输入框控件
	 */
	public static void showKeyBoard(Activity activity, EditText edt) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edt, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 跳转到网页显示界面
	 * 
	 * @param activity
	 * @param url
	 *            跳转的地址
	 */
	public static void showWebView(Activity activity, String url) {
		try {
			Uri uri = Uri.parse(url);
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			activity.startActivity(it);
		} catch (Exception e) {
		}
	}

	/**
	 * 跳转到第三方应用市场的界面
	 * 
	 * @param activity
	 * @param packageName
	 */
	public static void showMarketView(Activity activity, String packageName) {
		try {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
			activity.startActivity(browserIntent);
		} catch (Exception e) {
			activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
		}
	}

	/**
	 * 限制输入框的输入字符个数
	 * 
	 * @param edt
	 *            对应的输入框控件
	 * @param count
	 *            字符个数
	 */
	public static void setEditTextMaxLength(EditText edt, int count) {
		edt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(count) });
	}
	
	/**
	 * 把输入框的光标移动到最后面
	 * @param edt
	 */
	public static void setEditTextCursorLast(EditText edt) {
		edt.setSelection(edt.getText().length());
	}
	
	
	
	/**
	 * @param txt
	 *            给textView设置下划线
	 */
	public static void setLinedTextView(TextView txt) {
		txt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

	/**
	 * 给EditText设置默认字符串，并且把EditText的光标移动到最后面
	 * 
	 * @param edt
	 *            对应的输入框控件
	 * @param content
	 *            给edt输入的默认文字
	 */
	public static void setEditTextCursorEnd(EditText edt, String content) {
		if (content != null) {
			edt.setText(content);
			edt.setSelection(edt.getText().length());// 因为edt可能设置了输入个数限制，所以使用这个方法绝对的保证无误
		}
	}

	/**
	 * 获取屏幕的宽度
	 * 
	 * @param context
	 * @return int
	 */
	public static int getWindowWidth(Context context) {
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;
		return mScreenWidth;
	}

	/**
	 * 获取屏幕的高度
	 * 
	 * @param context
	 * @return int
	 */
	public static int getWindowHeigh(Context context) {
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenHeigh = dm.heightPixels;
		return mScreenHeigh;
	}

	/**
	 * 设置view的透明度
	 * 
	 * @param v
	 * @param alpha
	 *            值是从0到1，1表示全部透明
	 */
	@SuppressLint("NewApi") public static void setAlpha(View v, float alpha) {
		if (Build.VERSION.SDK_INT >= 11) {
			v.setAlpha(alpha);
		}
	}

	/**
	 * 调用系统方法回收已经不是用的bitmap对象，一定要记得调用，这是一个很好的习惯
	 * 
	 * @param bitmap
	 */
	public static void recycleBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		System.gc();
	}

	/**
	 * 复制一段 text文字到剪切板
	 * @param context
	 * @param text
	 */
	@SuppressLint("NewApi")
	public static void copyStringToClipboard(Context context, String text){
		ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText(text,text);
		//设置剪贴板的主要剪贴对象。
		clipboard.setPrimaryClip(clip);
	}
	
	
	
	/**
	 * 获取对应在Manifest 里面MetaData下的value值，这个方法一般的作用是用来获取渠道名
	 * @param context
	 * @param key  对应的键
	 * @return  value
	 */
	public static String getManifestMetaData(Context context, String key){
		try {
			String value=context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString(key);
			return value;
		} catch (Exception e) {
		}
		return null;
	}
}
