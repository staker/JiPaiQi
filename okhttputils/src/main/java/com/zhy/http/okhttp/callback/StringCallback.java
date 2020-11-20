package com.zhy.http.okhttp.callback;

import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by zhy on 15/12/14.
 * 这个接口主要是把返回的response解析成字符串返回，大部分时候用这个回调
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response) throws IOException
    {
        return response.body().string();
    }

}
