package com.staker.main.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.staker.main.fragment.BaseFragment;
import com.staker.main.fragment.HomeFragment01;
import com.staker.main.fragment.HomeFragment02;
import com.staker.main.fragment.HomeFragment03;
import com.staker.main.fragment.HomeFragment04;
import com.staker.main.view.util.BottomBarLayoutUtil;
import com.staker.master.R;


/**
 * Created by Clearlee
 * 2017/12/22.
 */
public class MainActivity extends BaseActivity {

    BottomBarLayoutUtil bottomBarLayoutUtil;
    HomeFragment01 homeFragment01;
    HomeFragment02 homeFragment02;
    HomeFragment03 homeFragment03;
    HomeFragment04 homeFragment04;

    private int currentFragmentIndex = 0;//当前进来显示的主界面

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        bottomBarLayoutUtil = new BottomBarLayoutUtil(this, new BottomBarLayoutUtil.IOnClickIndex() {
            @Override
            public void onClick(int index) {
                setFragment(index);
            }
        });
        initReOncreate(savedInstanceState);
    }

    private void initReOncreate(Bundle savedInstanceState) {
        // 第一次进来先切换到首页的fragment
        if (savedInstanceState == null) {
            setFragment(1);
            return;
        }
        currentFragmentIndex = savedInstanceState.getInt("currentFragmentIndex");
        if (currentFragmentIndex > 0) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            homeFragment01 = (HomeFragment01) getFragmentOld(1);
            homeFragment02 = (HomeFragment02) getFragmentOld(2);
            homeFragment03 = (HomeFragment03) getFragmentOld(3);
            homeFragment04 = (HomeFragment04) getFragmentOld(4);
            if (homeFragment01 != null) {
                transaction.hide(homeFragment01);
            }
            if (homeFragment02 != null) {
                transaction.hide(homeFragment02);
            }
            if (homeFragment03 != null) {
                transaction.hide(homeFragment03);
            }
            if (homeFragment04 != null) {
                transaction.hide(homeFragment04);
            }
            transaction.commitAllowingStateLoss();
            setFragment(currentFragmentIndex);
        } else {
            setFragment(1);
        }
    }

    //这是点击底部导航，对应切换fragment的方法
    private void setFragment(final int position) {
        if (currentFragmentIndex == position) {
            return;
        }
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            hideAllFragment(transaction);
            BaseFragment fragment = getFragmentOld(position);
            if (fragment == null) {
                fragment = getFragmentNew(position);
                transaction.add(R.id.layout_fragment, fragment);
            } else {
                transaction.attach(fragment);
                transaction.show(fragment);
            }
            transaction.commitAllowingStateLoss();
            currentFragmentIndex = position;
        } catch (Exception e) {
        }
        changeFragmentShow(position);
    }

    private BaseFragment getFragmentOld(int index) {
        switch (index) {
            case 1:
                return homeFragment01;
            case 2:
                return homeFragment02;
            case 3:
                return homeFragment03;
            case 4:
                return homeFragment04;
            default:
                //不处理
                break;
        }
        return homeFragment01;
    }

    private BaseFragment getFragmentNew(int index) {
        switch (index) {
            case 1:
                homeFragment01 = new HomeFragment01();
                return homeFragment01;
            case 2:
                homeFragment02 = new HomeFragment02();
                return homeFragment02;
            case 3:
                homeFragment03 = new HomeFragment03();
                return homeFragment03;
            case 4:
                homeFragment04 = new HomeFragment04();
                return homeFragment04;
            default:
                //不处理
                break;
        }
        homeFragment01 = new HomeFragment01();
        return homeFragment01;
    }
    private void hideAllFragment(FragmentTransaction transaction){
        if (homeFragment01 != null) {
            transaction.hide(homeFragment01);
        }
        if (homeFragment02 != null) {
            transaction.hide(homeFragment02);
        }
        if (homeFragment03 != null) {
            transaction.hide(homeFragment03);
        }
        if (homeFragment04 != null) {
            transaction.hide(homeFragment04);
        }
    }


    private void changeFragmentShow(int fragmentIndex){

        switch (fragmentIndex){
            case 1:
                if(homeFragment01!=null&&!homeFragment01.isResumeSelf()){
                    homeFragment01.onResumeSelf();
                }
                if(homeFragment02!=null&&homeFragment02.isResumeSelf()){
                    homeFragment02.onPauseSelf();
                }
                if(homeFragment03!=null&&homeFragment03.isResumeSelf()){
                    homeFragment03.onPauseSelf();
                }
                if(homeFragment04!=null&&homeFragment04.isResumeSelf()){
                    homeFragment04.onPauseSelf();
                }
                break;
            case 2:
                if(homeFragment01!=null&&homeFragment01.isResumeSelf()){
                    homeFragment01.onPauseSelf();
                }
                if(homeFragment02!=null&&!homeFragment02.isResumeSelf()){
                    homeFragment02.onResumeSelf();
                }
                if(homeFragment03!=null&&homeFragment03.isResumeSelf()){
                    homeFragment03.onPauseSelf();
                }
                if(homeFragment04!=null&&homeFragment04.isResumeSelf()){
                    homeFragment04.onPauseSelf();
                }
                break;
            case 3:
                if(homeFragment01!=null&&homeFragment01.isResumeSelf()){
                    homeFragment01.onPauseSelf();
                }
                if(homeFragment02!=null&&homeFragment02.isResumeSelf()){
                    homeFragment02.onPauseSelf();
                }
                if(homeFragment03!=null&&!homeFragment03.isResumeSelf()){
                    homeFragment03.onResumeSelf();
                }
                if(homeFragment04!=null&&homeFragment04.isResumeSelf()){
                    homeFragment04.onPauseSelf();
                }
                break;
            case 4:
                if(homeFragment01!=null&&homeFragment01.isResumeSelf()){
                    homeFragment01.onPauseSelf();
                }
                if(homeFragment02!=null&&homeFragment02.isResumeSelf()){
                    homeFragment02.onPauseSelf();
                }
                if(homeFragment03!=null&&homeFragment03.isResumeSelf()){
                    homeFragment03.onPauseSelf();
                }
                if(homeFragment04!=null&&!homeFragment04.isResumeSelf()){
                    homeFragment04.onResumeSelf();
                }
                break;
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentFragmentIndex", currentFragmentIndex);// 保存上次记录的fragment对应的index
        // 主要是因为当Activity重新创建的时候lastfragment会被清空，所以就记录上次lastfragment对应的tag，这样才能保证不会出错
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(homeFragment01!=null&&homeFragment01.isResumeSelf()){
            homeFragment01.onPauseSelf();
        }
        if(homeFragment02!=null&&homeFragment02.isResumeSelf()){
            homeFragment02.onPauseSelf();
        }
        if(homeFragment03!=null&&homeFragment03.isResumeSelf()){
            homeFragment03.onPauseSelf();
        }
        if(homeFragment04!=null&&homeFragment04.isResumeSelf()){
            homeFragment04.onPauseSelf();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (currentFragmentIndex){
            case 1:
                if(homeFragment01!=null&&!homeFragment01.isResumeSelf()){
                    homeFragment01.onResumeSelf();
                }
                break;
            case 2:
                if(homeFragment02!=null&&!homeFragment02.isResumeSelf()){
                    homeFragment02.onResumeSelf();
                }
                break;
            case 3:
                if(homeFragment03!=null&&!homeFragment03.isResumeSelf()){
                    homeFragment03.onResumeSelf();
                }
                break;
            case 4:

                if(homeFragment04!=null&&!homeFragment04.isResumeSelf()){
                    homeFragment04.onResumeSelf();
                }
                break;
        }
    }



}
