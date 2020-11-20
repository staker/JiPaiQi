package douyin;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.staker.main.application.MyApplication;
import com.staker.main.util.AccessibilityUtil;

import douyin.actions.SendMessageAction;
import douyin.actions.FollowCommenterAction;
import douyin.actions.SeeHomeAction;

import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Send_Message;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Follow_Commenter;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_See_Home;

public class DouYinEventHandle {
    private final  String TAG = getClass().getSimpleName();
    private static DouYinEventHandle instance;
    private AccessibilityService service;



    private DouYinEventHandle(AccessibilityService service) {
        this.service=service;
    }
    public static DouYinEventHandle getInstance(AccessibilityService service) {
        if (instance == null) {
            synchronized (AccessibilityUtil.class) {
                if (instance == null) {
                    instance = new DouYinEventHandle(service);
                }
            }
        }
        return instance;
    }
    public void startHandleEvent(AccessibilityEvent event){
        int eventType = event.getEventType();
        String className = event.getClassName().toString();
        Log.e(TAG,"抖音 className="+className);
        Log.e(TAG,"抖音 missionType="+MyApplication.getContext().missionType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                switch (MyApplication.getContext().missionType){
                    case Mission_See_Home:
                        SeeHomeAction.getInstance().run(event);
                        break;
                    case Mission_Send_Message:
                        SendMessageAction.getInstance().run(event);
                        break;
                    case Mission_Follow_Commenter:
                        FollowCommenterAction.getInstance().run(event);
                        break;
                }
             break;
        }
    }


}
