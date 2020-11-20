package weixin;

public class WeiXinConstant {

    public interface WeiXinClass{
        public static final String Class_Home = "com.tencent.mm.ui.LauncherUI";//微信首页 底部四个界面都属于首页
        public static final String Class_Chatting = "com.tencent.mm.ui.chatting.ChattingUI";//微信聊天页面
        public static final String Class_TimeLine = "com.tencent.mm.plugin.sns.ui.SnsTimeLineUI";//朋友圈界面

    }


    public interface WeiXinId{
        /**
         * 通讯录界面
         */
        public static final String ID_Contact_Listview = "com.tencent.mm:id/ih";
        public static final String ID_Contact_Item = "com.tencent.mm:id/iy";
        public static final String ID_Contact_Name = "com.tencent.mm:id/j1";

        /**
         * 聊天界面
         */
        public static final String WECHATID_CHATUI_EDITTEXT_ID = "com.tencent.mm:id/a_z";
        public static final String WECHATID_CHATUI_USERNAME_ID = "com.tencent.mm:id/ha";
        public static final String WECHATID_CHATUI_BACK_ID = "com.tencent.mm:id/h9";
        public static final String WECHATID_CHATUI_SWITCH_ID = "com.tencent.mm:id/a_x";


        /**
         * 朋友圈界面
         */
        public static final String ID_Listview = "com.tencent.mm:id/epj";//朋友圈的列表id
        public static final String ID_Item = "com.tencent.mm:id/eu7";// 朋友圈每一个item
        public static final String ID_Img_Head = "com.tencent.mm:id/eoo";//头像id
        public static final String ID_Txt_Name = "com.tencent.mm:id/b9i";//昵称
        public static final String ID_Img_Comment = "com.tencent.mm:id/eop";// 点赞和评论功能按钮

        public static final String ID_Layout_Zan = "com.tencent.mm:id/eoa";//点赞 取消点赞的layout
        public static final String ID_Layout_Comment = "com.tencent.mm:id/eod";//p评论的layout
        public static final String ID_Txt_Zan = "com.tencent.mm:id/eoc";// 点赞 的TextView  会显示是赞或者取消


    }

}
