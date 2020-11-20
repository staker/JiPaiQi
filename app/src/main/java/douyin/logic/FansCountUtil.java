package douyin.logic;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import static douyin.constant.DouYinConstants.DouYinId.ID_UserProfile_Txt_Fans;

public class FansCountUtil {


    /**
     * 获取粉丝数量
     * @param fansText 粉丝的字符串
     * @return long
     */
    public static long getFansCount(String fansText){
        long fansCount=0;
        if (TextUtils.isEmpty(fansText)){
            return fansCount;
        }
        if(fansText.contains("w")){
            fansText=fansText.replace("w","");
            try {
                fansCount=(long)(Double.parseDouble(fansText)*10000);
            }catch (Exception e){
                fansCount=0;
            }
        } else {
            try {
                fansCount=(long)Double.parseDouble(fansText);
            }catch (Exception e){
                fansCount=0;
            }
        }
        return fansCount;

    }
}
