package com.staker.main.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.staker.main.activity.AllSettingActivity;
import com.staker.main.activity.FollowCommenterSettingActivity;
import com.staker.main.application.Configuration;
import com.staker.main.application.MyApplication;
import com.staker.main.logic.JumpActivityUtil;
import com.staker.main.util.DateUtil;
import com.staker.main.util.GlobalActionUtil;
import com.staker.main.util.Logg;
import com.staker.main.view.util.ToastUtil;
import com.staker.main.view.util.TopBarLayoutUtil;
import com.staker.master.R;

import douyin.constant.DouYinConstants;

import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Follow_Commenter;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_Nothing;
import static douyin.constant.DouYinConstants.DouYinMissionType.Mission_See_Home;


/**
 * 这是交易界面，界面顶部的  交易所，下面对应切换为此交易所的产品
 */
public class HomeFragment02 extends BaseFragment {

    private Button btnStart01;

    TopBarLayoutUtil topBar;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home02;
    }
    @Override
    public void onResumeSelf() {
        try{
            setStartButtonState(btnStart01,false);
            switch (MyApplication.getContext().missionType){
                case Mission_Follow_Commenter:
                    setStartButtonState(btnStart01,true);
                    break;
            }
        }catch (Exception e){}

        if(MyApplication.getContext().bitmap!=null){
            Logg.e("-----------------------------buweinull");
        }else {
            Logg.e("截图为null");
        }
    }
    @Override
    public void initViews(View view) {
        topBar = new TopBarLayoutUtil(view);
        topBar.setTitle("功能");
        topBar.setRightText("全局设置", new TopBarLayoutUtil.IOnClick() {
            @Override
            public void onClick() {
                JumpActivityUtil.showNormalActivity(getActivity(), AllSettingActivity.class);
            }
        });
        btnStart01=findViewById(R.id.btn_start01);
        initIdsOnClickLinsener(R.id.btn_start01,R.id.btn_setting01,R.id.btn_stop);
    }

    private void startMission01() {
        if(!isBaseReady()){
            return;
        }
        setStartButtonState(btnStart01,true);
        MyApplication.getContext().missionType= Mission_Follow_Commenter;
        JumpActivityUtil.showPackageActivity(getActivity(), DouYinConstants.DouYin_PackageName,DouYinConstants.DouYinClass.Class_MainActivity);
        ToastUtil.showToast("脚本启动成功");
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_setting01:
                JumpActivityUtil.showNormalActivity(getActivity(), FollowCommenterSettingActivity.class);
                break;
            case R.id.btn_start01:
                startMission01();
                break;
            case R.id.btn_stop:
                stopMission();
                break;
        }
    }
    private void stopMission(){
        setStartButtonState(btnStart01,false);
        MyApplication.getContext().missionType=Mission_Nothing;
        ToastUtil.showToast("脚本已经停止运行");
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
