package douyin.actions;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.staker.main.application.MyApplication;
import com.staker.main.interfaces.Action;
import com.staker.main.util.AccessibilityUtil;
import com.staker.main.util.GlobalActionUtil;
import com.staker.main.util.HandlerManager;
import com.staker.main.util.Logg;
import com.staker.main.util.SleepUtil;

import java.util.List;

import douyin.logic.ShowPageUtil;

import static douyin.constant.DouYinConstants.DouYinClass.Class_ChatRoomActivity;
import static douyin.constant.DouYinConstants.DouYinClass.Class_MainActivity;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeMsg_Msg_Item;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeMsg_Recycler_Msg;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeMsg_Txt_Name;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeMsg_Txt_Start_Chat;

/**
 *  给互相关注的好友发信息
 */
public class SendMessageAction implements Action {
    private final  String TAG = getClass().getSimpleName();
    private static SendMessageAction mInstance;
    private AccessibilityService service;
    private AccessibilityUtil accessibilityUtil;

    private AccessibilityNodeInfo nodeRecycler;
    private boolean isRunning=false;//记录当前这个任务是否正在执行，如果正在执行，则新进来的任务不再执行
    private SendMessageAction() {
        service= MyApplication.getContext().getAccessibilityService();
        accessibilityUtil= AccessibilityUtil.getInstance(service);
    }

    public static SendMessageAction getInstance() {
        if (mInstance == null) {
            synchronized (SendMessageAction.class) {
                if (mInstance == null) {
                    mInstance = new SendMessageAction();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void run(AccessibilityEvent event) {
        Log.e(TAG,"进入任务-----------------发私信");
        String className = event.getClassName().toString();
        if(Class_MainActivity.equals(className)){
            startMessage();
        }else if(Class_ChatRoomActivity.equals(className)){

        }
    }

    private void startMessage(){
        if(isRunning){
            Logg.e("当前任务正在执行 发私信 任务，返回");
            return;
        }
        isRunning=true;
        AccessibilityNodeInfo rootNodeInfo = service.getRootInActiveWindow();
        if (rootNodeInfo == null) {
            isRunning=false;
            return;
        }
        AccessibilityNodeInfo nodeStartChat=accessibilityUtil.findViewById(ID_HomeMsg_Txt_Start_Chat);
        accessibilityUtil.logNodeInfo(nodeStartChat);
        if(nodeStartChat==null){
            ShowPageUtil.showHomeMessage(service);
        }

        initRecyclerView();
        if(nodeRecycler==null){
            isRunning=false;
            return;
        }
        HandlerManager.getInstance().postOnSubThread(runnableMain);
    }


    private Runnable runnableMain =new Runnable() {
        @Override
        public void run() {
            synchronized (runnableMain){
                while (isRunning){
                    startClickHead();
                }
            }
        }
    };

    private void startClickHead(){
        List<AccessibilityNodeInfo> listItem=accessibilityUtil.findViewListById(ID_HomeMsg_Msg_Item);
        boolean canScroll = true;
        while (canScroll&&listItem != null && listItem.size() > 0){
            int count=listItem.size();
            for (int j = 0; j < count; j++) {
                AccessibilityNodeInfo nodeItem=listItem.get(j);
                AccessibilityNodeInfo nodeTxtName=accessibilityUtil.findViewById(nodeItem,ID_HomeMsg_Txt_Name);
                if(nodeTxtName==null){
                    continue;
                }
                Logg.w("-------------获取的名字="+nodeTxtName.getText().toString());
                accessibilityUtil.clickViewByNodeInfo(nodeItem);


            }

            canScroll = GlobalActionUtil.performScrollForward(nodeRecycler);
            SleepUtil.sleepTime(500);
            listItem=accessibilityUtil.findViewListById(ID_HomeMsg_Msg_Item);
        }


        isRunning=false;
    }



    private void initRecyclerView(){
        nodeRecycler=accessibilityUtil.findViewById(ID_HomeMsg_Recycler_Msg);
    }
}
