package com.staker.main.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * JSON转换工具类 使用前调用getInstance方法
 * 
 * @author M.c
 * 
 * @date 2014-11-13
 * @toJson 将对象转换成json字符串
 * @getValue 在json字符串中，根据key值找到value
 * @json2Map 将json格式转换成map对象
 * @json2Bean 将json转换成bean对象
 * @json2List 将json格式转换成List对象
 * @Obj2Map obj 转为 map
 * 
 */
public class GsonUtil {
//	private static Configuration config = null;
//	private SharedPreferences share;

	private static GsonUtil instance;//单例模式
	private  Gson gson;
	private GsonUtil( ) {
		gson =new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();//这是默认的创建Gson实体类的方式
	}
	public static GsonUtil getInstance() {
		if (instance == null) {
			instance=new GsonUtil();
		}
		return instance;
	}



	/**
	 * 将实体对象转换成json字符串
	 * @param object
	 * @return
	 */
	public String toJson(Object object) {
		if (object == null) {
			return "{}";
		}
		return gson.toJson(object);
	}
	/**
	 * 将json转换成实体Object对象
	 *
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public <T> T json2Bean(String json, Class<T> clazz) {
		T object = null;
		if (gson != null) {
			object = gson.fromJson(json, clazz);
		}
		return object;
	}











	/**
	 * 在json字符串中，根据key值找到value
	 * 
	 * @param data
	 * @param key
	 * @return
	 */

	public String getValue(String data, String key) {
			try {
				JSONObject jsonObject = new JSONObject(data);
				return jsonObject.getString(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return null;
	}


	/**
	 * 将json格式转换成map对象
	 * 
	 * @param json
	 * @return
	 */
	public Map<String, Object> json2Map(String json) {
		Map<String, Object> objMap = null;
		if (gson != null) {
			Type type = new TypeToken<Map<String, Object>>() {
			}.getType();
			objMap = gson.fromJson(json, type);
		}
//		if (objMap == null) {
//			 objMap = new HashMap<String, Object>();
//			return null;
//		}
		return objMap;
	}






	/**
	 * 将json格式转换成List对象
	 * 
	 * @param json
	 * @param type
	 * @return
	 * 用法  ArrayList<UserPojo> list=GsonUtil.getInstance().json2List(jsonStr,new TypeToken<List<UserPojo>>(){}.getType());
	 * jsonStr就是对应的json数组字符串

	 */
	public <T> T json2List(String json, Type type) {
		if (gson != null) {
			return gson.fromJson(json, type);
		}
		return null;
	}

	/**
	 * obj 转为 map
	 * 
	 * @param obj
	 *            需要转的对象
	 * @return
	 */
	public Map<String, Object> Obj2Map(Object obj) {
		if (obj != null) {
			return json2Map(toJson(obj));
		}
		return null;
	}
}
