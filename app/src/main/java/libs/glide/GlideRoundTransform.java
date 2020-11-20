package libs.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by XJ on 2016/2/18.<br>
 * ${Desc}<br>
 * ${Todo}
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;
    private boolean hasBottom;

    public GlideRoundTransform(Context context) {
        this(context, dp2px(4));
    }

    public GlideRoundTransform(Context context, float px) {
        super(context);
        this.radius =px;
        hasBottom = true;
    }

    /**
     * @param context
     * @param px
     * @param hasBottom 底部是否进行圆角处理
     */
    public GlideRoundTransform(Context context, float px, boolean hasBottom) {
        super(context);
        this.radius =px;
        this.hasBottom = hasBottom;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if(hasBottom){
            return roundCrop(pool, toTransform);
        }else{
            return topRoundCrop(pool, toTransform);
        }
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        return result;
    }

    /**
     * 只对顶部进行圆角处理
     * @param pool
     * @param source
     * @return
     */
    private static Bitmap topRoundCrop(BitmapPool pool, Bitmap source){
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        RectF rectF1 = new RectF(0f, radius, source.getWidth(), source.getHeight());
        canvas.drawRect(rectF1, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
    public static float dp2px(int dp)
    {
        return Resources.getSystem().getDisplayMetrics().density * dp;
    }
}