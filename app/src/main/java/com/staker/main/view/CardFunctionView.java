/**
 * 
 */
package com.staker.main.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.staker.main.constant.Constants;
import com.staker.main.util.BroadcastUtil;
import com.staker.main.view.util.ToastUtil;
import com.staker.master.R;

import libs.glide.GlideUtil;


/**
 * @author 小木桩 14-07-24
 */
public class CardFunctionView extends LinearLayout implements View.OnClickListener {

	private Context context;
	private ImageView imgA,imgK,imgQ,imgJ,img10,img9,img8,img7,img6,img5,img4,img3,img2;

	public CardFunctionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public CardFunctionView(Context context) {
		this(context, null);
		init();
	}

	private void init() {
		LayoutInflater.from(context).inflate(R.layout.layout_card_function, this, true);
		img2=findViewById(R.id.img_2);
		img3=findViewById(R.id.img_3);
		img4=findViewById(R.id.img_4);
		img5=findViewById(R.id.img_5);
		img6=findViewById(R.id.img_6);
		img7=findViewById(R.id.img_7);
		img8=findViewById(R.id.img_8);
		img9=findViewById(R.id.img_9);
		img10=findViewById(R.id.img_10);
		imgJ=findViewById(R.id.img_j);
		imgQ=findViewById(R.id.img_q);
		imgK=findViewById(R.id.img_k);
		imgA=findViewById(R.id.img_a);
		img2.setOnClickListener(this);
		img3.setOnClickListener(this);
		img4.setOnClickListener(this);
		img5.setOnClickListener(this);
		img6.setOnClickListener(this);
		img7.setOnClickListener(this);
		img8.setOnClickListener(this);
		img9.setOnClickListener(this);
		img10.setOnClickListener(this);
		imgJ.setOnClickListener(this);
		imgQ.setOnClickListener(this);
		imgK.setOnClickListener(this);
		imgA.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
			switch (view.getId()){
				case R.id.img_2:
					BroadcastUtil.sendBroadcast1(context,"2", Constants.Action_Card_Value);
					break;
				case R.id.img_3:
					BroadcastUtil.sendBroadcast1(context,"3", Constants.Action_Card_Value);
					break;
				case R.id.img_4:
					BroadcastUtil.sendBroadcast1(context,"4", Constants.Action_Card_Value);
					break;
				case R.id.img_5:
					BroadcastUtil.sendBroadcast1(context,"5", Constants.Action_Card_Value);
					break;
				case R.id.img_6:
					BroadcastUtil.sendBroadcast1(context,"6", Constants.Action_Card_Value);
					break;
				case R.id.img_7:
					BroadcastUtil.sendBroadcast1(context,"7", Constants.Action_Card_Value);
					break;
				case R.id.img_8:
					BroadcastUtil.sendBroadcast1(context,"8", Constants.Action_Card_Value);
					break;
				case R.id.img_9:
					BroadcastUtil.sendBroadcast1(context,"9", Constants.Action_Card_Value);
					break;
				case R.id.img_10:
					BroadcastUtil.sendBroadcast1(context,"10", Constants.Action_Card_Value);
					break;
				case R.id.img_j:
					BroadcastUtil.sendBroadcast1(context,"11", Constants.Action_Card_Value);

					break;
				case R.id.img_q:
					BroadcastUtil.sendBroadcast1(context,"12", Constants.Action_Card_Value);
					break;
				case R.id.img_k:
					BroadcastUtil.sendBroadcast1(context,"13", Constants.Action_Card_Value);
					break;
				case R.id.img_a:
					BroadcastUtil.sendBroadcast1(context,"1", Constants.Action_Card_Value);
					break;
			}
	}
}