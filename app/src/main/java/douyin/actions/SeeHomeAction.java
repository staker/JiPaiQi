package douyin.actions;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.staker.main.application.Configuration;
import com.staker.main.application.MyApplication;
import com.staker.main.interfaces.Action;
import com.staker.main.util.AccessibilityUtil;
import com.staker.main.util.GlobalActionUtil;
import com.staker.main.util.Logg;
import com.staker.main.util.SleepUtil;
import com.staker.main.view.util.ToastUtil;

import java.util.List;
import java.util.Random;

import douyin.constant.DouYinConstants;
import douyin.function.ClickZanFunction;
import douyin.logic.LetterUtil;
import douyin.logic.RandomUtil;
import douyin.logic.ShowPageUtil;

import static douyin.constant.DouYinConstants.DouYinId.ID_Home_Comment_Edt_Comment;
import static douyin.constant.DouYinConstants.DouYinId.ID_Home_Comment_Img_Send_Comment;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeHome_Img_Comment;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeHome_Layout_Zan;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeHome_ViewPager;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Nothing;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_See_Home;

/**
 *  观看首页视频，并随机进行点赞，评论
 */
public class SeeHomeAction implements Action {
    private final  String TAG = getClass().getSimpleName();
    private static SeeHomeAction mInstance;
    private AccessibilityService service;
    private AccessibilityUtil accessibilityUtil;
    private AccessibilityNodeInfo nodeViewPager;


    static String CommentContent;
    static int MaxFollowCount;
    static int MaxSeeTime;
    static int MaxWatchCount;
    static int ZanProbability;
    static boolean IsComment;//是否进行评论


    private boolean isRunning=false;//记录当前这个任务是否正在执行，如果正在执行，则新进来的任务不再执行
    Random random=new Random();
    int watchCount=0;//记录刷视频的个数

    private SeeHomeAction() {
        service= MyApplication.getContext().getAccessibilityService();
        accessibilityUtil= AccessibilityUtil.getInstance(service);

    }

    public static SeeHomeAction getInstance() {
        if (mInstance == null) {
            synchronized (SeeHomeAction.class) {
                if (mInstance == null) {
                    mInstance = new SeeHomeAction();
                }
            }
        }
        return mInstance;
    }

    private void initSaveData(){
        CommentContent=Configuration.getInstance().getCommentContent();
        CommentContent= LetterUtil.getRandomLetter(CommentContent);
        MaxFollowCount=Configuration.getInstance().getMaxFollowCount();
        MaxSeeTime=Configuration.getInstance().getMaxWatchTime();
        MaxWatchCount=Configuration.getInstance().getMaxSeeCount();
        ZanProbability=Configuration.getInstance().getVideoZanProbability();
        IsComment=Configuration.getInstance().isCommentOpen();
    }
    @Override
    public void run(AccessibilityEvent event) {
        String className = event.getClassName().toString();
        initSaveData();
        if(DouYinConstants.DouYinClass.Class_MainActivity.equals(className)){
            ShowPageUtil.showHomeHome(service);//先切换到指定界面
            startSee();
        }

    }
    private  void startSee(){
        if(isRunning){
            Logg.e("当前任务正在执行-----------------返回");
            return;
        }
        isRunning=true;
        initViewPager();
        if(nodeViewPager==null){
            isRunning=false;
            return;
        }
        watchCount=0;
        while (isRunning&&MyApplication.getContext().missionType==Mission_See_Home){
            seeVideoStep01();
        }
    }
    private synchronized void seeVideoStep01(){
        if(watchCount>MaxWatchCount) {
            isRunning=false;
            MyApplication.getContext().missionType=Mission_Nothing;
            Logg.w("----------------超过了观看 数目");
            return;
        }
        watchCount++;
        ToastUtil.showToast("观看第"+watchCount+"个视频");
        SleepUtil.sleepTime(1000);
        int zanResult=ClickZanFunction.clickVideoZan(accessibilityUtil,ZanProbability);
        if(zanResult==1){
            if(IsComment&&RandomUtil.isRandomTrue(2)){
                commentStep02();
            }
        }else if(zanResult==-1){
            isRunning=false;
        }

        int watchTime=Math.abs(random.nextInt(MaxSeeTime-1))+2;//观看时间的时间
        SleepUtil.sleepTime(watchTime*1000);

        GlobalActionUtil.performScrollForward(nodeViewPager);//往下滑动一页
    }



    /**
     * 点击评论按钮就如评论
     */
    private void commentStep02(){
            //点击弹出评论界面
            accessibilityUtil.clickViewByID(ID_HomeHome_Img_Comment);
            SleepUtil.sleepTime(2000);


            //给评论输入框设置内容，并点击它
            AccessibilityNodeInfo nodeEdtComment=accessibilityUtil.findViewById(ID_Home_Comment_Edt_Comment);
            accessibilityUtil.inputText(nodeEdtComment,CommentContent+" "+ RandomUtil.getRandomEmoj());
            SleepUtil.sleepTime(500);
            accessibilityUtil.clickViewByNodeInfo(nodeEdtComment);
            SleepUtil.sleepTime(1000);


//
            //点击发送按钮
            accessibilityUtil.clickViewByID(ID_Home_Comment_Img_Send_Comment);
            ToastUtil.showToast("评论成功");
            SleepUtil.sleepTime(1500);

            //返回视频主界面
            GlobalActionUtil.goBack(service);
            SleepUtil.sleepTime(1000);

            //下滑到下一个视频
            boolean canScroll=GlobalActionUtil.performScrollForward(nodeViewPager);
            if(!canScroll){
                isRunning=false;
            }
    }















    private void initViewPager(){
        nodeViewPager=null;
        List<AccessibilityNodeInfo> list =accessibilityUtil.findViewListById(ID_HomeHome_ViewPager);
        if (list == null) {
            return;
        }
        for (AccessibilityNodeInfo temp : list){
            if("视频".equals(temp.getContentDescription())){
                nodeViewPager=temp;
                break;
            }
        }
    }
}
