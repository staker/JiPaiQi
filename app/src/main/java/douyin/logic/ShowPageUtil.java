package douyin.logic;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import com.staker.main.util.AccessibilityUtil;
import com.staker.main.util.Logg;
import com.staker.main.util.SleepUtil;

import java.util.List;

import static douyin.constant.DouYinConstants.DouYinId.ID_HomeMe_Txt_Account;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeMsg_Txt_Start_Chat;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeHome_Layout_Zan;
import static douyin.constant.DouYinConstants.DouYinId.ID_Home_Layout_One;

/**
 * 这是具体跳转到哪个页面的工具类
 */
public class ShowPageUtil {



    /**
     * 进入首页的首页导航
     * @param service
     */
    public static void showHomeHome(AccessibilityService service){

        AccessibilityUtil accessibilityUtil=AccessibilityUtil.getInstance(service);
        AccessibilityNodeInfo nodeZan=accessibilityUtil.findViewById(ID_HomeHome_Layout_Zan);
        if(nodeZan!=null){
            Logg.e("----当前已经在导航 首页");
            return;
        }
        SleepUtil.sleepTime(1000);
        List<AccessibilityNodeInfo> listOne=accessibilityUtil.findViewListById(ID_Home_Layout_One);
        AccessibilityNodeInfo nodeMsg=null;
        if(listOne!=null&&listOne.size()>4){
            nodeMsg=listOne.get(0);
        }
        if(nodeMsg!=null){
            Logg.e("点击导航 首页");
            AccessibilityNodeInfo nodeMsgLayout=nodeMsg.getParent();
            accessibilityUtil.clickViewByNodeInfo(nodeMsgLayout);
        }
    }


    /**
     * 进入首页的消息导航
     * @param service
     */
    public static void showHomeMessage(AccessibilityService service){
        AccessibilityUtil accessibilityUtil=AccessibilityUtil.getInstance(service);
        AccessibilityNodeInfo nodeStartChat=accessibilityUtil.findViewById(ID_HomeMsg_Txt_Start_Chat);
        if(nodeStartChat!=null){
            Logg.e("----当前已经在导航 消息");
            return;
        }
        SleepUtil.sleepTime(1000);
        List<AccessibilityNodeInfo> listOne=accessibilityUtil.findViewListById(ID_Home_Layout_One);
        AccessibilityNodeInfo nodeMsg=null;
        if(listOne!=null&&listOne.size()>4){
            nodeMsg=listOne.get(3);
        }
        if(nodeMsg!=null){
            Logg.e("点击首页导航 消息");
            AccessibilityNodeInfo nodeMsgLayout=nodeMsg.getParent();
            accessibilityUtil.clickViewByNodeInfo(nodeMsgLayout);
        }
    }


    /**
     * 进入首页我 导航
     * @param service
     */
    public static void showHomeMe(AccessibilityService service){
        AccessibilityUtil accessibilityUtil=AccessibilityUtil.getInstance(service);
        AccessibilityNodeInfo nodeTabs=accessibilityUtil.findViewById(ID_HomeMe_Txt_Account);
        accessibilityUtil.logListDesc(ID_HomeMe_Txt_Account);
        if(nodeTabs!=null){
            Logg.e("----当前已经在导航 我");
            return;
        }
        SleepUtil.sleepTime(1000);
        List<AccessibilityNodeInfo> listOne=accessibilityUtil.findViewListById(ID_Home_Layout_One);
        AccessibilityNodeInfo nodeMsg=null;
        if(listOne!=null&&listOne.size()>4){
            nodeMsg=listOne.get(4);
        }
        if(nodeMsg!=null){
            Logg.e("点击首页导航--------------我");
            AccessibilityNodeInfo nodeMsgLayout=nodeMsg.getParent();
            accessibilityUtil.clickViewByNodeInfo(nodeMsgLayout);
        }
    }

}
