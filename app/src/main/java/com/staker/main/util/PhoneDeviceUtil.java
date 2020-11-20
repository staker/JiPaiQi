package com.staker.main.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

public class PhoneDeviceUtil {
	public static final String Reg_Invalid_String = "[\\x00-\\x0b-\\x0c\\x0e-\\x1f]+";
//	public static final String Reg_Invalid_String = "[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]+";
	/**
	 * 判断 字符串是否为手机号码
	 * @param phoneNumber 字符串
	 * @return boolean
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "(11[0-9]|12[0-9]|13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|19[0-9]|0[1-9]|0[1-9][0-9]|0[1-9][0-9][0-9]|18[0123456789])\\d{8}";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
	/**
	 * 获得系统的API 的等级Level(因为有很多方法只在特定的api里面可以使用，所以有时候还是需要判断一下的)
	 * @param context
	 * @return int
	 */
	public static int getAPIlevel(Context context){
		return Build.VERSION.SDK_INT;
	}
	/**
	 * 获得设备的信息
	 * @return 格式如下：
	 * Model:UTime_FX;SDK:17;BRAND:UTime;CPU:armeabi-v7a;PRODUCT:UTime_FX
	 */
	public static String getMachineInfo(){
		return new StringBuilder().append("Model:").append(escapeJsonValue(Build.MODEL))
		.append(";SDK:").append(escapeJsonValue(Build.VERSION.SDK))
		.append(";BRAND:").append(escapeJsonValue(Build.BRAND))
		.append(";CPU:").append(escapeJsonValue(Build.CPU_ABI))
		.append(";PRODUCT:").append(escapeJsonValue(Build.PRODUCT)).toString();
	}

	/**
	 * 获取设备的唯一序列号
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context){
		try{
			TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
			String szImei = TelephonyMgr.getDeviceId();
			return szImei;
		}catch (Exception e){
			return "";
		}
	}



	/**
	 *  主要是把转义字符  转化成字符串的形式  如把\n转化成\\n这样对面就能拿到\n
	 *  
	 * @param str 需要转化的字符串
	 * @return String
	 */
	public static String escapeJsonValue(String str){
		return getSafeString(str)
				.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\r", "\\r")
				.replace("\n", "\\n")
				.replace("\t", "\\t")
				.replaceAll(Reg_Invalid_String, "");
	}
	
	
	/**
	 * 如果字符串str为null，则返回""
	 * @param str
	 * @return ""或者str
	 */
	private static String getSafeString(String str){
		return str == null ? "" : str;
	}
}
