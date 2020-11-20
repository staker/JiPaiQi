package douyin.actions;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.staker.main.application.Configuration;
import com.staker.main.application.MyApplication;
import com.staker.main.interfaces.Action;
import com.staker.main.util.AccessibilityUtil;
import com.staker.main.util.GlobalActionUtil;
import com.staker.main.util.HandlerManager;
import com.staker.main.util.Logg;
import com.staker.main.util.SleepUtil;
import com.staker.main.view.util.ToastUtil;

import java.util.List;
import java.util.Random;

import douyin.function.ClickZanFunction;
import douyin.logic.FansCountUtil;
import douyin.logic.LetterUtil;
import douyin.logic.TimeUtil;

import static douyin.constant.DouYinConstants.DouYinClass.Class_ChatRoomActivity;
import static douyin.constant.DouYinConstants.DouYinClass.Class_MainActivity;
import static douyin.constant.DouYinConstants.DouYinClass.Class_UserProfileActivity;
import static douyin.constant.DouYinConstants.DouYinId.ID_ChatActivity_Edt_Content;
import static douyin.constant.DouYinConstants.DouYinId.ID_ChatActivity_Img_Send;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeComment_Item;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeComment_Recycler_Comment;
import static douyin.constant.DouYinConstants.DouYinId.ID_Home_Comment_Img_Zan;
import static douyin.constant.DouYinConstants.DouYinId.ID_Home_Comment_Txt_Comment_Content;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeComment_Txt_Name;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeHome_Img_Comment;
import static douyin.constant.DouYinConstants.DouYinId.ID_HomeHome_ViewPager;
import static douyin.constant.DouYinConstants.DouYinId.ID_UserProfile_Btn_Follow;
import static douyin.constant.DouYinConstants.DouYinId.ID_UserProfile_Img_Chat;
import static douyin.constant.DouYinConstants.DouYinId.ID_UserProfile_Txt_Fans;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Follow_Commenter;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Nothing;

/**
 *  随机浏览视频，并随机点赞
 */
public class FollowCommenterAction implements Action {
    private final  String TAG = getClass().getSimpleName();
    private static FollowCommenterAction mInstance;
    private AccessibilityService service;
    private AccessibilityUtil accessibilityUtil;

    private AccessibilityNodeInfo nodeViewPager;



    private static int MaxFollowCount;
    private static int MaxSeeTime;
    private static int DelayFollowTime;
    private static int VideoZanProbability;
    private static int HotCommentZanProbability;
    private static int NormalCommentZanProbability;
    private static boolean isChatOpen;



    private boolean isRunning=false;//记录当前这个任务是否正在执行，如果正在执行，则新进来的任务不再执行
    private Random random=new Random();
    private int watchCount=0;//记录刷视频的个数
    private int followCount;//记录关注用户的个数
    private boolean isTalk;
//    boolean isLock=false;

    private FollowCommenterAction() {
        service= MyApplication.getContext().getAccessibilityService();
        accessibilityUtil= AccessibilityUtil.getInstance(service);
    }

    public static FollowCommenterAction getInstance() {
        if (mInstance == null) {
            synchronized (FollowCommenterAction.class) {
                if (mInstance == null) {
                    mInstance = new FollowCommenterAction();
                }
            }
        }
        return mInstance;
    }
    private void initSaveData(){
        MaxFollowCount=Configuration.getInstance().getMaxFollowCount();
        MaxSeeTime=Configuration.getInstance().getMaxWatchTime();
        DelayFollowTime=Configuration.getInstance().getDelayFollowTime();
        VideoZanProbability =Configuration.getInstance().getVideoZanProbability();
        HotCommentZanProbability=Configuration.getInstance().getHotCommentZanProbability();
        NormalCommentZanProbability=Configuration.getInstance().getNormalCommentZanProbability();
        isChatOpen=Configuration.getInstance().isChatOpen();
    }
    @Override
    public void run(AccessibilityEvent event) {
        Log.e(TAG,"进入任务=Watch_Follow");
        String className = event.getClassName().toString();
        initSaveData();
        if(Class_MainActivity.equals(className)){
            showMainActivity();
        }else if(Class_UserProfileActivity.equals(className)){
            Log.e(TAG,"进入用户详情界面");
            showUserProfileActivity();
        }else if(Class_ChatRoomActivity.equals(className)){
            Logg.e(" 进入聊天界面");
            showChatRoomActivity();
        }
    }
    private void showMainActivity(){
        SleepUtil.notifyTime(runnable);
        startWatch();
    }

