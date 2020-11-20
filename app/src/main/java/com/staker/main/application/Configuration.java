package com.staker.main.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.staker.main.constant.Constants;
import com.staker.main.pojo.UserPojo;
import com.staker.main.util.DateUtil;
import com.staker.main.util.GsonUtil;


/**
 * 保存用户各种信息的配置文件，这是一个单例
 *
 * @author 小木桩（staker） 04-06-21 04-06-24
 */
public class Configuration {
    private static Configuration config = null;
    private SharedPreferences share;


    private Configuration(Context context) {
        share = context.getSharedPreferences("configurations_data", Context.MODE_PRIVATE);
    }

    public static Configuration getInstance() {
        if (config == null) {
            config = new Configuration(MyApplication.getContext());
        }
        return config;
    }


    public int getSexType() {
        return share.getInt("sex_type", Constants.SexType.Type_All);
    }
    public void setSexType(int sexType) {
        Editor editor = share.edit();
        editor.putInt("sex_type", sexType);
        editor.commit();
    }


    /**
     * 超过多少粉丝就不关注
     * @return
     */
    public long getLimitFans(){
        return share.getInt("limit_fans", 50000);
    }
    public void setLimitFans(long limitFans){
        Editor editor = share.edit();
        editor.putLong("limit_fans", limitFans);
        editor.commit();
    }



    /**
     * 单个视频最长观看时间
     */
    public int getMaxWatchTime() {
        return share.getInt("max_watch_time", 5);
    }
    public void setMaxWatchTime(int second) {
        Editor editor = share.edit();
        editor.putInt("max_watch_time", second);
        editor.commit();
    }

    /**
     * 进入用户界面延迟关注时间
     */
    public int getDelayFollowTime() {
        return share.getInt("delay_follow_time", 5);
    }
    public void setDelayFollowTime(int time) {
        Editor editor = share.edit();
        editor.putInt("delay_follow_time", time);
        editor.commit();
    }


    /**
     * 最多关注用户数量
     */
    public int getMaxFollowCount() {
        return share.getInt("max_follow_count", 300);
    }
    public void setMaxFollowCount(int second) {
        Editor editor = share.edit();
        editor.putInt("max_follow_count", second);
        editor.commit();
    }

    /**
     * 设置点赞的概率数
     */
    public int getVideoZanProbability() {
        return share.getInt("video_zan_probability", 10);
    }
    public void setVideoZanProbability(int zanCount) {
        Editor editor = share.edit();
        editor.putInt("video_zan_probability", zanCount);
        editor.commit();
    }
    public int getHotCommentZanProbability() {
        return share.getInt("hot_comment_zan_probability", 3);
    }
    public void setHotCommentZanProbability(int zanCount) {
        Editor editor = share.edit();
        editor.putInt("hot_comment_zan_probability", zanCount);
        editor.commit();
    }
    public int getNormalCommentZanProbability() {
        return share.getInt("normal_comment_zan_probability", 3);
    }
    public void setNormalCommentZanProbability(int zanCount) {
        Editor editor = share.edit();
        editor.putInt("normal_comment_zan_probability", zanCount);
        editor.commit();
    }

    /**
     * 设置观看多少个视频之后停止脚本
     */
    public int getMaxSeeCount() {
        return share.getInt("max_see_count", 500);
    }
    public void setMaxSeeCount(int maxCount) {
        Editor editor = share.edit();
        editor.putInt("max_see_count", maxCount);
        editor.commit();
    }



    public void setLimitCommentTime(int limitCommentTime) {
        Editor editor = share.edit();
        editor.putInt("limit_comment_time", limitCommentTime);
        editor.commit();
    }
    public int getLimitCommentTime() {
        return share.getInt("limit_comment_time", 10);
    }



    public void setEndDate(String startDate){
        Editor editor = share.edit();
        editor.putString("end_date", startDate);
        editor.commit();
    }
    public String getEndDate(){
        return share.getString("end_date", "0");
    }

    public String getCommentContent(){
        return share.getString("comment_content", "你好，喜欢你拍的视频@这个挺有创意哈");
    }
    public void setCommentContent(String commentContent){
        Editor editor = share.edit();
        editor.putString("comment_content", commentContent);
        editor.commit();
    }

    /**
     * 设置是否启动评论
     */
    public boolean isCommentOpen(){
        return share.getBoolean("is_comment_open", false);
    }
    public void setCommentOpen(boolean isCommentOpen){
        Editor editor = share.edit();
        editor.putBoolean("is_comment_open", isCommentOpen);
        editor.commit();
    }

    /**
     * 设置是否启动私信
     */
    public boolean isChatOpen(){
        return share.getBoolean("is_chat_open", false);
    }
    public void setChatOpen(boolean isChatOpen){
        Editor editor = share.edit();
        editor.putBoolean("is_chat_open", isChatOpen);
        editor.commit();
    }



    /**
     * 设置私信内容 多条中间用@隔开
     * @return
     */
    public String getLetterContent01(){
        return share.getString("letter_content01", "你好！@嗨！@朋友你好啊@在干嘛呢？");
    }
    public void setLetterContent01(String content){
        Editor editor = share.edit();
        editor.putString("letter_content01", content);
        editor.commit();
    }
    public String getLetterContent02(){
        return share.getString("letter_content02", "很高兴认识你！@今天的天气很好");
    }
    public void setLetterContent02(String content){
        Editor editor = share.edit();
        editor.putString("letter_content02", content);
        editor.commit();
    }
    public String getLetterContent03(){
        return share.getString("letter_content03", "我想和你交个朋友@你愿意和我交朋友吗？");
    }
    public void setLetterContent03(String content){
        Editor editor = share.edit();
        editor.putString("letter_content03", content);
        editor.commit();
    }








    public void clearAllInfos() {
        Editor editor = share.edit();
        editor.clear();
        editor.commit();
    }
}
