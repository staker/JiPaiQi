package com.staker.main.view.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.staker.master.R;


/**
 * Created by Staker on 2017/6/23.
 * 这个类主要是用来控制顶部导航
 * 这是一个通用的顶部导航栏布局，左边是图片加文字   中间是title文字   右边是标题加问题   通过显示隐藏 和更换图片的方式来适应各种界面导航
 * 在布局默认中，所有控件都是gone即不可见
 */

public class TopBarLayoutUtil {
    public static int backImageResIdWhite = R.drawable.nav_back_white;//白色的返回按钮,默认是这种颜色的返回按钮
    public static int backImageResIdGray = R.drawable.nav_back_gray;//灰色的返回按钮


    TextView txtLeft;

    ImageView imgLeft;
    TextView txtRight;
    ImageView imgRight;
    TextView txtTitle;

    RelativeLayout layoutMain;

    View viewLine;
    private View view;
    private Activity activity;

    /**
     * 设置点击图片或者文字之后的回掉接口
     */
    public interface IOnClick {
        public abstract void onClick();
    }

    public TopBarLayoutUtil(View view) {
        this.view = view;
        findViewByView();
    }

    public TopBarLayoutUtil(Activity activity) {
        this.activity = activity;
        findViewByActivity();
    }

    private void findViewByView() {
        if (txtLeft == null) {
            txtLeft = (TextView) view.findViewById(R.id.txt_left);
            txtRight = (TextView) view.findViewById(R.id.txt_right);
        }

        if (txtTitle == null) {
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
        }
        if (imgLeft == null) {
            imgLeft = (ImageView) view.findViewById(R.id.img_left);
            imgRight = (ImageView) view.findViewById(R.id.img_right);
        }
        layoutMain=(RelativeLayout) view.findViewById(R.id.title_relative_layout);
        viewLine= view.findViewById(R.id.view_bottom_line);
    }

    private void findViewByActivity() {
        if (txtLeft == null) {
            txtLeft = (TextView) activity.findViewById(R.id.txt_left);
            txtRight = (TextView) activity.findViewById(R.id.txt_right);
        }

        if (txtTitle == null) {
            txtTitle = (TextView) activity.findViewById(R.id.txt_title);
        }
        if (imgLeft == null) {
            imgLeft = (ImageView) activity.findViewById(R.id.img_left);
            imgRight = (ImageView) activity.findViewById(R.id.img_right);
        }
        layoutMain=(RelativeLayout) activity.findViewById(R.id.title_relative_layout);
        viewLine= activity.findViewById(R.id.view_bottom_line);

    }

    public void seBottomLineColor(int color) {
        if (viewLine != null) {
            viewLine.setVisibility(View.VISIBLE);
            viewLine.setBackgroundColor(color);
        }
    }

    /**
     * 设置导航条的背景颜色
     * @param color
     */
    public void setTopBarColor(int color) {
        if (layoutMain != null) {
            layoutMain.setBackgroundColor(color);
        }
    }

    /**
     * 设置中间标题的文字
     *
     * @param title
     */
    public void setTitle(String title) {
        if (txtTitle != null && !TextUtils.isEmpty(title)) {
            txtTitle.setVisibility(View.VISIBLE);
            txtTitle.setText(title);
        }
    }

    public void setTitle(String title, int color) {
        if (txtTitle != null && !TextUtils.isEmpty(title)) {
            txtTitle.setVisibility(View.VISIBLE);
            txtTitle.setText(title);
            txtTitle.setTextColor(color);
        }
    }


