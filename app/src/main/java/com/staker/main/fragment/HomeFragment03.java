package com.staker.main.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


import com.staker.main.view.util.TopBarLayoutUtil;
import com.staker.master.R;


public class HomeFragment03 extends BaseFragment{
    TopBarLayoutUtil topBar;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home03;
    }
    @Override
    public void initViews(View view) {
        topBar=new TopBarLayoutUtil(view);
        topBar.setTitle("功能介绍");
        init();
    }

    private void init() {

    }

}
