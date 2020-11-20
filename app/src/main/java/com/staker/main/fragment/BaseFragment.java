package com.staker.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.staker.main.activity.BaseActivity;


/**
 * Fragment基类
 * 这个类里面的EventBus到以后一定要全部去掉，只有在需要的Fragment里面注册即可
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected BaseActivity ctivity;
    protected View mView;
    private boolean isInit = false;
    private boolean isResumeSelf=false;


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        activity = (BaseActivity) activity;
//    }

    public abstract int getLayoutId();

    public abstract void initViews(View view);//创建好了view之后，就可以调用这个方法来进行一些初始化的操作

    /**
     * 这个方法可以让子fragment在findview的时候很简单的使用，并且不需要强转，类似Activity里面的findviewbyid
     * @param resID 控件的ID
     * @param <T> 接收的控件类型，
     * @return 任意类型
     */
    public <T extends View> T findViewById(int resID) {
        return (T) mView.findViewById(resID);
    }
    /**
     * 这个方法是仅仅只做创建view的操作
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInit) {//这样的目的
            initViews(mView);
            isInit = true;
        }
    }

    @Override
    public void onClick(View v) {
    }
    /**
     * 以下是两个设置监听的方法，很实用
     *
     * @param ids
     */
    public void initIdsOnClickLinsener(int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }
    public void initViewsClickLinstener(View... views) {
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }


    /**
     * 这两个方法可以 在子fragment里面调用。 是它本身可见和不可见时候触发的。而不是上层的Activity。
     * 但是它需要手动在上层的Activity去自己调用。
     */
    public void onResumeSelf() {
        isResumeSelf=true;
    }
    public void onPauseSelf(){
        isResumeSelf=false;
    }
    public boolean isResumeSelf(){
        return  isResumeSelf;
    }
}