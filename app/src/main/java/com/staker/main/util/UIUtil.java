package com.staker.main.util;

import android.content.Context;
import android.util.TypedValue;

import com.staker.main.application.MyApplication;


/**
 * Created by XJ on 2016/2/4.<br>
 * ${Desc}<br>
 * ${Todo}
 */
public class UIUtil {

    public static int dp2px(Context context, float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    private static final float defultDisplayWidth = 750;
    private static final float defultDisplayHeight = 1334;

    public static float getDimenByWidth(float dimen){
        return StakerUtil.getWindowWidth(MyApplication.getContext()) / defultDisplayWidth * dimen;
    }

    public static float getDimenByHeight(float dimen){
        return StakerUtil.getWindowHeigh(MyApplication.getContext())  / defultDisplayHeight * dimen;
    }
}
