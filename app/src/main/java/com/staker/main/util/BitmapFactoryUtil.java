package com.staker.main.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * 
 * @author staker
 * 注：下面的所有方法都不适合矢量图，因为采用的是Bitmap.Config.RGB_565，所以透明像素都已经去掉了
 * 
 * 04-08-08  优化图片的读取和设置图片的方式
 * 04-08-18 这个类本来写好了，可是每次提交他都变了。
   14-12-24  增加读取图片自动判断缩放的方法
 */
public class BitmapFactoryUtil {
	public static Bitmap.Config Bitmap_RGB_565= Bitmap.Config.RGB_565;
	public static Bitmap.Config Bitmap_RGB_888= Bitmap.Config.ARGB_8888;


	/**
	 * 通过输入流获取图片bitmap
	 * @param stream
	 * @return
	 */
//	public static Bitmap readBmpFromStream(InputStream stream) {
//		final BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inPreferredConfig = Bitmap.Config.RGB_565;
//		options.inPurgeable = true;//true表示可以被系统回收，false表示内存不足时也不能被回收
//		options.inInputShareable = true;
//		options.inJustDecodeBounds = false;
//
//		InputStream is=connection.getInputStream();
//		byte[] data = readStream(is);
//		Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);;
//
//		Bitmap b = BitmapFactory.decodeStream(stream);
//		return b;
//	}

	
	/**
	 * 根据期望的宽度和高度值，去对图片进行缩放
	 * @param filePath  图片的sd卡路劲
	 * @param reqWidth  期望宽度值
	 * @param reqHeight 期望高度值
	 * @return  Bitmap
	 */
	public static Bitmap readBmpFromPath(String filePath, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; //设置只读取图片的属性，不将图片内容读到内存
		BitmapFactory.decodeFile(filePath, options);
		if(reqWidth<=0||reqHeight<=0){
			options.inSampleSize=1;
		}else{
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		}
		  		
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;//true表示可以被系统回收，false表示内存不足时也不能被回收
		options.inInputShareable = true;
		options.inJustDecodeBounds = false; 
		Bitmap b = BitmapFactory.decodeFile(filePath, options);
		return b;
	}
	/**
	 * 根据期望的宽度和高度值，去对图片进行缩放
	 * @param filePath  图片的sd卡路劲
	 * @param reqWidth  期望宽度值
	 * @param reqHeight 期望高度值
	 * @param config    读取图片的格式，默认是Bitmap_RGB_565  传null即为Bitmap_RGB_565
	 * @return  Bitmap
	 */
	public static Bitmap readBmpFromPath(String filePath, int reqWidth, int reqHeight, Bitmap.Config config) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; 
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
		if(config==null){
			options.inPreferredConfig = Bitmap.Config.RGB_565;
		}else{
			options.inPreferredConfig=config;
		}	
		options.inPurgeable = true;//true表示可以被系统回收，false表示内存不足时也不能被回收
		options.inInputShareable = true;
		options.inJustDecodeBounds = false; 
		Bitmap b = BitmapFactory.decodeFile(filePath, options);
		return b;
	}
	

	
	/**
	 * 以最省内存的方式
	 * 从本地资源中读取图片  允许压缩
	 * 
	 * 缩放：是否缩放会根据真正拿到的图片大小而定，如果拿到的图片大小比我们期望的要大，那么会进行缩放
	 * 所以缩放后的图片宽度和高度一定是大于等于我们的期望值
	 * @param context
	 * @param resId  图片的id
	 * @param reqWidth  希望缩放的宽度
	 * @param reqHeight  希望缩放的高度
	 * @return
	 */
	public static Bitmap readBmpResource(Context context, int resId, int reqWidth, int reqHeight){
		final BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true; 
		BitmapFactory.decodeResource(context.getResources(), resId, opt);
		opt.inSampleSize = calculateInSampleSize(opt, reqWidth, reqHeight);
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inJustDecodeBounds = false; 
		return BitmapFactory.decodeResource(context.getResources(), resId, opt);
	}
	/**
	 * 以最省内存的方式
	 * 从本地资源中读取图片  允许压缩
	 * @param context
	 * @param resId  图片的id
	 * @param reqWidth  希望缩放的宽度
	 * @param reqHeight  希望缩放的高度	
	 * @param config    读取图片的格式，默认是Bitmap_RGB_565  传null即为Bitmap_RGB_565
	 * @return
	 */
	public static Bitmap readBmpResource(Context context, int resId, int reqWidth, int reqHeight, Bitmap.Config config){
		final BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true; 
		BitmapFactory.decodeResource(context.getResources(), resId, opt);
		opt.inSampleSize = calculateInSampleSize(opt, reqWidth, reqHeight);
		if(config==null){
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
		}else{
			opt.inPreferredConfig=config;
		}
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inJustDecodeBounds = false; 
		return BitmapFactory.decodeResource(context.getResources(), resId, opt);




	}
	
	

	/**
	 * 根据期望大小，来对图片进行压缩后，设置给imageview控件
	 * @param img
	 * @param filePath
	 * @param reqWidth
	 * @param reqHeight
	 */
	public static void setImagePath(ImageView img, String filePath, int reqWidth, int reqHeight) {
		Bitmap bitmap=readBmpFromPath(filePath, reqWidth, reqHeight);
		if(bitmap!=null&&img!=null){
			img.setImageBitmap(bitmap);
			bitmap=null;
		}
	}
	/**
	 * 根据期望大小，来对图片进行压缩后，设置给imageview控件
	 * @param img
	 * @param filePath
	 * @param reqWidth
	 * @param reqHeight
	 * @param config    读取图片的格式，默认是Bitmap_RGB_565  传null即为Bitmap_RGB_565
	 */
	public static void setImagePath(ImageView img, String filePath, int reqWidth, int reqHeight, Bitmap.Config config) {
		Bitmap bitmap=readBmpFromPath(filePath, reqWidth, reqHeight,config);
		if(bitmap!=null){
			img.setImageBitmap(bitmap);
			bitmap=null;
		}
	}

	/**
	 * 
	 * @param context
	 * @param img    
	 * @param resId   图片的id
	 * @param reqWidth  期望宽度值
	 * @param reqHeight  期望高度值
	 */
	public static void setImageResources(Context context, ImageView img, int resId, int reqWidth, int reqHeight) {
		Bitmap bitmap=readBmpResource(context, resId, reqWidth, reqHeight);
		if(bitmap!=null){
			img.setImageBitmap(bitmap);
			bitmap=null;
		}
	}
	/**
	 * 
	 * @param context
	 * @param img    
	 * @param resId   图片的id
	 * @param reqWidth  期望宽度值
	 * @param reqHeight  期望高度值
	 * @param config    读取图片的格式，默认是Bitmap_RGB_565  传null即为Bitmap_RGB_565
	 */
	public static void setImageResources(Context context, ImageView img, int resId, int reqWidth, int reqHeight, Bitmap.Config config) {
		Bitmap bitmap=readBmpResource(context, resId, reqWidth, reqHeight,config);
		if(bitmap!=null){
			img.setImageBitmap(bitmap);
			bitmap=null;
		}
	}
	
	
	/**
	 * 根据期望的高度和宽度值计算目标需要缩放的倍数
	 * @param options
	 * @param reqWidth  期望宽度
	 * @param reqHeight 期望高度
	 * @return  缩放的倍数
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
	    // 源图片的高度和宽度  
	    final int height = options.outHeight;  
	    final int width = options.outWidth;  
	    int inSampleSize = 1;  
	    if (height > reqHeight || width > reqWidth) {  
	        // 计算出实际宽高和目标宽高的比率  
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高  
	        // 一定都会大于等于目标的宽和高。  
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;  
	    }  
	    return inSampleSize;  
	} 
	
}