package com.staker.main.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParseUrlToBitmap {
	/**
	 * 这个方法是解析网站上面的图片，返回一个bitmap对象，此外这个方法还附带压缩的功能
	 * @param uri  图片的url地址
	 * @param newWidth   如果newWidth<0那么保持原来的尺寸
	 * @return  Bitmap
	 * @throws Exception
	 * 
	 * 此外data.length代表的是图片的字节数，  可以大致的认为1000字节为1kb
	 * 而图片缩放为100*100之后的字节约为：
	 * 
	 * 
	 *SoftReference<Bitmap>
	 *以后bitmap都用SoftReference包围，那么就不会出现内存溢出了，因为它会自动释放前面的
	 */
	public static Bitmap parseUrlToPic(String uri, int newWidth) throws Exception {
			
			URL url=new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(10000);
			connection.setRequestMethod("GET");
			connection.connect();			
			if(connection.getResponseCode()== HttpURLConnection.HTTP_OK){
				InputStream is=connection.getInputStream();
				byte[] data = readStream(is);
				Bitmap bitmap= BitmapFactory.decodeByteArray(data, 0, data.length);;
				if(newWidth>0){
					bitmap=ImageHandleUtil.scaleBitmap(bitmap, newWidth, newWidth);
				}else if(data.length>20000){
					//大于20K就压缩,300*300大致是3kb左右，我暂时不是很清楚，有待验证
					bitmap=ImageHandleUtil.scaleBitmap(bitmap, 300, 300);
				}
				try {
					data=null;
					is.close();
					is=null;
				} catch (Exception e) {
					//这里面主要是释放资源
				}
				return bitmap;
			}					
		return null;
		
	}
	/**
	 * 将输入流转换成字节
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream(3048);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			bout.write(buffer, 0, len);
		}
		bout.close();
		is.close();
		return bout.toByteArray();
	}

}
