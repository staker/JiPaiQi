package libs.okhttp;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Staker on 2017/6/26.
 * 这个类是专门对应通用型服务器接口的  网络请求代码
 * 一般后台服务器接口定义都是一般是p1和p2 对应json1和json2  json1对应的是接口需求的数据，json2对应的是服务器专门的用户token
 * 有些后台服务器只要传一个数据，那么就直接是  key关键字p对应json
 */

public class MyServerHttpUtil{
    //后台服务器的回掉接口  其实和StringCallback定义相同，只是这里是自己定义，区别一下
    public abstract static class ServerCallBack{
        public abstract void onSuccess(String result);
        public abstract void onError(Exception e);//请求接口调用失败，那么就会出现异常，则调用上面的方法
        /**
         * UI Thread 这个方法直接再主线程中执行的
         * @param progress  对应的加载进度
         * 加载的进度，这个方法可以根据情况来决定子类是否使用
         */
        public void inProgress(float progress){ }
    }


    /**
     * 异步调用后台接口
     * @param url  后台接口
     * @param p  传入接口的json数据
     * @param callBack  回调接口
     */
    public static void sendServerRequest(String url, String p, final ServerCallBack callBack){
        HashMap<String,String> map=new HashMap<String,String>();
        if(url==null){
            return ;
        }
        if(p==null){
            p="test";
        }
        map.put("p",p);
        MyOkHttpUtil.sendPost(url, map, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                if(callBack!=null){
                    callBack.onError(e);
                }
            }
            @Override
            public void onResponse(String response) {
                if(callBack!=null){
                    callBack.onSuccess(response);
                }
            }
            public void inProgress(float progress){
                if(callBack!=null){
                    callBack.inProgress(progress);
                }
            }
        });
    }





    /**
     *
     * 异步调用后台接口
     * @param url  后台接口
     * @param p  传入接口的json数据
     * @param callBack  回调接口
     */
    public static void sendServerRequest(String url, String p, final StringCallback callBack){
        HashMap<String,String> map=new HashMap<String,String>();//这个一定要不能为空，否则请求会出错
        if(url==null){
            return ;
        }
        if(p==null){
            p="test";//因为传的参数P一定不能为空，所以这里就随便写一个默认的值
        }
        map.put("p",p);
        MyOkHttpUtil.sendPost(url, map, callBack);
    }













    /**
     *
     * @param url  后台接口
     * @param p1  传入接口的json数据
     * @param p2   一般这里是传入后台的token
     * @param p3    可能会用到的传入后台的字符串
     * @param callBack  回调接口
     */
    public static void sendServerRequest(String url, String p1, String p2, String p3, final ServerCallBack callBack){
        HashMap<String,String> map=new HashMap<String,String>();
        if(url==null){
            return ;
        }

        if(p1!=null){
            map.put("p1",p1);
        }else{
            map.put("p1","test");
        }
        if(p2!=null){
            map.put("p2",p2);
        }
        if(p3!=null){
            map.put("p3",p3);
        }
        MyOkHttpUtil.sendPost(url, map, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                if(callBack!=null){
                    callBack.onError(e);
                }
            }
            @Override
            public void onResponse(String response) {
                if(callBack!=null){
                    callBack.onSuccess(response);
                }
            }
            public void inProgress(float progress){
                if(callBack!=null){
                    callBack.inProgress(progress);
                }
            }
        });
    }





    /**
     * 请求后台数据
     * @param url  对应的接口url
     * @param p  传入后台的p对应的字符串数据
     * @return  String
     * @throws IOException
     */
    public static String sendServerRequest(String url, String p){
        HashMap<String,String> map=new HashMap<String,String>();
        if(url==null){
            return null;
        }
        if(p==null){
            p="test";//因为传的参数P一定不能为空，所以这里就随便写一个默认的值
        }
        map.put("p",p);

        return MyOkHttpUtil.getRequestContent(url, MyOkHttpUtil.HttpMode.Post,map);
    }


    /**
     * 这是一个请求后台服务器接口的 方法 一般后台服务器是传一个json对应一个p关键字，如果传两个参数的话，一般是p1和p2 对应json1和json2
     * 这里我们就设置三个参数  分别为关键字 p1,p2,p3
     * @param url  后台接口的地址
     * @param p1  key关键字p1对应的 json或者字符串
     * @param p2  key关键字p2对应的 json或者字符串
     * @param p3  key关键字p2对应的 json或者字符串
     * @return  请求接口的返回结果，此方法不开启线程
     * @throws IOException
     */
    public static String sendServerRequest(String url, String p1, String p2, String p3){
        HashMap<String,String> map=new HashMap<String,String>();
        if(url==null){
            return null;
        }
        if(p1!=null){
            map.put("p1",p1);
        }else{
            map.put("p1","test");
        }

        if(p2!=null){
            map.put("p2",p2);
        }
        if(p3!=null){
            map.put("p3",p3);
        }
        return MyOkHttpUtil.getRequestContent(url, MyOkHttpUtil.HttpMode.Post,map);
    }

}
