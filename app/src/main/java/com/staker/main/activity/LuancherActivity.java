package com.staker.main.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.staker.main.application.Configuration;
import com.staker.main.constant.Constants;
import com.staker.main.fragment.HomeFragment04;
import com.staker.main.view.SmoothCheckBox;
import com.staker.main.view.util.ToastUtil;
import com.staker.main.view.util.TopBarLayoutUtil;
import com.staker.master.R;

import com.staker.main.logic.IntegerParseUtil;


/**
 *
 */
public class LuancherActivity extends BaseActivity {

    private HomeFragment04 homeFragment04;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_luancher);
        initFragment();
    }

    //这是点击底部导航，对应切换fragment的方法
    private void initFragment() {
        if (homeFragment04 == null) {
            homeFragment04 = new HomeFragment04();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(homeFragment04);
            transaction.attach(homeFragment04);
            transaction.show(homeFragment04);
            return;
        }
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.layout_fragment, homeFragment04);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                ToastUtil.showToast("授权失败");
            } else {
                ToastUtil.showToast("授权成功");
            }
        }

    }
}


