package libs.okhttp;

import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

/**
 * 把张弘扬的okhttputils库，再次简单封装一下，变成自己习惯使用的方式
 * 这是通用的方式，可能传入的参数比较多，但是使用在具体项目的时候，还是需要再封装一层。请看MyOkHttpUtil类，专门针对单个项目而实现
 */

public class MyOkHttpUtil {

    //两个网络请求方式
    public interface HttpMode{
        public static final int Get=1;//
        public static final int Post=2;//
    }


    /**
     * 通过Post方式请求url
     * @param url  请求地址
     * @param map 传入的键值对
     * @param callback  回掉接口   有三种，分别是BitmapCallBack，FileCallBack，StringCallBack，大部分情况只用到了StringCallBack
     */
    public static void sendPost(String url, Map<String, String> map, Callback callback) {
        excuteRequest(url, HttpMode.Post,map, callback);
    }

    /**
     * 通过Get方式请求url
     * @param url  请求地址
     * @param map 传入的键值对
     * @param callback  回掉接口  有三种，分别是BitmapCallBack，FileCallBack，StringCallBack，大部分情况只用到了StringCallBack
     */
    public static void sendGet(String url, Map<String, String> map, Callback callback) {
        excuteRequest(url, HttpMode.Get,map, callback);
    }
    public static void sendGet(String url, Callback callback) {
        excuteRequest(url, HttpMode.Get,null, callback);
    }








    //执行定义好的请求
    private static void excuteRequest(String url, int httpMode, Map<String, String> map, Callback callback ){
        OkHttpRequestBuilder requestBuilder;
        if(httpMode== HttpMode.Post){
            requestBuilder= OkHttpUtils.post();
        }else{
            requestBuilder= OkHttpUtils.get();;
        }
        if(map!=null){
            requestBuilder.params(map);
        }
        requestBuilder.url(url).build().execute(callback);
    }

    /**
     * 调用下面的实现方式，直接用get形式，这个比较通用（需要自己启动线程）
     * @param url
     * @return  String
     */
    public static String getRequestContent(String url){
        return getRequestContent(url, HttpMode.Get,null);
    }
    /**
     * 同步请求网络数据，请自己开线程
     * @param url
     * @param httpMode  请求模式 ，一般都是Get
     * @param map  一般这里都是不需要传的，除非是post格式
     * @return  返回请求结果字符串
     */
    public static String getRequestContent(String url, int httpMode, Map<String, String> map){
        OkHttpRequestBuilder requestBuilder;
        if(httpMode== HttpMode.Post){
            requestBuilder= OkHttpUtils.post();
        }else{
            requestBuilder= OkHttpUtils.get();;
        }
        if(map!=null){
            requestBuilder.params(map);
        }
        requestBuilder.url(url);
        RequestCall rc = requestBuilder.build();
        rc.connTimeOut(10000);
        rc.readTimeOut(10000);
        rc.writeTimeOut(10000);
        try {
            Response response = rc.execute();
            String str = response.body().string();
            return str;
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
