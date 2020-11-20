package com.zhy.http.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by zhy on 15/12/14.
 * 这个接口主要是把返回的response解析成Bitmap返回，用处不到，因为现在有很多图片加载库
 */
public abstract class BitmapCallback extends Callback<Bitmap>
{
    @Override
    public Bitmap parseNetworkResponse(Response response) throws IOException
    {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
