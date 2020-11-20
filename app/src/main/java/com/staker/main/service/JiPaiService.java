package com.staker.main.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.staker.main.application.MyApplication;
import com.staker.main.constant.Constants;
import com.staker.main.util.BroadcastUtil;
import com.staker.main.util.Logg;
import com.staker.main.view.CardFunctionView;
import com.staker.main.view.CardResultView;

/**
 * Created by dongzhong on 2018/5/30.
 */

public class JiPaiService extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParamsResult,layoutParamsFunction;

    private CardResultView cardResultView;
    private CardFunctionView cardFunctionView;
    private boolean isWindowShow=false;

    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        initWindowView();
        initStopReceiver();
    }
    private void initStopReceiver(){
        BroadcastUtil.registerReceiver1(this,stopJipaiReceiver, Constants.Action_Stop_Jipai);
    }
    private void initWindowView(){
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParamsResult = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParamsResult.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParamsResult.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParamsResult.format = PixelFormat.RGBA_8888;
        layoutParamsResult.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        layoutParamsResult.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL ;
        layoutParamsResult.width = MyApplication.getContext().screenWidth>MyApplication.getContext().screenHeight?MyApplication.getContext().screenWidth:MyApplication.getContext().screenHeight;
        layoutParamsResult.height = 200;
        layoutParamsResult.x = 0;
        layoutParamsResult.y = 0;

        layoutParamsFunction = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParamsFunction.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParamsFunction.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParamsFunction.format = PixelFormat.RGBA_8888;
        layoutParamsFunction.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        layoutParamsFunction.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        layoutParamsFunction.width = MyApplication.getContext().screenWidth>MyApplication.getContext().screenHeight?MyApplication.getContext().screenWidth:MyApplication.getContext().screenHeight;
        layoutParamsFunction.width=1000;
        layoutParamsFunction.height = 200;
        layoutParamsFunction.x = 0;
        layoutParamsFunction.y = 0;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isWindowShow&&MyApplication.getContext().canWindowShow){
            showFloatingWindow();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {
        isWindowShow=true;
        cardResultView=new CardResultView(this);
        cardResultView.setOnTouchListener(new FloatingOnTouchListener(layoutParamsResult));
        windowManager.addView(cardResultView, layoutParamsResult);

        cardFunctionView=new CardFunctionView(this);
        cardFunctionView.setOnTouchListener(new FloatingOnTouchListener(layoutParamsFunction));
        windowManager.addView(cardFunctionView, layoutParamsFunction);

    }








































    BroadcastReceiver stopJipaiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isWindowShow&&windowManager!=null){
                isWindowShow=false;
                if(cardResultView!=null){
                    windowManager.removeView(cardResultView);
                }
                if(cardFunctionView!=null){
                    windowManager.removeView(cardFunctionView);
                }
            }
        }
    };


    @Override
    public void onDestroy() {
        unregisterReceiver(stopJipaiReceiver);
        super.onDestroy();
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private WindowManager.LayoutParams layoutParams;
        public FloatingOnTouchListener(WindowManager.LayoutParams layoutParams){
            this.layoutParams=layoutParams;
        }
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
