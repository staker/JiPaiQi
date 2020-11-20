package com.staker.main.application;

import android.accessibilityservice.AccessibilityService;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import douyin.constant.DouYinConstants;
import libs.Avos.AvosUtil;

/**
 *
 */
public class MyApplication extends Application {
    private static MyApplication application;
    private AccessibilityService service;
    public int screenWidth;
    public int screenHeight;
    public boolean canWindowShow=false;
    public int missionType= DouYinConstants.DouYinMissionType.Mission_Nothing;



    public Bitmap bitmap;
    public static MyApplication getContext() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
        initScreenSize();
    }
    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
    }
    private void init() {
        AvosUtil.initialize(this);

    }
    public void setAccessibilityService(AccessibilityService service){
        this.service=service;
    }
    public AccessibilityService getAccessibilityService(){
        return service;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
