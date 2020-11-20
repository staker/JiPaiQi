package com.staker.main.view;

import android.content.Context;
//创建一个这样的view的目的是为了给fragment设置状态栏颜色的时候，不会出现多个顶部的状态栏view
public class StatusBarView extends android.view.View{
    public  StatusBarView(Context context){
        super(context);
    }
}
