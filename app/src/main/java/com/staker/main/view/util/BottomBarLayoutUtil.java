package com.staker.main.view.util;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.staker.master.R;


/**
 * Created by Staker on 2017/6/23.
 * 这个类主要是用来控制底部导航
 */

public class BottomBarLayoutUtil {
    public static int unSelectedTextColor = Color.rgb(165, 165, 165);//为选中底部文字颜色
    public static int selectedTextColor = Color.rgb(26, 172, 237);//选中菜单  底部文字的颜色


    TextView txt01,txt02,txt03,txt04;
    ImageView img01,img02,img03,img04;






    private Activity activity;
    private IOnClickIndex iOnClickIndex;

    /**
     * 设置点击导航之后返回对应的index数字
     */
    public interface IOnClickIndex {
        public abstract void onClick(int index);
    }

    public BottomBarLayoutUtil(Activity activity, IOnClickIndex iOnClickIndex) {
        this.activity = activity;
        this.iOnClickIndex=iOnClickIndex;
        findViewById();
        changeBackgroundBySelected(1);
    }


    private void findViewById() {
        img01 = (ImageView) activity.findViewById(R.id.img_01);
        img02 = (ImageView) activity.findViewById(R.id.img_02);
        img03 = (ImageView) activity.findViewById(R.id.img_03);
        img04 = (ImageView) activity.findViewById(R.id.img_04);


        txt01 = (TextView) activity.findViewById(R.id.txt_01);
        txt02 = (TextView) activity.findViewById(R.id.txt_02);
        txt03 = (TextView) activity.findViewById(R.id.txt_03);
        txt04 = (TextView) activity.findViewById(R.id.txt_04);


        activity.findViewById(R.id.layout_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iOnClickIndex!=null){
                    changeBackgroundBySelected(1);iOnClickIndex.onClick(1);
                }
            }
        });
        activity.findViewById(R.id.layout_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iOnClickIndex!=null){
                    changeBackgroundBySelected(2);
                    iOnClickIndex.onClick(2);
                }
            }
        });
        activity.findViewById(R.id.layout_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iOnClickIndex!=null){
                    changeBackgroundBySelected(3);
                    iOnClickIndex.onClick(3);
                }
            }
        });
        activity.findViewById(R.id.layout_04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iOnClickIndex!=null){
                    changeBackgroundBySelected(4);
                    iOnClickIndex.onClick(4);
                }
            }
        });
    }



        /**
         * 改变选中之后的颜色背景
         *
         * @param index
         *            菜单的下标值
         */
        private void changeBackgroundBySelected(int index) {
            switch (index) {
                case 1:
                    img01.setBackgroundResource(R.mipmap.home_bottombar01_pressed);
                    img02.setBackgroundResource(R.mipmap.home_bottombar02_unpressed);
                    img03.setBackgroundResource(R.mipmap.home_bottombar03_unpressed);
                    img04.setBackgroundResource(R.mipmap.home_bottombar04_unpressed);

                    txt01.setTextColor(selectedTextColor);
                    txt02.setTextColor(unSelectedTextColor);
                    txt03.setTextColor(unSelectedTextColor);
                    txt04.setTextColor(unSelectedTextColor);
                    break;
                case 2:

                    img01.setBackgroundResource(R.mipmap.home_bottombar01_unpressed);
                    img02.setBackgroundResource(R.mipmap.home_bottombar02_pressed);
                    img03.setBackgroundResource(R.mipmap.home_bottombar03_unpressed);
                    img04.setBackgroundResource(R.mipmap.home_bottombar04_unpressed);

                    txt01.setTextColor(unSelectedTextColor);
                    txt02.setTextColor(selectedTextColor);
                    txt03.setTextColor(unSelectedTextColor);
                    txt04.setTextColor(unSelectedTextColor);
                    break;
                case 3:

                    img01.setBackgroundResource(R.mipmap.home_bottombar01_unpressed);
                    img02.setBackgroundResource(R.mipmap.home_bottombar02_unpressed);
                    img03.setBackgroundResource(R.mipmap.home_bottombar03_pressed);
                    img04.setBackgroundResource(R.mipmap.home_bottombar04_unpressed);

                    txt01.setTextColor(unSelectedTextColor);
                    txt02.setTextColor(unSelectedTextColor);
                    txt03.setTextColor(selectedTextColor);
                    txt04.setTextColor(unSelectedTextColor);
                    break;
                case 4:
                    img01.setBackgroundResource(R.mipmap.home_bottombar01_unpressed);
                    img02.setBackgroundResource(R.mipmap.home_bottombar02_unpressed);
                    img03.setBackgroundResource(R.mipmap.home_bottombar03_unpressed);
                    img04.setBackgroundResource(R.mipmap.home_bottombar04_pressed);

                    txt01.setTextColor(unSelectedTextColor);
                    txt02.setTextColor(unSelectedTextColor);
                    txt03.setTextColor(unSelectedTextColor);
                    txt04.setTextColor(selectedTextColor);
                    break;

                default:
                    break;
            }
        }


    }
