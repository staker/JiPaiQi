package com.staker.main.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * @author staker
 * 04-08-08 1.修改图片的获取方式,采用最优的方式获取， 2.可以对获取的图片进行压缩
 */
public class AssetsHelper {
	/**
	 * 通过图片的获得对应的商品图片
	 * 
	 * @param name 图片的名字
	 * @param scaleSize  需要缩放到的尺寸，如果为2，则宽度和高度缩放为原来的一半，以此类推
	 * @return Bitmap
	 */
	public static Bitmap getShopPicByName(Context context, String name, int scaleSize) {
		String picPath = "shop/"+name + ".png";
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			AssetManager asset = context.getAssets();
			is = asset.open(picPath);
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			if(scaleSize>1){
				opt.inSampleSize = scaleSize;// 图片宽高都为原来的二分之一，即图片为原来的四分之一
			}
			bitmap = BitmapFactory.decodeStream(is,null,opt);
		} catch (Exception e) {
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (Exception e2) {
			}
		}
		return bitmap;
	}

	
	/**
	 * 通过图片的获得对应的组合图片
	 * 
	 * @param name 图片的名字
	 * @param scaleSize  需要缩放到的尺寸，如果为2，则宽度和高度缩放为原来的一半，以此类推
	 * @return Bitmap
	 */
	public static Bitmap getPorPicByName(Context context, String name, int scaleSize) {
		String picPath = "zuhe/p320/"+name + ".jpg";
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			AssetManager asset = context.getAssets();
			is = asset.open(picPath);
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			if(scaleSize>1){
				opt.inSampleSize = scaleSize;// 图片宽高都为原来的二分之一，即图片为原来的四分之一
			}
			bitmap = BitmapFactory.decodeStream(is,null,opt);
		} catch (Exception e) {
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (Exception e2) {
			}
		}
		return bitmap;
	}


	  /**
	    * 获取文件夹大小
	    * @param file File实例
	    * @return long 单位为M
	    * @throws Exception
	    */
	    public static long getFolderSize(java.io.File file)throws Exception {
	    long size = 0;
	    java.io.File[] fileList = file.listFiles();
	    for (int i = 0; i < fileList.length; i++)
	    {
	    if (fileList[i].isDirectory())
	    {
	    size = size + getFolderSize(fileList[i]);
	    } else
	    {
	    size = size + fileList[i].length();
	    }
	    }
	    return size/1048576;
	    }
	
}