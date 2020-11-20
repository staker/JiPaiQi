package com.staker.main.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

/**
 * @author tonychen
 * @project tvui-v3
 * @packagename com.aispeech.tvui.common.manager
 * @blog tonychen1991.blogspot.com
 * @email chenyanrongtony@gmail.com
 * @date 2018/9/3
 * @descript Handler帮助类，可以快速切换线程，避免频繁创建Handler
 */
public class HandlerManager {
    private Handler mMainHandler;
    private Handler mSubHandler;
    private Handler mCatHandler;
    private HandlerThread mSubHandlerThread;
    private HandlerThread mCatHandlerThread;

    private HandlerManager(){
        mMainHandler = new Handler(Looper.getMainLooper());
        mSubHandlerThread = new HandlerThread("mSubHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
        mSubHandlerThread.start();
        mSubHandler = new Handler(mSubHandlerThread.getLooper());
        mCatHandlerThread = new HandlerThread("mCatHandlerThread", Process.THREAD_PRIORITY_AUDIO);
        mCatHandlerThread.start();
        mCatHandler = new Handler(mCatHandlerThread.getLooper());
    }


    private static class InnerHolder{
        private static  HandlerManager mInstance = new HandlerManager();
    }

    public static HandlerManager getInstance(){
        return InnerHolder.mInstance;
    }

    public Handler getmMainHandler() {
        return mMainHandler;
    }

    public Handler getSubHandler() {
        return mSubHandler;
    }

    public Handler getCatHandler() {
        return mCatHandler;
    }


    public void postOnMainThread(Runnable runnable){
        mMainHandler.post(runnable);
    }

    public void postOnSubThread(Runnable runnable){
        mSubHandler.post(runnable);
    }

    public void postOnCatThread(Runnable runnable){
        mCatHandler.post(runnable);
    }

    public void postDelayedOnMainThread(Runnable runnable,long delayMillis){
        mMainHandler.postDelayed(runnable,delayMillis);
    }

    public void postDelayedOnSubThread(Runnable runnable,long delayMillis){
        mSubHandler.postDelayed(runnable, delayMillis);
    }

    public void postDelayedOnCatThread(Runnable runnable,long delayMillis){
        mCatHandler.postDelayed(runnable, delayMillis);
    }
}
