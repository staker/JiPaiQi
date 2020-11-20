/**
 * 
 */
package com.staker.main.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.staker.main.application.Configuration;
import com.staker.main.constant.Constants;
import com.staker.main.logic.CardValueUtil;
import com.staker.main.util.BroadcastUtil;
import com.staker.main.util.DateUtil;
import com.staker.main.util.Logg;
import com.staker.main.view.util.ToastUtil;
import com.staker.master.R;

import java.util.ArrayList;

import libs.glide.GlideUtil;




/**
 * @author 小木桩 14-07-24
 */
public class CardResultView extends LinearLayout implements View.OnClickListener {

	private Button btnReset,btnBack,btnCount;
	private Context context;
	private LinearLayout layoutWrap;
	private ArrayList<String> listCardValue;
	private String lastAddValue="";
	Drawable drawableForeground;
	boolean  isFirstAddCard=true;
	public CardResultView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		listCardValue=new ArrayList<String>();
		init();
	}

	public CardResultView(Context context) {
		this(context, null);
		init();
	}

	private void init() {
		BroadcastUtil.registerReceiver1(context,cardValueReceiver, Constants.Action_Card_Value);
		LayoutInflater.from(context).inflate(R.layout.layout_card_result, this, true);
		layoutWrap=findViewById(R.id.layout_wrap);
		btnReset=findViewById(R.id.btn_reset);
		btnBack=findViewById(R.id.btn_back);
		btnReset.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btnCount=findViewById(R.id.btn_count);
		drawableForeground=context.getDrawable(R.drawable.selector_foreground_5);
	}


	public void refreshCardValue() {
		layoutWrap.removeAllViews();
		isFirstAddCard=true;
		ArrayList<String> listTemp2= CardValueUtil.getListByValue("2",listCardValue);
		addCardList("2",listTemp2);
		ArrayList<String> listTemp1= CardValueUtil.getListByValue("1",listCardValue);
		addCardList("1",listTemp1);

		for(int i=15;i>2;i--){
			ArrayList<String> listTemp= CardValueUtil.getListByValue(i+"",listCardValue);
			addCardList(i+"",listTemp);
		}
		int size=listCardValue.size();
		btnCount.setText("牌张:"+size);
		invalidate();
	}

	private void addCardList(String cardValue,ArrayList<String> list){
		int size=list.size();
		if(size==0){
			return;
		}
		switch (size){
			case 1:
				addCard(cardValue,CardValueUtil.getHeiImgIdByValue(cardValue));
				break;
			case 2:
				addCard(cardValue,CardValueUtil.getHeiImgIdByValue(cardValue));
				addCard(cardValue,CardValueUtil.getHongImgIdByValue(cardValue));
				break;
			case 3:
				addCard(cardValue,CardValueUtil.getHeiImgIdByValue(cardValue));
				addCard(cardValue,CardValueUtil.getHongImgIdByValue(cardValue));
				addCard(cardValue,CardValueUtil.getMeiImgIdByValue(cardValue));
				break;
			case 4:
				addCard(cardValue,CardValueUtil.getHeiImgIdByValue(cardValue));
				addCard(cardValue,CardValueUtil.getHongImgIdByValue(cardValue));
				addCard(cardValue,CardValueUtil.getMeiImgIdByValue(cardValue));
				addCard(cardValue,CardValueUtil.getFangImgIdByValue(cardValue));
				break;
		}
	}

	private void addCard(String cardValue,int imgId) {
		Logg.e("add  card value="+cardValue);
		final ImageView imageView=new ImageView(context);
		imageView.setBackgroundResource(imgId);
		imageView.setForeground(drawableForeground);
		imageView.setTag(cardValue);
		LayoutParams p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		p.width=140;
//		p.height=200;
		if(isFirstAddCard){
			p.leftMargin = 0;
		}else {
			p.leftMargin = -162;
		}
		isFirstAddCard=false;
		imageView.setLayoutParams(p);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				removeCard((String)imageView.getTag());
				Logg.e("移除卡片="+imageView.getTag());
			}
		});
		layoutWrap.addView(imageView);
	}


	private void removeCard( String cardValue){
		if(TextUtils.isEmpty(cardValue)){
			return;
		}
		ArrayList<String> listTemp=CardValueUtil.getListByValue(cardValue,listCardValue);

		int size=listTemp.size();
		Logg.e(cardValue +"size="+size);
		if(size==0){
			return;
		}
		switch (size){
			case 1:
				listCardValue.remove(cardValue);
				refreshCardValue();
				break;
			case 2:
				listCardValue.remove(cardValue);
				refreshCardValue();
				break;
			case 3:
				listCardValue.remove(cardValue);
				refreshCardValue();
				break;
			case 4:
				listCardValue.remove(cardValue);
				refreshCardValue();
				break;
		}

	}

	private void reset(){
		String endDate= Configuration.getInstance().getEndDate();
        if(DateUtil.getNowStringDate("-").compareTo(endDate)>0){
            ToastUtil.showToast("请联系管理员申请激活码");
            return ;
        }
		isFirstAddCard=true;
		listCardValue.clear();
		refreshCardValue();
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_back:
				removeCard(lastAddValue);
				lastAddValue="";
			break;
			case R.id.btn_reset:
				reset();
				break;
		}
	}

	BroadcastReceiver cardValueReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String cardValue=intent.getStringExtra("data01");
			Logg.e("收到增加卡牌的广播="+cardValue);
			ArrayList<String> list=CardValueUtil.getListByValue(cardValue,listCardValue);
			int size=list.size();
			if(size<4){
				lastAddValue=cardValue;
				listCardValue.add(cardValue);
				refreshCardValue();
			}
		}
	};

	@Override
	protected void onDetachedFromWindow() {
		context.unregisterReceiver(cardValueReceiver);
		super.onDetachedFromWindow();
	}
}
