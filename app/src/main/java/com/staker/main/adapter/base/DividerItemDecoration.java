package com.staker.main.adapter.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Staker on 2017/7/5.
 * 这是给RecyclerView使用的分割线类，很方便。
 * 直接调用构造方法，设置图片的id即可，也可以是xml定义的shape图形
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int mOrientation;

    private int dividerHeight=-1;

    /**
     * 使用系统默认的 分割线，淡黑色
     * @param context
     * @param orientation  布局横向还是垂直
     */
    public DividerItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }
    public DividerItemDecoration(Context context, int orientation, int drawableResId) {
        mDivider = context.getResources().getDrawable(drawableResId);
        setOrientation(orientation);
    }
    public DividerItemDecoration(Context context, int orientation, Drawable drawable) {
        mDivider = drawable;
        setOrientation(orientation);
    }
    public void setDivider(Drawable drawable, int height){
        mDivider = drawable;
        if(height>=0){
            this.dividerHeight=height;
        }
    }
    public void setDivider(Context context, int drawableResId, int height){
        mDivider = context.getResources().getDrawable(drawableResId);
        if(height>=0){
            this.dividerHeight=height;
        }
    }

    /**
     * 由于一些shape图形都是没有设置size大小的，当没设置size大小的时候，
     * 直接加载它的xml，并不会在RecyclerView的分割线中显示，
     * 因为mDivider.getIntrinsicHeight()读取到的高度为0；
     * 只有调用下面这个方法，设置高度才会有效果
     * @param height
     */
    public void setDividerHeight(int height){
    this.dividerHeight=height;
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView v = new RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            if(dividerHeight<0){
                dividerHeight=mDivider.getIntrinsicHeight();
            }
            final int bottom = top+dividerHeight;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            if(dividerHeight<=0){
                dividerHeight=mDivider.getIntrinsicHeight();
            }
            final int right = left + dividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
