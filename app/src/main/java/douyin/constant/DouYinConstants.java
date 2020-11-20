package douyin.constant;

public class DouYinConstants {
    public static final String DouYin_PackageName="com.ss.android.ugc.aweme";//抖音的包名

    public interface DouYinMissionType{
        public static final int Mission_Nothing=0;//当前停止了脚本
        public static final int Mission_See_Home =10000;//看首页的视频，并随机点赞评论等
        public static final int Mission_Follow_Commenter =10001;//刷视频并关注评论里面的人
        public static final int Mission_Send_Message =10002;//给互相 关注的人发私信
    }




    public interface DouYinClass{
        public static final String Class_MainActivity = "com.ss.android.ugc.aweme.main.MainActivity";//底部的首页，关注，消息，我，都属于这个界面，点击评论之后依然在这个界面
        public static final String Class_UserProfileActivity = "com.ss.android.ugc.aweme.profile.ui.UserProfileActivity";//用户主页界面
        public static final String Class_ChatRoomActivity = "com.ss.android.ugc.aweme.im.sdk.chat.ChatRoomActivity";//和用户聊天的界面

    }


    public interface DouYinId{

        /**
         * 底部导航
         */
        public static final String ID_Home_Layout_One = DouYin_PackageName+":id/"+"d8w";//底部导航其中一个容器 RelativeLayout

        /**
         * 导航首页
         */

        public static final String ID_HomeHome_ViewPager = DouYin_PackageName+":id/"+"eb2";//视频主滑动控件
        public static final String ID_HomeHome_Layout_Zan = DouYin_PackageName+":id/"+"a74";//首页点赞按钮 ( 描述里面有选中和未选中状态)
        public static final String ID_HomeHome_Img_Comment = DouYin_PackageName+":id/"+"x_";//评论按钮


        /**
         * 导航首页弹出评论界面
         */
        public static final String ID_HomeComment_Recycler_Comment = DouYin_PackageName+":id/"+"ckn";//评论列表
        public static final String ID_HomeComment_Item = DouYin_PackageName+":id/"+"zy";//单个评论的item
        public static final String ID_HomeComment_Txt_Name = DouYin_PackageName+":id/"+"title";//用户名
        public static final String ID_Home_Comment_Txt_Comment_Content = DouYin_PackageName+":id/"+"a0c";//评论的内容，里面包含了时间
        public static final String ID_Home_Comment_Edt_Comment= DouYin_PackageName+":id/"+"wn";//评论输入框
        public static final String ID_Home_Comment_Img_Send_Comment= DouYin_PackageName+":id/"+"x3";//发送评论
        public static final String ID_Home_Comment_Img_Zan= DouYin_PackageName+":id/"+"b55";//点赞按钮


        /**
         * 用户详情界面
         */
        public static final String ID_UserProfile_Btn_Follow = DouYin_PackageName+":id/"+"ce1";//关注按钮
        public static final String ID_UserProfile_Txt_Fans= DouYin_PackageName+":id/"+"akf";//粉丝数量
        public static final String ID_UserProfile_Img_Chat= DouYin_PackageName+":id/"+"cws";//聊天按钮

        /**
         * 聊天界面
         */
        public static final String ID_ChatActivity_Edt_Content = DouYin_PackageName+":id/"+"bui";//聊天内容
        public static final String ID_ChatActivity_Img_Send = DouYin_PackageName+":id/"+"cwh";//发送按钮

        /**
         * 导航 消息
         */
        public static final String ID_HomeMsg_Recycler_Msg = DouYin_PackageName+":id/"+"";//消息列表
        public static final String ID_HomeMsg_Msg_Item = DouYin_PackageName+":id/"+"";//消息列表单个item
        public static final String ID_HomeMsg_Txt_Name = DouYin_PackageName+":id/"+"";//用户名字
        public static final String ID_HomeMsg_Txt_Start_Chat = DouYin_PackageName+":id/"+"daa";//右上角发起聊天按钮


        /**
         * 导航 我
         */
        public static final String ID_HomeMe_Layout_Tabs = DouYin_PackageName+":id/"+"d8t";//作品，收藏，喜欢对应的layout
        public static final String ID_HomeMe_Txt_Account = DouYin_PackageName+":id/"+"e60";//抖音账号
    }


}
