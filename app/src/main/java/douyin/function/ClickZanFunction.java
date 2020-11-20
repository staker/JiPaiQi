package douyin.function;

import android.view.accessibility.AccessibilityNodeInfo;

import com.staker.main.application.Configuration;
import com.staker.main.application.MyApplication;
import com.staker.main.util.AccessibilityUtil;
import com.staker.main.util.Logg;
import com.staker.main.util.SleepUtil;
import com.staker.main.view.util.ToastUtil;

import douyin.logic.RandomUtil;

import static douyin.constant.DouYinConstants.DouYinId.ID_HomeHome_Layout_Zan;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Nothing;

public class ClickZanFunction {

    /**
     *
     * @param accessibilityUtil
     * @param zanProbability
     * @return  返回1代表点赞成功  0不成功  -1  不在这个界面了
     */
    public static  int  clickVideoZan(AccessibilityUtil accessibilityUtil, int zanProbability){
        AccessibilityNodeInfo nodeZan=accessibilityUtil.findViewById(ID_HomeHome_Layout_Zan);
        if(nodeZan==null){
            //说明已经不在视频首页了，那么直接退出
            Logg.w("----------------点赞按钮  找不到");
            return -1;
        }

        boolean isZan= RandomUtil.isRandomTrue(zanProbability);
        if(isZan){
            ToastUtil.showToast("点赞成功");
            accessibilityUtil.clickViewByNodeInfo(nodeZan);
            SleepUtil.sleepTime(1000);
            return 1;
        }
        return 0;
    }


    /**
     * 点赞热门评论
     * @param zanProbability  点赞概率
     * @return
     */
    public static  int  clickHotCommentZan(AccessibilityNodeInfo nodeInfo, int zanProbability){
        Logg.e("点赞热门评论");
        if(nodeInfo==null){
            return -1;
        }
        boolean isZan=RandomUtil.isRandomTrue(zanProbability);
        if(isZan){
            Logg.e("   执行点赞热门评论");
            AccessibilityUtil.getInstance(null).forceClick(nodeInfo);
            SleepUtil.sleepTime(1000);
            return 1;
        }
        return 0;
    }



    /**
     * 点赞普通评论
     * @param zanProbability  点赞概率
     * @return
     */
    public static  int  clickNormalCommentZan(AccessibilityNodeInfo nodeInfo, int zanProbability){
        Logg.e("点赞  普通评论");
        if(nodeInfo==null){
            return -1;
        }
        boolean isZan=RandomUtil.isRandomTrue(zanProbability);
        if(isZan){
            Logg.e("   执行点赞普通评论");
            AccessibilityUtil.getInstance(null).forceClick(nodeInfo);
            SleepUtil.sleepTime(500);
            return 1;
        }
        return 0;
    }






}




