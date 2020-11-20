package com.staker.main.util;

import android.os.AsyncTask;
import android.os.Handler;

/**
 * 
 * @author 小木桩（staker）
 *
 *	封装的一个异步任务类
 */
public class AsynTaskUtil {
	public interface IAsynTask{
		public Object run();
		public void over(Object object);
	}
	
	/**
	 * 这个暂时被废弃掉，因为采用异步的方式  会阻塞。原因是不能开始太多个异步任务
	 * 但是有时候也是很有用的，比如在listview的时候，就不适合开启太多线程，而是可以用异步的方式，这样界面就不需要同时加载很多数据，而是一次进行
	 * @param iTask
	 */
	public static void startTask(final IAsynTask iTask){
		if(iTask==null){
			return;
		}
		new AsyncTask<Void, Void, Object>() {
			@Override
			protected Object doInBackground(Void... params) {
				Object object=iTask.run();
				return object;				
			}
			@Override
			protected void onPostExecute(Object resultObject) {
				iTask.over(resultObject);			
			}
		}.execute();	
	}
	
	
	
	
	
	
	private Object object=null;
	private IAsynTask iTask;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			iTask.over(object);
		}
	};
	/**
	 * 需要构造之后才能调用的异步方法，采用线程操作
	 * @param iTask
	 */
	public void startTaskOnThread(final IAsynTask iTask){	
		if(iTask==null){			
			return;
		}
		this.iTask=iTask;
		new Thread(){
			public void run() {
				object=iTask.run();
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
}
