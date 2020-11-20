package libs.glide;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.File;


/***
 * 异步加载图片工具类
 *
 *         可自定义参数
 *         DisplayImageOptions 总设置 Options
 *         读取时,读取失败,读取为空时的图片 OnLoadingImage,OnFailImage,OnEmptyImage
 *         是否缓存 默认是 CacheInMemory,CacheOnDisk  内存缓存和图片缓存
 *         圆角大小 默认10px setRoundPixels
 *         设置图片内存缓存最大尺寸 内存默认2*1024*1024 maxMemoryCache 本地默认50*1024*1024
 *         maxDiskCache
 *         最大线程数 默认5 maxThreadNums
 *         本地缓存路径 默认"/ImageLoader/cache" cacheDirName
 */
public class GlideUtil {


    private static GlideUtil instance;

    private static final int for_bitmap = 0;

    private static final int defaultBannerId = 0;//默认图片未加载
    private static final int defaultAvatarId = 0;//默认头像图片
    private static final int defaultImgId = 0;//默认图片



    private GlideUtil() {
    }

    /**
     * 单例模式 获取对象
     */
    public static GlideUtil getInstance() {
        if (instance == null) {
            synchronized (GlideUtil.class) {
                if (instance == null) {
                    instance = new GlideUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 带下载监听的图片加载方法
     * @param imageView
     * @param url
     * @param loadLisetner
     */
    public void setImage(ImageView imageView, String url, final ImageLoadLisetner loadLisetner) {
        if (url == null || url.isEmpty()) {
            return;
        }
        DrawableRequestBuilder requestBuilder = null;
        if (url.contains("http")) {
            requestBuilder = Glide.with(imageView.getContext()).load(url);
        } else {
            requestBuilder = Glide.with(imageView.getContext()).load(new File(url));
        }
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        requestBuilder.crossFade();
        requestBuilder.into(new GlideDrawableImageViewTarget(imageView){
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                if(loadLisetner!=null){
                    loadLisetner.onLoadSuccess();
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if(loadLisetner!=null){
                    loadLisetner.onLoadFaild();
                }
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                if(loadLisetner!=null){
                    loadLisetner.onLoadStart();
                }
            }
        });
//        requestBuilder.into(imageView);
    }

    public void setImage(ImageView iv, String url) {
        setImage(iv, url, true);
    }
    public void setImage(ImageView iv, String url, int defaultImgId) {
        setImage(iv, url, defaultImgId,true);
    }

    /**
     * 给ImageView设置图片，可以是本地地址也可以是网络地址
     *
     * @param imageView
     * @param url       图片地址
     * @param isAnim    是否带动画效果
     */
    public void setImage(ImageView imageView, String url, boolean isAnim) {
        if (url == null || url.isEmpty()) {
            return;
        }
        DrawableRequestBuilder requestBuilder = null;
        if (url.contains("http")) {
            requestBuilder = Glide.with(imageView.getContext()).load(url);
        } else {
            requestBuilder = Glide.with(imageView.getContext()).load(new File(url));
        }
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        requestBuilder.placeholder(defaultImgId);
        if (isAnim) {
            requestBuilder.crossFade();
        }
        requestBuilder.into(imageView);
    }

    /**
     * 给ImageView设置图片，可以是本地地址也可以是网络地址
     *
     * @param imageView
     * @param url             图片地址
     * @param defualtImgResId 默认显示的图片资源id
     * @param isAnim          是否带动画效果
     */
    public void setImage(ImageView imageView, String url, int defualtImgResId, boolean isAnim) {
        if (imageView==null|| TextUtils.isEmpty(url)) {
            return;
        }
        DrawableRequestBuilder drb = Glide.with(imageView.getContext()).load(url);
        if (defualtImgResId > 0) {
            drb.placeholder(defualtImgResId);
        }
        if (isAnim) {
            drb.crossFade();
        }
        drb.into(imageView);
    }


    /**
     * 给ImageView控件设置本地资源图片  默认带动画效果
     *
     * @param imageView
     * @param resId     图片的资源id
     */
    public void setImage(ImageView imageView, int resId) {
        setImage(imageView, resId, true);
    }

    /**
     * 给ImageView控件设置本地资源图片  可以手动设置是否带动画效果
     *
     * @param imageView
     * @param resId     图片的资源id
     */
    public void setImage(ImageView imageView, int resId, boolean isAnim) {
        if (resId != 0) {
            return;
        }
        DrawableRequestBuilder requestBuilder = Glide.with(imageView.getContext()).load(resId);
        requestBuilder.placeholder(defaultImgId);//设置资源加载过程中的占位Drawable。
        if (isAnim)
            requestBuilder.crossFade();
        requestBuilder.into(imageView);
    }

    public void setImageRoundFordp(ImageView iv, String url, boolean isAnim, int dpvalue) {
        if (url == null || url.isEmpty()) {
            return;
        }
        DrawableRequestBuilder drb = Glide.with(iv.getContext()).load(url).transform(new GlideRoundTransform(iv.getContext(), GlideRoundTransform.dp2px(dpvalue)));
        drb.placeholder(for_bitmap);
        if (isAnim)
            drb.crossFade();
        drb.into(iv);
    }

    public void setImageRoundFordp(ImageView iv, String url, int dpvalue) {
        setImageRoundFordp(iv, url, true, dpvalue);
    }

    public void setImageRoundForpx(ImageView iv, String url, boolean isAnim, int pxvalue) {
        if (url == null || url.isEmpty()) {
            return;
        }
        DrawableRequestBuilder drb = Glide.with(iv.getContext()).load(url).transform(new GlideRoundTransform(iv.getContext(), pxvalue));
        drb.placeholder(for_bitmap);
        if (isAnim)
            drb.crossFade();
        drb.into(iv);
    }

    /**
     * 只有顶部进行处理的圆角
     *
     * @param iv
     * @param url
     * @param defualt
     * @param pxvalue
     */
    public void setImageTopRoundFordp(ImageView iv, String url, int defualt, int pxvalue) {
        if (url == null || url.isEmpty()) {
            return;
        }
        DrawableRequestBuilder drb = Glide.with(iv.getContext()).load(url).transform(new GlideRoundTransform(iv.getContext(), pxvalue, false));
        drb.placeholder(defualt);
        if (true)
            drb.crossFade();
        drb.into(iv);
    }

    public void setImageRoundForpx(ImageView iv, String url, int pxvalue) {
        setImageRoundForpx(iv, url, true, pxvalue);
    }

    public void setImageCricle(ImageView iv, String url, boolean isAnim) {
        setImageCricle(iv, url, defaultImgId, isAnim);
    }

    public void setImageCricle(ImageView iv, String url, int defualt, boolean isAnim) {
        if (url == null || url.isEmpty()) {
            return;
        }
        DrawableRequestBuilder drb = Glide.with(iv.getContext()).load(url).transform(new GlideCircleTransform(iv.getContext()));
        drb.placeholder(defualt);
        if (isAnim)
            drb.crossFade();
        drb.into(iv);
    }

    public void setImageCricle(ImageView iv, String url) {
        setImageCricle(iv, url, false);
    }

    public void setImageCricle(ImageView iv, int resourceId) {
        setImageCricle(iv, resourceId, defaultImgId, true);
    }

    public void setImageCricle(ImageView iv, int resourceId, int defualt, boolean isAnim) {
        DrawableRequestBuilder drb = Glide.with(iv.getContext()).load(resourceId).transform(new GlideCircleTransform(iv.getContext()));
        drb.placeholder(defualt);
        if (isAnim)
            drb.crossFade();
        drb.into(iv);
    }

    public void setImageFromGif(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).asGif().into(imageView);
    }


    //	private static ImagePipelineConfig getImagePipelineConfig(Context context)
//	{
//		ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
//				.setBitmapMemoryCacheParamsSupplier(bitmapCacheParamsSupplier)
//				.setCacheKeyFactory(cacheKeyFactory)
//				.setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
//				.setExecutorSupplier(executorSupplier)
//				.setImageCacheStatsTracker(imageCacheStatsTracker)
//				.setMainDiskCacheConfig(mainDiskCacheConfig)
//				.setMemoryTrimmableRegistry(memoryTrimmableRegistry)
//				.setNetworkFetchProducer(networkFetchProducer)
//				.setPoolFactory(poolFactory)
//				.setProgressiveJpegConfig(progressiveJpegConfig)
//				.setRequestListeners(requestListeners)
//				.setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
//				.build();
//		return config;
//	}
}
/**
 * 1.Glide.with()使用
 * with(Context context). 使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制。
 * with(Activity activity).使用Activity作为上下文，Glide的请求会受到Activity生命周期控制。
 * with(FragmentActivity activity).Glide的请求会受到FragmentActivity生命周期控制。
 * with(android.app.Fragment fragment).Glide的请求会受到Fragment 生命周期控制。
 * with(android.support.v4.app.Fragment fragment).Glide的请求会受到Fragment生命周期控制。
 * 2.requestManager.load()使用
 * <p>
 * Glide基本可以load任何可以拿到的媒体资源，如：
 * load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
 * load assets资源：load("file:///android_asset/f003.gif")
 * load raw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
 * load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
 * load ContentProvider资源：load("content://media/external/images/media/139469")
 * load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
 * load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
 * 当然，load不限于String类型，还可以：
 * load(Uri uri)，load(File file)，load(Integer resourceId)，load(URL url)，load(byte[] model)，load(T model)，loadFromMediaStore(Uri uri)。
 * load的资源也可以是本地视频，如果想要load网络视频或更高级的操作可以使用VideoView等其它控件完成。
 * 而且可以使用自己的ModelLoader进行资源加载：
 * using(ModelLoader<A, T> modelLoader, Class<T> dataClass)，using(final StreamModelLoader<T> modelLoader)，using(StreamByteArrayLoader modelLoader)，using(final FileDescriptorModelLoader<T> modelLoader)。
 * 返回GenericRequestBuilder实例。
 * <p>
 * 3.requestBuilder  这是最顶级的设置类，非常重要
 * thumbnail(float sizeMultiplier). 请求给定系数的缩略图。如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示。系数sizeMultiplier必须在(0,1)之间，可以递归调用该方法。
 * sizeMultiplier(float sizeMultiplier). 在加载资源之前给Target大小设置系数。
 * diskCacheStrategy(DiskCacheStrategy strategy).设置缓存策略。DiskCacheStrategy.SOURCE：缓存原始数据，DiskCacheStrategy.RESULT：缓存变换(如缩放、裁剪等)后的资源数据，DiskCacheStrategy.NONE：什么都不缓存，DiskCacheStrategy.ALL：缓存SOURC和RESULT。默认采用DiskCacheStrategy.RESULT策略，对于download only操作要使用DiskCacheStrategy.SOURCE。
 * priority(Priority priority). 指定加载的优先级，优先级越高越优先加载，但不保证所有图片都按序加载。枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW。默认为Priority.NORMAL。
 * dontAnimate(). 移除所有的动画。
 * animate(int animationId). 在异步加载资源完成时会执行该动画。
 * animate(ViewPropertyAnimation.Animator animator). 在异步加载资源完成时会执行该动画。
 * placeholder(int resourceId). 设置资源加载过程中的占位Drawable。
 * placeholder(Drawable drawable). 设置资源加载过程中的占位Drawable。
 * fallback(int resourceId). 设置model为空时要显示的Drawable。如果没设置fallback，model为空时将显示error的Drawable，如果error的Drawable也没设置，就显示placeholder的Drawable。
 * fallback(Drawable drawable).设置model为空时显示的Drawable。
 * error(int resourceId).设置load失败时显示的Drawable。
 * error(Drawable drawable).设置load失败时显示的Drawable。
 * listener(RequestListener<? super ModelType, TranscodeType> requestListener). 监听资源加载的请求状态，可以使用两个回调：onResourceReady(R resource, T model, Target<R> target, boolean isFromMemoryCache, boolean isFirstResource)和onException(Exception e, T model, Target&lt;R&gt; target, boolean isFirstResource)，但不要每次请求都使用新的监听器，要避免不必要的内存申请，可以使用单例进行统一的异常监听和处理。
 * skipMemoryCache(boolean skip). 设置是否跳过内存缓存，但不保证一定不被缓存（比如请求已经在加载资源且没设置跳过内存缓存，这个资源就会被缓存在内存中）。
 * override(int width, int height). 重新设置Target的宽高值（单位为pixel）。
 * into(Y target).设置资源将被加载到的Target。
 * into(ImageView view). 设置资源将被加载到的ImageView。取消该ImageView之前所有的加载并释放资源。
 * into(int width, int height). 后台线程加载时要加载资源的宽高值（单位为pixel）。
 * preload(int width, int height). 预加载resource到缓存中（单位为pixel）。
 * asBitmap(). 无论资源是不是gif动画，都作为Bitmap对待。如果是gif动画会停在第一帧。
 * asGif().把资源作为GifDrawable对待。如果资源不是gif动画将会失败，会回调.error()。
 */