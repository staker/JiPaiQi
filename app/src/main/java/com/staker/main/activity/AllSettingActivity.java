package com.staker.main.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.staker.main.application.Configuration;
import com.staker.main.constant.Constants;
import com.staker.main.view.SmoothCheckBox;
import com.staker.main.view.util.ToastUtil;
import com.staker.main.view.util.TopBarLayoutUtil;
import com.staker.master.R;

import com.staker.main.logic.IntegerParseUtil;


/**
 *
 */
public class AllSettingActivity extends BaseActivity {
    private EditText edtWatchTime,edtComment,edtDelayFollow;
    private EditText edtHotCommentZanProbability, edtVideoZanProbability,edtNormalCommentZanProbability;
    private EditText edtLetter01,edtLetter02,edtLetter03;
    private SmoothCheckBox switchNan,switchNv,switchAll;
    TopBarLayoutUtil topBar;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_all_setting);
        switchNan=(SmoothCheckBox)findViewById(R.id.switch_nan);
        switchNv=(SmoothCheckBox)findViewById(R.id.switch_nv);
        switchAll = (SmoothCheckBox)findViewById(R.id.switch_all);
        edtWatchTime=(EditText)findViewById(R.id.edt_watch_time);
        edtComment = (EditText)findViewById(R.id.edt_comment);
        edtLetter01 = (EditText)findViewById(R.id.edt_letter01);
        edtLetter02 = (EditText)findViewById(R.id.edt_letter02);
        edtLetter03 = (EditText)findViewById(R.id.edt_letter03);
        edtVideoZanProbability = (EditText)findViewById(R.id.edt_zan_probability);
        edtHotCommentZanProbability= (EditText)findViewById(R.id.edt_hot_comment_zan_probability);
        edtNormalCommentZanProbability= (EditText)findViewById(R.id.edt_normal_comment_zan_probability);
        edtDelayFollow= (EditText)findViewById(R.id.edt_delay_follow);
        initIdsClickLinstener(R.id.btn_sure);
        initTopBar();
        init();
    }

    private void init() {
        int watchTime = Configuration.getInstance().getMaxWatchTime();
        edtWatchTime.setText("" + watchTime);

        int delayFollow = Configuration.getInstance().getDelayFollowTime();
        edtDelayFollow.setText("" + delayFollow);

        String commentContent = Configuration.getInstance().getCommentContent();
        edtComment.setText("" + commentContent);

        String letterContent01 = Configuration.getInstance().getLetterContent01();
        String letterContent02 = Configuration.getInstance().getLetterContent02();
        String letterContent03 = Configuration.getInstance().getLetterContent03();
        edtLetter01.setText("" + letterContent01);
        edtLetter02.setText("" + letterContent02);
        edtLetter03.setText("" + letterContent03);

        int videoZanProbabilityCount = Configuration.getInstance().getVideoZanProbability();
        int hotCommentZanProbability= Configuration.getInstance().getHotCommentZanProbability();
        int normalCommentZanProbability= Configuration.getInstance().getNormalCommentZanProbability();
        edtVideoZanProbability.setText("" + videoZanProbabilityCount);
        edtHotCommentZanProbability.setText("" + hotCommentZanProbability);
        edtNormalCommentZanProbability.setText("" + normalCommentZanProbability);

        int sexType=Configuration.getInstance().getSexType();
        if(sexType== Constants.SexType.Type_Nan){
            switchNan.setChecked(true);
        }else  if(sexType== Constants.SexType.Type_Nv){
            switchNv.setChecked(true);
        }else {
            switchAll.setChecked(true);
        }

        switchNan.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if(isChecked){
                    switchNv.setChecked(false);
                    switchAll.setChecked(false);
                }
            }
        });
        switchNv.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if(isChecked){
                    switchNan.setChecked(false);
                    switchAll.setChecked(false);
                }
            }
        });
        switchAll.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if(isChecked){
                    switchNv.setChecked(false);
                    switchNan.setChecked(false);
                }
            }
        });

    }
    private void initTopBar(){
        topBar=new TopBarLayoutUtil(this);
        topBar.setTitle("刷视频点赞评论设置", Color.WHITE);
        topBar.setLeftDrawable(0, new TopBarLayoutUtil.IOnClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }


    private void saveData() {
        int watchTime=IntegerParseUtil.parseIntNumber(edtWatchTime,2,"最长刷视频时间");
        if(watchTime==-1){
            return;
        }

        int videoZanProbability=IntegerParseUtil.parseIntNumber(edtVideoZanProbability,0,"视频点赞概率");
        if(videoZanProbability==-1){
            return;
        }

        int hotCommentZanProbability=IntegerParseUtil.parseIntNumber(edtHotCommentZanProbability,0,"热评点赞概率");
        if(hotCommentZanProbability==-1){
            return;
        }

        int normalCommentZanProbability=IntegerParseUtil.parseIntNumber(edtNormalCommentZanProbability,0,"普通评论点赞概率");
        if(normalCommentZanProbability==-1){
            return;
        }

        int delayFollow=IntegerParseUtil.parseIntNumber(edtDelayFollow,1,"延迟关注时间");
        if(delayFollow==-1){
            return;
        }


        String comment=edtComment.getText().toString();
        if(TextUtils.isEmpty(comment)){
            ToastUtil.showToast("请设置评论内容");
            return;
        }

        String letter01=edtLetter01.getText().toString();
        if(TextUtils.isEmpty(letter01)){
            ToastUtil.showToast("私信话术语一必须设置");
            return;
        }
        String letter02=edtLetter02.getText().toString();
        String letter03=edtLetter03.getText().toString();

        if(switchNan.isChecked()){
            Configuration.getInstance().setSexType(Constants.SexType.Type_Nan);
        }else if(switchNv.isChecked()){
            Configuration.getInstance().setSexType(Constants.SexType.Type_Nv);
        }else {
            Configuration.getInstance().setSexType(Constants.SexType.Type_All);
        }

        Configuration.getInstance().setLetterContent01(letter01);
        Configuration.getInstance().setLetterContent02(letter02);
        Configuration.getInstance().setLetterContent03(letter03);
        Configuration.getInstance().setCommentContent(comment);
        Configuration.getInstance().setDelayFollowTime(delayFollow);
        Configuration.getInstance().setVideoZanProbability(videoZanProbability);
        Configuration.getInstance().setHotCommentZanProbability(hotCommentZanProbability);
        Configuration.getInstance().setNormalCommentZanProbability(normalCommentZanProbability);
        Configuration.getInstance().setMaxWatchTime(watchTime);

        ToastUtil.showToast("设置成功");
        finish();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_sure:
                saveData();
                break;

        }
    }
}
