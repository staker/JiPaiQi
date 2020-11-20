package libs.Avos;

import android.content.Context;

import cn.leancloud.core.AVOSCloud;
import cn.leancloud.core.AVOSService;


/**
 * Created by Staker on 2017/8/25.
 */

public class AvosUtil {
    public final static String AppId="DJqUtDexS2OyiSIJOr1DYPNm-gzGzoHsz";
    public final static String AppKey="1GS143kTDHtCyofM3JmQghAl";


    public static void initialize(Context context){
        // 配置 SDK 储存
//        AVOSCloud.setServer(AVOSService.API, "https://xxx.example.com");
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(AppId,AppKey);
    }


}
