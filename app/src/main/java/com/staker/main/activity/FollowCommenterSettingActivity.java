package com.staker.main.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.staker.main.application.Configuration;
import com.staker.main.application.MyApplication;
import com.staker.main.constant.Constants;
import com.staker.main.logic.IntegerParseUtil;
import com.staker.main.view.SmoothCheckBox;
import com.staker.main.view.util.ToastUtil;
import com.staker.main.view.util.TopBarLayoutUtil;
import com.staker.master.R;
import com.suke.widget.SwitchButton;


/**
 *
 */
public class FollowCommenterSettingActivity extends BaseActivity {
    private EditText edtFollowCount,edtCommentTime;
    private SwitchButton switchChat;
    TopBarLayoutUtil topBar;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_follow_commenter_setting);
        edtFollowCount=(EditText)findViewById(R.id.edt_follow_count);
        edtCommentTime=(EditText)findViewById(R.id.edt_comment_time);
        switchChat=(SwitchButton)findViewById(R.id.switch_chat);






        initIdsClickLinstener(R.id.btn_sure);
        initTopBar();
        init();
    }

    private void init() {
        int followCount = Configuration.getInstance().getMaxFollowCount();
        edtFollowCount.setText("" + followCount);

        int limitCommentTime=Configuration.getInstance().getLimitCommentTime();
        edtCommentTime.setText("" + limitCommentTime);

        boolean isChatOpen=Configuration.getInstance().isChatOpen();
        if(isChatOpen){
            switchChat.setChecked(true);
        }else {
            switchChat.setChecked(false);
        }
    }
    private void initTopBar(){
        topBar=new TopBarLayoutUtil(this);
        topBar.setTitle("关注最近评论用户设置", Color.WHITE);
        topBar.setLeftDrawable(0, new TopBarLayoutUtil.IOnClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }


    private void saveData() {

        int followCount = IntegerParseUtil.parseIntNumber(edtFollowCount,1,"关注人数");
        if(followCount==-1){
            return;
        }

        int commentLimitTime = IntegerParseUtil.parseIntNumber(edtCommentTime,1,"评论时间");
        if(commentLimitTime==-1){
            return;
        }


        Configuration.getInstance().setChatOpen(switchChat.isChecked());


        Configuration.getInstance().setMaxFollowCount(followCount);
        Configuration.getInstance().setLimitCommentTime(commentLimitTime);
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
