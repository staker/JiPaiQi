package com.staker.main.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 *  * 圆角ImageView  *  * @author skg  *  
 */
public class RoundImageView extends AppCompatImageView {

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RoundImageView(Context context) {
		super(context);
		init();
	}

	private final RectF roundRect = new RectF();
	private float rect_adius =400;//默认的弧度4  这里改成500，直接画圆形
	private final Paint maskPaint = new Paint();
	private final Paint zonePaint = new Paint();

	private void init() {
		maskPaint.setAntiAlias(true);
		maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

		zonePaint.setAntiAlias(true);
		zonePaint.setColor(Color.WHITE);

		float density = getResources().getDisplayMetrics().density;
		rect_adius = rect_adius * density;
	}

	public void setRectAdius(float adius) {
		rect_adius = adius;
		invalidate();
	}
	/**
	 * 设置图片是否位圆形，如果设置弧度够大，那么图片就成了圆形
	 * @param isCircle
	 */
	public void setCircleImage(boolean isCircle){
		rect_adius=500;
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int w = getWidth();
		int h = getHeight();
		roundRect.set(0, 0, w, h);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
		canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);

		canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
		try {
			super.draw(canvas);
		} catch (Exception e) {
		}
		
		canvas.restore();
	}
}