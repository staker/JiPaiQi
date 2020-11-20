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
import com.staker.main.logic.IntegerParseUtil;
import com.staker.main.view.util.ToastUtil;
import com.staker.main.view.util.TopBarLayoutUtil;
import com.staker.master.R;
import com.suke.widget.SwitchButton;


/**
 *
 */
public class SeeHomeSettingActivity extends BaseActivity {
    private EditText edtSeeCount;
    private SwitchButton switchComment;
    TopBarLayoutUtil topBar;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_see_home_setting);
        edtSeeCount=(EditText)findViewById(R.id.edt_see_count);
        switchComment = (SwitchButton)findViewById(R.id.switch_comment);
        initIdsClickLinstener(R.id.btn_sure);
        initTopBar();
        init();
    }

    private void init() {

        int seeCount = Configuration.getInstance().getMaxSeeCount();
        edtSeeCount.setText("" + seeCount);

        boolean isComentOpen=Configuration.getInstance().isCommentOpen();
        switchComment.setChecked(isComentOpen);



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

        int seeCount=IntegerParseUtil.parseIntNumber(edtSeeCount,1,"观看视频个数");
        if(seeCount==-1){
            return;
        }

        if (switchComment.isChecked()) {
            Configuration.getInstance().setCommentOpen(true);
        }else {
            Configuration.getInstance().setCommentOpen(false);
        }

        Configuration.getInstance().setMaxSeeCount(seeCount);

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
