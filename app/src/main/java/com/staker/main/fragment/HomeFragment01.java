package com.staker.main.fragment;

import android.view.View;
import android.widget.Button;


import com.staker.main.activity.AllSettingActivity;
import com.staker.main.activity.SeeHomeSettingActivity;
import com.staker.main.application.Configuration;
import com.staker.main.application.MyApplication;
import com.staker.main.logic.JumpActivityUtil;
import com.staker.main.util.DateUtil;
import com.staker.main.util.GlobalActionUtil;
import com.staker.main.view.util.ToastUtil;
import com.staker.main.view.util.TopBarLayoutUtil;
import com.staker.master.R;

import douyin.constant.DouYinConstants;

import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Follow_Commenter;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Nothing;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_See_Home;


/**
 *
 *cd D:\D\AppS\木木模拟器\emulator\nemu\vmonitor\bin
 *
 * adb_server.exe connect 127.0.0.1:7555

 */
public class HomeFragment01 extends BaseFragment {
    TopBarLayoutUtil topBar;
    private Button btnStart01;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home01;
    }
    @Override
    public void onResumeSelf() {
        try{
            try{
                setStartButtonState(btnStart01,false);
                switch (MyApplication.getContext().missionType){
                    case Mission_See_Home:
                        setStartButtonState(btnStart01,true);
                        break;
                }
            }catch (Exception e){}
        }catch (Exception e){}
    }
    @Override
    public void initViews(View view) {
        btnStart01=findViewById(R.id.btn_start01);
        initIdsOnClickLinsener(R.id.btn_start01,R.id.btn_setting01,R.id.btn_stop);
        topBar = new TopBarLayoutUtil(view);
        topBar.setTitle("养号");
        topBar.setRightText("全局设置", new TopBarLayoutUtil.IOnClick() {
            @Override
            public void onClick() {
                JumpActivityUtil.showNormalActivity(getActivity(), AllSettingActivity.class);
            }
        });
    }




    private void startMission01() {
        if(!isBaseReady()){
            return;
        }
        setStartButtonState(btnStart01,true);
        MyApplication.getContext().missionType= Mission_See_Home;
        JumpActivityUtil.showPackageActivity(getActivity(), DouYinConstants.DouYin_PackageName,DouYinConstants.DouYinClass.Class_MainActivity);
        ToastUtil.showToast("脚本启动成功");
    }
    private void stopMission(){
        setStartButtonState(btnStart01,false);
        MyApplication.getContext().missionType=Mission_Nothing;
        ToastUtil.showToast("脚本已经停止运行");
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_setting01:
                JumpActivityUtil.showNormalActivity(getActivity(), SeeHomeSettingActivity.class);
                break;
            case R.id.btn_start01:
                startMission01();
                break;
            case R.id.btn_stop:
                stopMission();
                break;
        }
    }
    private boolean isBaseReady(){
        String endDate= Configuration.getInstance().getEndDate();
        if(DateUtil.getNowStringDate("-").compareTo(endDate)>0){
            ToastUtil.showToast("请联系管理员申请激活码");
            return false;
        }
        if(!GlobalActionUtil.checkCurrentServiceEnabled(getActivity())){
            ToastUtil.showToast("请选择柚子引流\n并点击进去开启授权");
            GlobalActionUtil.goAccess(getActivity());
            return false;
        }
        return true;
    }
    private void setStartButtonState(Button btnStart,boolean isStart){
        if(isStart){
            btnStart.setBackgroundResource(R.drawable.shape_red01_3);
            btnStart.setText("运行中");
            btnStart.setEnabled(false);
        }else {
            btnStart.setBackgroundResource(R.drawable.shape_blue01_3);
            btnStart.setText("启动");
            btnStart.setEnabled(true);
        }
    }
}
