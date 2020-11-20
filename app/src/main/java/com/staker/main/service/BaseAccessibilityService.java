package com.staker.main.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.staker.main.application.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by staker
 * 2017/12/22.
 */
public class BaseAccessibilityService extends AccessibilityService {

//
//    private static final String TAG = "BaseAccessibilityService";
//    private List<String> allNameList = new ArrayList<>();
//    private int mRepeatCount;
//
//    public static boolean hasSend;
//    public static final int SEND_FAIL = 0;
//    public static final int SEND_SUCCESS = 1;
//    public static int SEND_STATUS;

    /**
     * 必须重写的方法，响应各种事件。
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        //不直接在这里操作，而是通过另外一个service去执行，保证界面流畅运行
        HandleEventService.startWithEvent(getApplicationContext(), event);
    }
//        int eventType = event.getEventType();
//        Log.e("staker","eventType="+eventType);
//        Log.e("staker","packname="+event.getPackageName());
//
//        switch (event.getPackageName().toString()){
//            case Package_WeiXin:
//                break;
//            case Package_DouYin:
//                break;
//        }
//        switch (eventType) {
//            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED: {
//
//                String currentActivity = event.getClassName().toString();
//                Log.e("staker","currentActivity="+currentActivity);
//                if (hasSend) {
//                    return;
//                }
//
//                if (currentActivity.equals(WeChatTextWrapper.WechatClass.WECHAT_CLASS_LAUNCHUI)) {
//                    handleFlow_LaunchUI();
//                } else if (currentActivity.equals(WeChatTextWrapper.WechatClass.WECHAT_CLASS_CONTACTINFOUI)) {
//                    handleFlow_ContactInfoUI();
//                } else if (currentActivity.equals(WeChatTextWrapper.WechatClass.WECHAT_CLASS_CHATUI)) {
//                    handleFlow_ChatUI();
//                }else if(currentActivity.equals(WeChatTextWrapper.WechatClass.WECHAT_TimeLine)){
//                    handTimeLineUI();
//                }
//            }
//            break;
//        }
//    }

//    private void handTimeLineUI(){
//
//    }
//
//
//    private void handleFlow_ChatUI() {
//
//        //如果微信已经处于聊天界面，需要判断当前联系人是不是需要发送的联系人
//        String curUserName = WechatUtils.findTextById(this, WeChatTextWrapper.WechatId.WECHATID_CHATUI_USERNAME_ID);
//        if (!TextUtils.isEmpty(curUserName) && curUserName.equals(WechatUtils.NAME)) {
//            if (WechatUtils.findViewByIdAndPasteContent(this, WeChatTextWrapper.WechatId.WECHATID_CHATUI_EDITTEXT_ID, WechatUtils.CONTENT)) {
//                sendContent();
//            } else {
//                //当前页面可能处于发送语音状态，需要切换成发送文本状态
//                WechatUtils.findViewIdAndClick(this, WeChatTextWrapper.WechatId.WECHATID_CHATUI_SWITCH_ID);
//
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                if (WechatUtils.findViewByIdAndPasteContent(this, WeChatTextWrapper.WechatId.WECHATID_CHATUI_EDITTEXT_ID, WechatUtils.CONTENT)) {
//                    sendContent();
//                }
//            }
//        } else {
//            //回到主界面
//            WechatUtils.findViewIdAndClick(this, WeChatTextWrapper.WechatId.WECHATID_CHATUI_BACK_ID);
//        }
//    }
//
//    private void handleFlow_ContactInfoUI() {
//        WechatUtils.findTextAndClick(this, "发消息");
//    }
//
//    private void handleFlow_LaunchUI() {
//
//
//        try {
//            //点击通讯录，跳转到通讯录页面
//            ArrayList<AccessibilityNodeInfo> list= AccessibilityUtil.findViewByEqualsText(this,"支付");
//            for (AccessibilityNodeInfo temp : list){
//                Log.e("staker","temp name="+temp.getClassName());
////                AccessibilityUtil.clickView(temp);
//            }
////            WechatUtils.findTextAndClick(this, "通讯录");
////
////            Thread.sleep(50);
////
////            //再次点击通讯录，确保通讯录列表移动到了顶部
////            WechatUtils.findTextAndClick(this, "通讯录");
////
////            Thread.sleep(200);
////
////            //遍历通讯录联系人列表，查找联系人
////            AccessibilityNodeInfo itemInfo = traversalAndFindContacts();
////            if (itemInfo != null) {
////                WechatUtils.performClick(itemInfo);
////            } else {
////                SEND_STATUS = SEND_FAIL;
////                resetAndReturnApp();
////            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 从头至尾遍历寻找联系人
//     *
//     * @return
//     */
//    private AccessibilityNodeInfo traversalAndFindContacts() {
//
//        if (allNameList != null) allNameList.clear();
//
//        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//        List<AccessibilityNodeInfo> listview = rootNode.findAccessibilityNodeInfosByViewId(WeChatTextWrapper.WechatId.WECHATID_CONTACTUI_LISTVIEW_ID);
//
//        //是否滚动到了底部
//        boolean scrollToBottom = false;
//        if (listview != null && !listview.isEmpty()) {
//            while (true) {
//                //获取当前屏幕上的联系人信息
//                List<AccessibilityNodeInfo> nameList = rootNode.findAccessibilityNodeInfosByViewId(WeChatTextWrapper.WechatId.WECHATID_CONTACTUI_NAME_ID);
//                List<AccessibilityNodeInfo> itemList = rootNode.findAccessibilityNodeInfosByViewId(WeChatTextWrapper.WechatId.WECHATID_CONTACTUI_ITEM_ID);
//
//                if (nameList != null && !nameList.isEmpty()) {
//                    for (int i = 0; i < nameList.size(); i++) {
//                        if (i == 0) {
//                            //必须在一个循环内，防止翻页的时候名字发生重复
//                            mRepeatCount = 0;
//                        }
//                        AccessibilityNodeInfo itemInfo = itemList.get(i);
//                        AccessibilityNodeInfo nodeInfo = nameList.get(i);
//                        String nickname = nodeInfo.getText().toString();
//                        Log.d(TAG, "nickname = " + nickname);
//                        if (nickname.equals(WechatUtils.NAME)) {
//                            return itemInfo;
//                        }
//                        if (!allNameList.contains(nickname)) {
//                            allNameList.add(nickname);
//                        } else if (allNameList.contains(nickname)) {
//                            Log.d(TAG, "mRepeatCount = " + mRepeatCount);
//                            if (mRepeatCount == 3) {
//                                //表示已经滑动到顶部了
//                                if (scrollToBottom) {
//                                    Log.d(TAG, "没有找到联系人");
//                                    //此次发消息操作已经完成
//                                    hasSend = true;
//                                    return null;
//                                }
//                                scrollToBottom = true;
//                            }
//                            mRepeatCount++;
//                        }
//                    }
//                }
//
//                if (!scrollToBottom) {
//                    //向下滚动
//                    listview.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                } else {
//                    return null;
//                }
//
//                //必须等待，因为需要等待滚动操作完成
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }
//
//    private void sendContent() {
//        WechatUtils.findTextAndClick(this, "发送");
//        SEND_STATUS = SEND_SUCCESS;
//        resetAndReturnApp();
//    }
//
//    private void resetAndReturnApp() {
//        hasSend = true;
//        ActivityManager activtyManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activtyManager.getRunningTasks(3);
//        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
//            if (this.getPackageName().equals(runningTaskInfo.topActivity.getPackageName())) {
//                activtyManager.moveTaskToFront(runningTaskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
//                return;
//            }
//        }
//    }


    /**
     * 当系统连接上你的服务时被调用
     */
    @Override
    protected void onServiceConnected() {
        Toast.makeText(this, "嘻嘻嘻", Toast.LENGTH_SHORT).show();
        super.onServiceConnected();
        MyApplication.getContext().setAccessibilityService(this);
    }
    /**
     * 必须重写的方法：系统要中断此service返回的响应时会调用。在整个生命周期会被调用多次。
     */
    @Override
    public void onInterrupt() {
        Toast.makeText(this, "我要死鸟", Toast.LENGTH_SHORT).show();
    }


}
