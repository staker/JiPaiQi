package com.staker.main.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;

import java.io.ByteArrayOutputStream;

/**
 * 这是一个截取屏幕快照的工具类，
 */
public class ScreenBitmapUtil {
	/**
	 * 把Bitmap 转成 Byte 
	 * @param bm
	 * @return byte[]
	 */
	public static byte[] BitmapToBytes(Bitmap bm){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	/**
	 * 将屏幕截图成bitmap，再将bitmap转化成byte数组
	 * @param activity
	 * @return byte[]
	 */
	public static byte[] printScreenToBytes(Activity activity){
		View view1 = activity.getWindow().getDecorView();
		Display display = activity.getWindowManager().getDefaultDisplay();
		view1.layout(0, 0, display.getWidth(), display.getHeight());
		view1.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
		return BitmapToBytes(bitmap);
	}
	
	
	/**
	 * 将屏幕截图成bitmap,去掉电池等标题栏
	 * @param activity
	 * @return Bitmap
	 */
	public static Bitmap printScreenToBitmap(Activity activity){
		//方法一
//		View view1 = activity.getWindow().getDecorView();
//		Display display = activity.getWindowManager().getDefaultDisplay();
//		view1.layout(0, 0, display.getWidth(), display.getHeight());
//		view1.setDrawingCacheEnabled(true);
//		Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
		
		//方法二
		Display display = activity.getWindowManager().getDefaultDisplay();
		View view= activity.getWindow().getDecorView();
		Bitmap bitmap = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
		view.draw(new Canvas(bitmap));
		
		//上面两个方法获得的是含有标题栏的图片
		
		
		
		//下面的方法是去掉标题栏
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;	
		Bitmap bmpall= Bitmap.createBitmap(display.getWidth(),display.getHeight()-rect.top, Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(bmpall);
		canvas.drawBitmap(bitmap, 0, -statusBarHeight, new Paint());
		return bmpall;
	}
}
