package douyin.logic;

import android.text.TextUtils;

import com.staker.main.application.Configuration;
import com.staker.main.logic.IntegerParseUtil;
import com.staker.main.util.Logg;

public class TimeUtil {
    /**
     * 胖段评论是否是在设置的分钟以前
     * @param comment
     * @return
     */
    public static boolean isCommentMinuteAgo(String comment){
        if(TextUtils.isEmpty(comment)){
            return false;
        }
        comment=comment.trim().replace("\u2060","");
        String[] strs=comment.split(" ");
        String suffixTime=strs[strs.length-1];
        if(suffixTime.contains("刚刚")){
            return true;
        }
        int commentTime= Configuration.getInstance().getLimitCommentTime();

        if(suffixTime.contains("分钟前")){
            suffixTime=suffixTime.replace("分钟前","");
            int showTime= Integer.parseInt(suffixTime);

            if(showTime<=commentTime){
                return true;
            }
        }
        return false;
    }

}
