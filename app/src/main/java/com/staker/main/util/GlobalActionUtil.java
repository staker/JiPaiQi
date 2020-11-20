package com.staker.main.util;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.staker.main.service.BaseAccessibilityService;

import java.util.List;

public class GlobalActionUtil {

    /**
     * 返回
     */
    public static void  goBack(AccessibilityService service){
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }
    /**
     * 返回首页
     */
    public static void  goHome(AccessibilityService service){
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }
    /**
     * 拉出通知栏
     */
    public static void  showNotifications(AccessibilityService service){
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }
    /**
     * 显示最近任务
     */
    public static void  goRecentsApp(AccessibilityService service){
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
    }






    /**
     * move 事件
     * @param nodeInfo
     */
    @TargetApi(23)
    public static void performMoveDown(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        nodeInfo.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN.getId());
    }


    /**
     * 向下滚动 直接滑动一页
     * @param nodeInfo   必须ClassName为android.widget.ListView 的控件调用此方法才有效，否则无效
     * @return 滑动结果是否成功
     */
    public static boolean performScrollForward(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }

    /**
     * 向上滚动  直接滑动一页
     * @param nodeInfo   必须ClassName为android.widget.ListView 的控件调用此方法才有效，否则无效
     * @return 滑动结果是否成功
     */
    public static boolean performScrollBackward(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }


































    /**
     * 打开辅助授权界面
     * @param context
     */
    public static void goAccess(Context context){
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch (Exception e){}
    }

    /**
     * Check当前辅助服务是否启用
     *
     * @param packageName   需要检查的服务所在的包名包名
     * @param ServiceClass  需要检查的服务的类
     * @return 是否启用
     */
    public static boolean checkAccessibilityEnabled(Context context,String packageName,Class<?> ServiceClass) {
        String serviceName=packageName+"/"+ ServiceClass.getName();
        Log.e("staker","serviceName="+serviceName);
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查当前app的BaseAccessibilityService 对应发服务是否开启
     * @param context
     * @return
     */
    public static boolean checkCurrentServiceEnabled(Context context){
        return checkAccessibilityEnabled(context,context.getPackageName(),BaseAccessibilityService.class);
    }

}