    private void showUserProfileActivity(){
        if(isTalk){//进入过聊天界面，这里就直接返回
            isTalk=false;
            GlobalActionUtil.goBack(service);
            return;
        }
        followUser();
    }
    private void showChatRoomActivity(){
        isTalk=true;
        SleepUtil.sleepTime(1000);
        AccessibilityNodeInfo nodeEdit=accessibilityUtil.findViewById(ID_ChatActivity_Edt_Content);
        accessibilityUtil.clickViewByNodeInfo(nodeEdit);
        SleepUtil.sleepTime(1500);

        String letter01=Configuration.getInstance().getLetterContent01();
        letter01= LetterUtil.getRandomLetter(letter01);
        accessibilityUtil.inputText(nodeEdit,letter01);
        SleepUtil.sleepTime(2000);
        accessibilityUtil.clickViewByID(ID_ChatActivity_Img_Send);



        String letter02=Configuration.getInstance().getLetterContent02();
        letter02= LetterUtil.getRandomLetter(letter02);
        if(TextUtils.isEmpty(letter02)){
            GlobalActionUtil.goBack(service);
        }
        accessibilityUtil.inputText(nodeEdit,letter02);
        SleepUtil.sleepTime(2000);
        accessibilityUtil.clickViewByID(ID_ChatActivity_Img_Send);



        String letter03=Configuration.getInstance().getLetterContent03();
        letter03= LetterUtil.getRandomLetter(letter03);
        if(TextUtils.isEmpty(letter03)){
            GlobalActionUtil.goBack(service);
        }
        accessibilityUtil.inputText(nodeEdit,letter03);
        SleepUtil.sleepTime(2000);
        accessibilityUtil.clickViewByID(ID_ChatActivity_Img_Send);

        GlobalActionUtil.goBack(service);
        SleepUtil.sleepTime(1000);
        GlobalActionUtil.goBack(service);

    }
    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            synchronized (runnable){
                watchCount=0;
                while (isRunning&&MyApplication.getContext().missionType==Mission_Follow_Commenter){
                    step01Watch();
                    step02ClickHead();
                }
            }
        }
    };






    private  void startWatch(){
        if(isRunning){
            Logg.e("当前任务正在执行，返回");
            return;
        }
        isRunning=true;
        AccessibilityNodeInfo rootNodeInfo = service.getRootInActiveWindow();
        if (rootNodeInfo == null) {
            isRunning=false;
            return;
        }
        initViewPager();
        if(nodeViewPager==null){
            isRunning=false;
            return;
        }
        HandlerManager.getInstance().postOnSubThread(runnable);
    }
    private void followUser(){
        if(followCount>MaxFollowCount){//判断关注的数量
            MyApplication.getContext().missionType=Mission_Nothing;
            isRunning=false;
            return;
        }
        SleepUtil.sleepTime(2000+DelayFollowTime*1000);


        //这里还需要判断性别和年龄是否符合

        long fansCount=0;
        AccessibilityNodeInfo nodeFans=accessibilityUtil.findViewById(ID_UserProfile_Txt_Fans);
        if(nodeFans==null){
            fansCount=0;
        }else {
            fansCount= FansCountUtil.getFansCount(nodeFans.getText().toString());
        }
        if(fansCount<= Configuration.getInstance().getLimitFans()){
            AccessibilityNodeInfo nodeFollow=accessibilityUtil.findViewById(ID_UserProfile_Btn_Follow);
            accessibilityUtil.logListDesc(ID_UserProfile_Btn_Follow);
            String follow=nodeFollow==null?null:nodeFollow.getText().toString();
            if("关注".equals(follow)) {
                Logg.e("----点击关注");
                followCount++;
                accessibilityUtil.clickViewByNodeInfo(nodeFollow);
            }else {
                Logg.w("----已经关注----");
            }
        }else {
            Logg.w("粉丝数量 超过限制");
        }
        intoChat();
    }


    private void intoChat(){
        if(isChatOpen){
            isTalk=true;
            SleepUtil.sleepTime(1000);
            accessibilityUtil.clickViewByID(ID_UserProfile_Img_Chat);
        }else {
            GlobalActionUtil.goBack(service);
        }
    }

    /**
     * 看视频并进入评论界面
     */
    private  void step01Watch(){
            watchCount++;
             ToastUtil.showToast("观看第"+watchCount+"个视频");
             int zanResult=ClickZanFunction.clickVideoZan(accessibilityUtil, VideoZanProbability);
             if(zanResult==-1){
                 isRunning=false;
                 return;
             }
            int watchTime=random.nextInt(MaxSeeTime)+1;//观看时间的时间
            SleepUtil.sleepTime(watchTime*1000);

            accessibilityUtil.clickViewByID(ID_HomeHome_Img_Comment);//点击评论按钮
            SleepUtil.sleepTime(1000);
    }



    /**
     * 在评论界面，找用户进入其主页
     */
    private void step02ClickHead(){
        AccessibilityNodeInfo nodeRecycler=accessibilityUtil.findViewById(ID_HomeComment_Recycler_Comment);
        List<AccessibilityNodeInfo>  listItem=accessibilityUtil.findViewListById(ID_HomeComment_Item);
        if(nodeRecycler==null){
            return;
        }
        boolean canScroll = true;
        int i=0;
        boolean isOver=false;
        while (!isOver&&canScroll&&listItem != null && listItem.size() > 0){
            int count=listItem.size();
            for (int j = 0; j < count; j++) {
                if(j==0||j==count-1){
                    //去掉最后一条和第一条，因为通过坐标点击。第一个和最后一个可能只显示了一部分
                    continue;
                }
                AccessibilityNodeInfo nodeItem=listItem.get(j);
                i++;
                AccessibilityNodeInfo nodeTxtName=accessibilityUtil.findViewById(nodeItem, ID_HomeComment_Txt_Name);
                AccessibilityNodeInfo nodeTxtContent=accessibilityUtil.findViewById(nodeItem,ID_Home_Comment_Txt_Comment_Content);
                AccessibilityNodeInfo nodeZan=accessibilityUtil.findViewById(nodeItem,ID_Home_Comment_Img_Zan);

                if(nodeTxtName==null||nodeTxtContent==null){
                    i=0;
                    Logg.w("-------------热评的回复");
                    continue;
                }

                String commentContent=nodeTxtContent.getText().toString();
                if(TimeUtil.isCommentMinuteAgo(commentContent)&&i>3){
                    Logg.e(i+"  commentContent="+commentContent) ;
                    Logg.e(i+"  点击头像==============="+nodeTxtName.getText()) ;
                    ClickZanFunction.clickNormalCommentZan(nodeZan,1);
                    accessibilityUtil.forceClick(nodeTxtName);
                    SleepUtil.waitTime(runnable);
                }else if(i>10){
                    Logg.e("---超过10条时间都不对  退出");
                    isOver=true;
                    break;
                }else if(i<=3){
                    ClickZanFunction.clickHotCommentZan(nodeZan,HotCommentZanProbability);
                    Logg.e("-------------热评");
                }else {
                    Logg.e("-------------过滤");
                }
            }

            Logg.e("-------------繼續執行");
            canScroll = GlobalActionUtil.performScrollForward(nodeRecycler);
            SleepUtil.sleepTime(500);
            listItem=accessibilityUtil.findViewListById(ID_HomeComment_Item);
        }
        GlobalActionUtil.goBack(service);
        GlobalActionUtil.performScrollForward(nodeViewPager);
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
