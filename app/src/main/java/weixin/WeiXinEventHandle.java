package weixin;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.staker.main.util.AccessibilityUtil;

import weixin.actions.TimelineZanAction;

public class WeiXinEventHandle {
     private final  String TAG = getClass().getSimpleName();
    private static weixin.WeiXinEventHandle instance;
    private AccessibilityService service;
    private  WeiXinEventHandle(AccessibilityService service) {
        this.service=service;
    }
    public static weixin.WeiXinEventHandle getInstance(AccessibilityService service) {
        if (instance == null) {
            synchronized (AccessibilityUtil.class) {
                if (instance == null) {
                    instance = new weixin.WeiXinEventHandle(service);
                }
            }
        }
        return instance;
    }
    public void startHandleEvent(AccessibilityEvent event){
        int eventType = event.getEventType();
        String className = event.getClassName().toString();
        Log.e(TAG,"className="+className);
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                new TimelineZanAction().run(event);
             break;
        }
    }


}
