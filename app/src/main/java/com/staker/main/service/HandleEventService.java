package com.staker.main.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.accessibility.AccessibilityEvent;

import com.staker.main.application.MyApplication;

import douyin.DouYinEventHandle;
import weixin.WeiXinEventHandle;

import static com.staker.main.constant.Constants.PackageName.Package_DouYin;
import static com.staker.main.constant.Constants.PackageName.Package_WeiXin;

public class HandleEventService extends IntentService {

    public HandleEventService() {
        super("HandleEventService");
    }

    public static void startWithEvent(Context context, AccessibilityEvent event) {
        Intent intent = new Intent(context, HandleEventService.class);
        intent.putExtra("extra_event", event);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            AccessibilityEvent event = intent.getParcelableExtra("extra_event");
            if (event != null) {
            switch (event.getPackageName().toString()){
                case Package_WeiXin:
                    WeiXinEventHandle.getInstance(MyApplication.getContext().getAccessibilityService()).startHandleEvent(event);
                    break;
                case Package_DouYin:
                    DouYinEventHandle.getInstance(MyApplication.getContext().getAccessibilityService()).startHandleEvent(event);
                    break;
            }

            }
        }
    }
}
