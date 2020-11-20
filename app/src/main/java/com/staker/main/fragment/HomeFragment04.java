package com.staker.main.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.staker.main.application.Configuration;
import com.staker.main.application.MyApplication;
import com.staker.main.pojo.TimeCodePojo;
import com.staker.main.service.JiPaiService;
import com.staker.main.util.AsynTaskUtil;
import com.staker.main.util.BroadcastUtil;
import com.staker.main.util.DateUtil;
import com.staker.main.view.LoadingDialog;
import com.staker.main.view.util.ToastUtil;
import com.staker.main.view.util.TopBarLayoutUtil;
import com.staker.master.R;

import org.w3c.dom.Text;

import java.util.Date;


import libs.Avos.AvosCodeUtil;
import libs.Avos.HttpObjectListener;

import static com.staker.main.constant.Constants.Action_Stop_Jipai;


public class HomeFragment04 extends BaseFragment{
    private TopBarLayoutUtil topBar;
    TextView txtLeftTime,txtEndTime;


    private EditText edtCode;
    private Button btnSure;


    LoadingDialog loadingDialog;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            init();
            ToastUtil.showToast("激活码激活成功");
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home04;
    }
    @Override
    public void initViews(final View view) {
        txtLeftTime=findViewById(R.id.txt_left_time);
        edtCode=findViewById(R.id.edt_code);
        btnSure=findViewById(R.id.btn_sure);
        txtEndTime=findViewById(R.id.txt_end_time);
        initIdsOnClickLinsener(R.id.btn_start,R.id.btn_stop);
        initViewsClickLinstener(btnSure);
        loadingDialog=LoadingDialog.getInstance();
        initTopBar(view);
        init();
    }
    private void initTopBar(View view) {
        topBar=new TopBarLayoutUtil(view);
        topBar.setTitle("我");
    }
    private void init(){
        String endDate=Configuration.getInstance().getEndDate();
        Log.e("staker","init endDate  ="+endDate);
        if(DateUtil.getNowStringDate("-").compareTo(endDate)>0){
            txtEndTime.setText("到期时间：暂停使用");
            txtLeftTime.setText("剩余时间：0天");
        }else {
            long interverDays=DateUtil.getTwoTimeIntervalDays(DateUtil.getNowStringDate("-"),endDate);
            txtLeftTime.setText("剩余时间："+interverDays+"天");
            txtEndTime.setText("截止日期："+endDate);
        }
    }

    @Override
    public void onResumeSelf() {
        try{
            init();
        }catch (Exception e){}
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.btn_sure:
                startJiHuo();
                break;
            case R.id.btn_start:
                start();
                break;
            case R.id.btn_stop:
                stop();
                break;
        }
    }

    private void start(){
//        String endDate= Configuration.getInstance().getEndDate();
//        if(DateUtil.getNowStringDate("-").compareTo(endDate)>0){
//            ToastUtil.showToast("请联系管理员申请激活码");
//            return ;
//        }

        if (!Settings.canDrawOverlays(getActivity())) {
            ToastUtil.showToast("当前无权限，请授权");
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getActivity().getPackageName())), 0);
        } else {
            MyApplication.getContext().canWindowShow=true;
            getActivity()
            .startService(new Intent( getActivity(), JiPaiService.class));
        }


    }
    private void stop(){
        BroadcastUtil.sendBroadcast(getActivity(),Action_Stop_Jipai);
    }








    private void startJiHuo(){

        String code=edtCode.getText().toString().trim();
        if(code.length()==0){
            ToastUtil.showToast("请输入激活码");
            return;
        }
        loadingDialog.showDialogLoading(true,getActivity(),null);
        AvosCodeUtil.queryCode(code, new HttpObjectListener() {
            @Override
            public void onSucess(Object object) {
                loadingDialog.showDialogLoading(false,getActivity(),null);
                TimeCodePojo timeCodePojo=(TimeCodePojo)object;
                if(timeCodePojo==null){
                    ToastUtil.showToast("系统错误");
                }else{
                    if(!timeCodePojo.canUse){
                        ToastUtil.showToast("此激活码已经被人使用过");
                        return;
                    }
                    //开始更新激活码的状态，并且把时间更新到个人用户当中
                    updateTimeCode(timeCodePojo);
                }
            }

            @Override
            public void onDataError(String dataError) {
                super.onDataError(dataError);
                loadingDialog.showDialogLoading(false,getActivity(),null);
                ToastUtil.showToast(""+dataError);
            }
        });
    }


    private void updateTimeCode(final TimeCodePojo timeCodePojo){
        AvosCodeUtil.updateCode(timeCodePojo, new HttpObjectListener() {
            @Override
            public void onSucess(Object object) {
                String endDate=Configuration.getInstance().getEndDate();
                if(DateUtil.getNowStringDate("-").compareTo(endDate)>0){
                    String newEndDate=DateUtil.getPlusDayDate(DateUtil.getNowStringDate("-"),timeCodePojo.days);
                    Configuration.getInstance().setEndDate(newEndDate);

                }else {
                    long interverDays=DateUtil.getTwoTimeIntervalDays(DateUtil.getNowStringDate("-"),endDate);
                    long totalDays=interverDays+timeCodePojo.days;
                    String newEndDate=DateUtil.getPlusDayDate(DateUtil.getNowStringDate("-"),(int)totalDays);
                    Configuration.getInstance().setEndDate(newEndDate);
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

}
