package com.staker.main.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * a http manager. manager a transaction
 * 通用的网络请求类，简单版本
 */
public class HttpManager {
    public static final int GET = 0;
    public static final int POST = 1;
    public static final int POST_REST = 2;
    public static final int PUT = 3;
    public static final int DELETE = 4;
    public static final int UPLOAD = 5;
	/**
	 * 将流转换成byte数组
	 * @param is 输入流InputStream
	 * @return 返回byte[]数组
	 * @throws Exception
	 */
	public static byte[] readStreamTobyte(InputStream is) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			bout.write(buffer, 0, len);
		}
		bout.close();
		is.close();
		return bout.toByteArray();
	}
	 /**
	  * 
	  * @param inSream 输入流
	  * @param charsetName 字符集编码(默认是utf-8)
	  * @return String
	  * @throws Exception
	  */
	    public static String readStreamToString(InputStream inSream, String charsetName) throws Exception {
	         ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	         byte[] buffer = new byte[1024];	 
	         int len = -1;	 
	         while( (len = inSream.read(buffer)) != -1 ){
	              outStream.write(buffer, 0, len); 
	         }	 
	         byte[] data = outStream.toByteArray();	 
	         outStream.close();	 
	         inSream.close();
	         if(charsetName==null){
	        	return new String(data);
	         }
	         return new String(data, charsetName);
	 
	    }
	  /**
	   * @param pageUrl 请求的url
	   * @param charsetName 编码方式 默认utf-8
	   * @param sMethod 请求方式  默认 GET
	   * @return String
	   * 注：这里没有开启线程
	   */
	
	public static String getContentFromUrl(String pageUrl, String charsetName, String sMethod){
		String result=null;
		try {
			URL url = new URL(pageUrl);    //定义路径URL对象
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();    //打开连接
	        conn.setConnectTimeout(10000);    //设置连接超时	
	        //conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
	        if(sMethod==null){
    			conn.setRequestMethod("GET");//默认请求方式
    		}else{
    			conn.setRequestMethod(sMethod);
    		} 
    		if (conn.getResponseCode() == 200) {
    			InputStream is = conn.getInputStream(); //得到网络返回的输入流
    			result = readStreamToString(is, charsetName);//解析获取字符串	
    		} 
		} catch (Exception e) {
			return null;
		}
        return   result;
    }
  
}
