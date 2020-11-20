package com.zhy.http.okhttp.callback;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class Callback<T>
{
    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request)
    {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter()
    {
    }

    /**
     * UI Thread
     *
     * @param progress
     * 加载的进度，这个方法可以根据情况来决定子类是否使用
     */
    public void inProgress(float progress)
    {

    }
    /**
     * Thread Pool Thread
     *
     * 子类必须实现的一个方法，这里是请求的返回结果，子类可以根据需求把response处理后的结果改成对应的类型然后返回到客户端
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws IOException;

    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(T response);


    public static Callback CALLBACK_DEFAULT = new Callback()
    {

        @Override
        public Object parseNetworkResponse(Response response) throws IOException
        {
            return null;
        }

        @Override
        public void onError(Request request, Exception e)
        {

        }

        @Override
        public void onResponse(Object response)
        {

        }
    };

}