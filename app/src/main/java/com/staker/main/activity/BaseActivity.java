package com.staker.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.staker.main.util.ActivityUtil;


/**
 * Activity基类
 * 2017/1/17.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected BaseActivity mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        ActivityUtil.getInstance().add(mContext);//将当前Activity加载到队列

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置Activity为竖屏

        //下面两句代码是让输入框的焦点显示出来，不让键盘盖住
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initViews(savedInstanceState);//初始化控件
    }

    protected abstract void initViews(Bundle savedInstanceState);//这个方法相当于在别的Activity中的onCreate方法必须改写


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.getInstance().remove(this);//删除Activity队列
    }

    @Override
    public void onClick(View v) {
    }





    //不传数据跳转到其他界面
    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 用view 的id形式给初始化的view设置监听事件
     * 就是直接给一个控件设置一个监听事件即可，但是并不需要find这个view来使用，比如导航栏的返回箭头，
     * 只要设置监听即可
     *
     * @param ids
     */
    public void initIdsClickLinstener(int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    /**
     * 给一系列的view设置监听事件，这个方法很好用
     * 由于需要操作这个view，不仅仅是设置监听事件，所以上面的监听方法就不太使用了
     *
     * @param views
     */
    public void initViewsClickLinstener(View... views) {
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }
}