    /**
     * 设置左边文字
     */
    public void setLeftText(String leftText, final IOnClick listener) {
        if (txtLeft != null && !TextUtils.isEmpty(leftText)) {
            txtLeft.setVisibility(View.VISIBLE);
            imgLeft.setVisibility(View.INVISIBLE);
            txtLeft.setText(leftText);
            if (listener != null) {
                txtLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick();
                    }
                });
            }
        }
    }
    /**
     * 设置左边文字
     */
    public void setLeftText(String leftText) {
        if (txtLeft != null && !TextUtils.isEmpty(leftText)) {
            txtLeft.setVisibility(View.VISIBLE);
            imgLeft.setVisibility(View.INVISIBLE);
            txtLeft.setText(leftText);
        }
    }

    public void setLeftTextColor(int color) {
        txtLeft.setTextColor(color);
    }

    public void setRightTextColor(int color) {
        txtRight.setTextColor(color);
    }

    /**
     * 设置右边文字和监听器
     */
    public void setRightText(String rightText, final IOnClick listener) {
        if (txtRight != null && !TextUtils.isEmpty(rightText)) {
            txtRight.setVisibility(View.VISIBLE);
            imgRight.setVisibility(View.INVISIBLE);
            txtRight.setText(rightText);
            if (listener != null) {
                txtRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick();
                    }
                });
            }
        }
    }
    public void setRightText(String rightText) {
        if (txtRight != null && !TextUtils.isEmpty(rightText)) {
            txtRight.setVisibility(View.VISIBLE);
            imgRight.setVisibility(View.INVISIBLE);
            txtRight.setText(rightText);
        }
    }

    /**
     * 设置左边的图片资源和点击之后的监听器，drawableResId《=0的话就使用默认xml中的图片
     *
     * @param drawableResId 图片id
     * @param listener      监听器
     */
    public void setLeftDrawable(int drawableResId, final IOnClick listener) {
        if (imgLeft != null) {
            txtLeft.setVisibility(View.INVISIBLE);
            imgLeft.setVisibility(View.VISIBLE);
            if (drawableResId > 0) {
                imgLeft.setImageResource(drawableResId);
            } else {
                imgLeft.setImageResource(backImageResIdWhite);
            }
            if (listener != null) {
                imgLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick();
                    }
                });
            }
        }
    }

    /**
     * 设置右边的图片资源和点击之后的监听器，drawableResId《=0的话就使用默认xml中的图片
     *
     * @param drawableResId 图片id
     * @param listener      监听器
     */
    public void setRightDrawable(int drawableResId, final IOnClick listener) {
        if (imgRight != null) {
            txtRight.setVisibility(View.INVISIBLE);
            imgRight.setVisibility(View.VISIBLE);
            if (drawableResId > 0) {
                imgRight.setImageResource(drawableResId);
            }
            if (listener != null) {
                imgRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick();
                    }
                });
            }
        }
    }
    public void setRightDrawable(int drawableResId) {
        if (imgRight != null) {
            txtRight.setVisibility(View.INVISIBLE);
            imgRight.setVisibility(View.VISIBLE);
            if (drawableResId > 0) {
                imgRight.setImageResource(drawableResId);
            }
        }
    }
    public void setLeftDrawable(int drawableResId) {
        if (imgLeft != null) {
            txtLeft.setVisibility(View.INVISIBLE);
            imgLeft.setVisibility(View.VISIBLE);
            if (drawableResId > 0) {
                imgLeft.setImageResource(drawableResId);
            } else {
                imgLeft.setImageResource(backImageResIdWhite);
            }
        }
    }
    public void setLeftDrawableAndFinish(int drawableResId) {
        if (imgLeft != null) {
            txtLeft.setVisibility(View.INVISIBLE);
            imgLeft.setVisibility(View.VISIBLE);
            if (drawableResId > 0) {
                imgLeft.setImageResource(drawableResId);
            } else {
                imgLeft.setImageResource(backImageResIdWhite);
            }
            imgLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(activity!=null){
                        activity.finish();
                    }
                }
            });
        }
    }

    /**
     * 设置左边文字
     */
    public void setLeftTextAndFinish(String leftText) {
        if (txtLeft != null && !TextUtils.isEmpty(leftText)) {
            txtLeft.setVisibility(View.VISIBLE);
            imgLeft.setVisibility(View.INVISIBLE);
            txtLeft.setText(leftText);
            txtLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(activity!=null){
                        activity.finish();
                    }
                }
            });
        }
    }



}
