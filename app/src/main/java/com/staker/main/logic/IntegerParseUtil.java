package com.staker.main.logic;

import android.widget.EditText;

import com.staker.main.view.util.ToastUtil;

public class IntegerParseUtil {

    /**
     *
     * @param editText
     * @param minLimit  最小值
     * @param errorTips  报错提示
     * @return  返回正确的值，如果返回-1，代表解析失败
     */
    public static int parseIntNumber(EditText editText,int minLimit,String errorTips){
        try {
            String content=editText.getText().toString();
            int value=Integer.parseInt(content);
            if(value>=minLimit){
                return value;
            }else {
                ToastUtil.showToast(errorTips+"不能小于"+minLimit);
                return -1;
            }
        }catch (Exception e){
            ToastUtil.showToast(errorTips+"数据填写错误");
            return -1;
        }
    }

}
