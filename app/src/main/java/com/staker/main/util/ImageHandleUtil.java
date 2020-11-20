package com.staker.main.util;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 
 * @author 小木桩（staker）
 * 这是一个图片处理的类，包含压缩图片，加阴影，圆角等等静态方法
 *
 */
public class ImageHandleUtil {
	/**
	 *  截取当前的屏幕，返回一张bitmap图片
	 * @param activity
	 * @return Bitmap
	 */
	public static Bitmap printScreen(Activity activity){
		View view1 = activity.getWindow().getDecorView();
		Display display = activity.getWindowManager().getDefaultDisplay();
		view1.layout(0, 0, display.getWidth(), display.getHeight());//可以指定截屏的大小，不包括状态栏
		view1.setDrawingCacheEnabled(true);
		//Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());//这里仅仅只是复制一个新的bitmap
		Bitmap bitmap = view1.getDrawingCache();//这样就够了
		return bitmap;
	}
	
	
	
	/**
	 * 缩放一张图片到指定高度和宽度,newWidth新的宽度，newHeight新的高度 scale a bitmap to a new Width
	 * and Height（图片的高宽完全由用户决定，这里不进行控制scaleBitmapWidth方法则会加以控制）
	 * 如果图片的高宽度比需要压缩的高宽度小的话，则不进行缩放
	 * @param newWidth
	 *            新的宽度
	 * @param newHeight
	 *            新的高度
	 **/
	public static Bitmap scaleBitmap(Bitmap bitmap, double newWidth,
                                     double newHeight) {
		if(bitmap==null){
			return null;
		}
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		
		if(width<newWidth||height<newHeight){
			return bitmap;
		}
		
		Matrix matrix = new Matrix();
		// 缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 设置缩放
		matrix.postScale(scaleWidth, scaleHeight);
		// 缩放方法
		Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, (int) width,
				(int) height, matrix, true);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		System.gc();
		return bitmap1;
	}
	/**
	 * 缩放图片，根据宽度进行缩放，缩放之后图片不会变形
	 * @param bitmap
	 * @param newWidth  需要缩放的图片宽度，高度根据宽度来变，这样就不会把图片变形
	 * @return Bitmap
	 */
	public static Bitmap scaleBitmapWidth(Bitmap bitmap, float newWidth){
		if(bitmap==null){
			return null;
		}
		float width = bitmap.getWidth();	
		if(width<newWidth){
			return bitmap;
		}
		
		float height = bitmap.getHeight();
		float newHeight=newWidth*height/width;
		
		Matrix matrix = new Matrix();
		// 缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 设置缩放
		matrix.postScale(scaleWidth, scaleHeight);
		// 缩放方法
		Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, (int) width,
				(int) height, matrix, true);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		System.gc();
		return bitmap1;
	}
	/**
	 * 缩放图片，根据高度进行缩放，缩放之后图片不会变形
	 * @param bitmap
	 * @param newHeight  需要缩放的图片高度，宽度根据高度来变，这样就不会把图片变形
	 * @return Bitmap
	 */
	public static Bitmap scaleBitmapHeight(Bitmap bitmap, float newHeight){
		if(bitmap==null){
			return null;
		}	
		float height = bitmap.getHeight();
		if(height<newHeight){
			return bitmap;
		}
		float width = bitmap.getWidth();
		float newWidth=newHeight*width/height;
		
		Matrix matrix = new Matrix();
		// 缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 设置缩放
		matrix.postScale(scaleWidth, scaleHeight);
		// 缩放方法
		Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, (int) width,
				(int) height, matrix, true);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		System.gc();
		return bitmap1;
	}
	/**
	* 获取压缩后的图片
	* @param res
	* @param resId
	* @param reqWidth            所需图片压缩尺寸最小宽度
	* @param reqHeight           所需图片压缩尺寸最小高度
	* @return
	*/
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
	   
	    // 首先不加载图片,仅获取图片尺寸
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    // 当inJustDecodeBounds设为true时,不会加载图片仅获取图片尺寸信息
	    options.inJustDecodeBounds = true;
	    // 此时仅会将图片信息会保存至options对象内,decode方法不会返回bitmap对象
	    BitmapFactory.decodeResource(res, resId, options);

	    // 计算压缩比例,如inSampleSize=4时,图片会压缩成原图的1/4
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // 当inJustDecodeBounds设为false时,BitmapFactory.decode...就会返回图片对象了
	    options. inJustDecodeBounds = false;
	    // 利用计算的比例值获取压缩后的图片对象
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	/**
	* 计算压缩比例值
	* @param options       解析图片的配置信息
	* @param reqWidth            所需图片压缩尺寸最小宽度
	* @param reqHeight           所需图片压缩尺寸最小高度
	* @return
	*/
	public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
	       // 保存图片原宽高值
	       final int height = options. outHeight;
	       final int width = options. outWidth;
	       // 初始化压缩比例为1
	       int inSampleSize = 1;

	       // 当图片宽高值任何一个大于所需压缩图片宽高值时,进入循环计算系统
	       if (height > reqHeight || width > reqWidth) {

	             final int halfHeight = height / 2;
	             final int halfWidth = width / 2;

	             // 压缩比例值每次循环两倍增加,
	             // 直到原图宽高值的一半除以压缩值后都~大于所需宽高值为止
	             while ((halfHeight / inSampleSize) >= reqHeight
	                        && (halfWidth / inSampleSize) >= reqWidth) {
	                  inSampleSize *= 2;
	            }
	      }

	       return inSampleSize;
	}
	/**
	 * 将一张Bitmap图片转化成一张带有倒影的Bitmap图片 give a reflection to the bitmap
	 **/
	public static Bitmap getReflectionBitmap(Bitmap bitmap) {
		final int reflectionGap = 4;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
				h / 2, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);
		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in28
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient30
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}

	/**
	 * 将一张Drawable图片转化成一张Bitmap图片 change a Drawable to a Bitmap
	 **/
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 * Bitmap → byte[]  bitmap图片转化成byte数组
	 * @param bm
	 * @return   byte[]
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		if(bm==null){
    		return null;
    	}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
	/**
	 * byte[] → Bitmap
	 * @param b
	 * @return Bitmap
	 */
	public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
        	InputStream input = new ByteArrayInputStream(b);
//        	BitmapFactory.Options options=new BitmapFactory.Options();
//        	options.inSampleSize = 1;
            return BitmapFactory.decodeStream(input, null, null);
       //     return BitmapFactory.decodeByteArray(b, 0, b.length); 这个方法非常的消耗性能
        } else {
            return null;
        }
    }
	  /**
     * 计算一张Bitmap的kb数大小（即文件的大小）  比较准确
     * @param bitmap
     * @return float
     */
    public static float getSizeOfBitmap(Bitmap bitmap){
    	if(bitmap==null){
    		return 0;
    	}
    	return Bitmap2Bytes(bitmap).length/1024f;
    }
    
	/**
	 * 此方法用于上传图片到服务器是最适合不过的，能过循序渐进的对图片进行压缩到指定的大小   单位kb
	 * 
	 * 把bitmap按照图片的质量  进行压缩到指定的kb大小，
		注意，bitmap的大小是指读出到内存之后的大小，并不是说将它保存在文件中也是这么大
	 * @param bitmap  需要压缩的图片
	 * @param size    图片压缩之后的kb大小
	 * @return byte[]   压缩之后的图片byte[]数组
	 */
	public static byte[] compressBitmap(Bitmap bitmap, float size){
		if(bitmap==null){
			return null;
		}
				
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
		byte[] b=baos.toByteArray();
		if(b.length/1024f<=size){
			return b;//如果图片本身的大小已经小于这个大小了，就没必要进行压缩
		}
		
		int quality=100;
		while (baos.toByteArray().length / 1024f>size) {
			quality=quality-4;// 每次都减少5
			baos.reset();// 重置baos即清空baos
			if(quality<=0){
				break;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			Logg.e("------质量--------"+baos.toByteArray().length/1024f);		
		}
		return baos.toByteArray();
	}
	/**
	 * 压缩图片到接近指定大小返回
	 * @param picPath  图片文件的保存路径
	 * @param size  图片压缩之后的kb大小
	 * @return  byte[]   压缩之后的图片byte[]数组
	 */
	public static byte[] compressBitmap(String picPath, float size){
		if(picPath==null){
			return null;
		}
		Bitmap bitmap= BitmapFactoryUtil.readBmpFromPath(picPath, 200, 200);
		return compressBitmap(bitmap, size);
	}
	
	
//	/**
//	 * 不怎么好用，此外需要导入包，也不能压缩到指定的大小
//	 * 把原始的图片文件压缩   根据quality  
//	 * @param picPath  原始图片的绝对路径
//	 * @param quality  0-1之间，  1为不压缩
//	 * @return String
//	 */
//	private static String compressBitmap(String picPath,float quality){
//		String filePath=null;
//		try {
//			String srcPath =picPath;  
//	        String destPath = Environment.getExternalStorageDirectory().getPath()+"//upbaa";;  
//	  
//	        Iterator iter = javax.imageio.ImageIO.getImageWritersByFormatName("jpeg");  
//	  
//	        ImageWriter writer = (ImageWriter)iter.next();  
//	  
//	        ImageWriteParam iwp = writer.getDefaultWriteParam();  
//	  
//	        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);  
//	  
//	        iwp.setCompressionQuality(quality);  
//	     //   /temp_head.jpg
//	        File fileDirectory = new File(destPath);
//			if(!fileDirectory.exists()){
//				if(fileDirectory.mkdir()){
//					Logg.e("文件目录创建创建成功="+fileDirectory.getAbsolutePath());
//				}else{
//					return null;
//				}				
//			}
//			File file = new File(fileDirectory.getAbsolutePath(), "temp_head.jpg");
//	        FileImageOutputStream output = new FileImageOutputStream(file);  
//	        writer.setOutput(output);  
//	  
//	        FileInputStream inputStream = new FileInputStream(srcPath);  
//	        BufferedImage originalImage = ImageIO.read(inputStream);  
//	  
//	        IIOImage image = new IIOImage(originalImage, null, null);  
//	        writer.write(null, image, iwp);  
//	        writer.dispose();  
//	        filePath=file.getAbsolutePath();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}	
//		return filePath;
//	}
	
	
	
	/**
	 * 将一张Bitmap图片转化成一张Drawable图片 change a Bitmap to a Drawable
	 **/
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * 将一张Bitmap图片转成带有圆角的Bitmap，roundPx是传进来的弧度参数,
	 * 
	 * @param roundPx
	 *            弧度值 当roundPx >= 270的时候，整张图片变成一个椭圆形。当roundPx <= 0的时候，图片将不产生任何影响
	 **/
	public static Bitmap getBitmapWithRoundedCorner(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 对Bitmap图片进行透明度处理
	 * 
	 * @param bitmap
	 *            原始图片
	 * @param number
	 *            透明度，0为全透明，100为不透明，而且貌似还会高亮一些，若值超过100则减去100另算它的值
	 **/
	public static Bitmap setAlpha(Bitmap bitmap, int number) {
		int[] argb = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getPixels(argb, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),
				bitmap.getHeight());// 获得图片的ARGB值
		number = number * 255 / 100;

		for (int i = 0; i < argb.length; i++) {
			argb[i] = (number << 24) | (argb[i] & 0x00ffffff);// 修改最高2位的值
		}
		bitmap = Bitmap.createBitmap(argb, bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		return bitmap;
	}

	/**
	 * 截取Bitmap图片中的一部分图片（设整张图片的宽度为100，高度为100） （x1,y1）和（x2,y2）
	 * 分别为截取图片的两个点，且（x2,y2）这个点必须在（x1,y1）点的右下方，否则无效
	 * 
	 * @param x1
	 *            第一个点的X坐标，（图片的宽度设为100）
	 * @param y1
	 *            第一个点的Y坐标，（图片的高度设为100）
	 * @param x2
	 *            第二个点的X坐标，（图片的宽度设为100）
	 * @param y2
	 *            第二个点的Y坐标，（图片的高度设为100）
	 **/
	public static Bitmap getOnePieceBitmap(Bitmap bitmap, int x1, int y1,
                                           int x2, int y2) {
		if (x1 >= x2 || y1 >= y2) {
			return bitmap;
		} else {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			int x = width * x1 / 100;
			int y = height * y1 / 100;
			int newWidth = (x2 - x1) * width / 100;
			int newHeight = (y2 - y1) * height / 100;
			return Bitmap.createBitmap(bitmap, x, y, newWidth, newHeight);
		}
	}

	/**
	 * 将一张Drawable图片变成灰色，类似QQ头像
	 * 
	 **/
	public static Drawable grayingTheDrawable(Drawable drawable) {
		drawable.mutate();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
		drawable.setColorFilter(cf);
		return drawable;

	}

	/**
	 * 这个类用于保存切图方法当中的Bitmap， 两个参数 bitmap图片和bitmap图片所在的位置index
	 **/
	public class ImagePiece {
		public int index = 0;
		public Bitmap bitmap = null;
	}

	/**
	 * 将一张Bitmap图片切割成很多块，比如拼图游戏就经常能够用到 切好的图片为从左到右，依次往下
	 * 
	 * @param xPiece
	 *            X方向被切成的块数
	 * @param yPiece
	 *            Y方向被切成的块数
	 * 
	 **/
	public ArrayList<ImagePiece> splitBitmapToPieces(Bitmap bitmap, int xPiece,
                                                     int yPiece) {

		ArrayList<ImagePiece> listPieces = new ArrayList<ImagePiece>(xPiece
				* yPiece);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int pieceWidth = width / xPiece;
		int pieceHeight = height / yPiece;
		for (int i = 0; i < yPiece; i++) {
			for (int j = 0; j < xPiece; j++) {
				ImagePiece piece = new ImagePiece();
				piece.index = j + i * xPiece;
				int xValue = j * pieceWidth;
				int yValue = i * pieceHeight;
				piece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
						pieceWidth, pieceHeight);
				listPieces.add(piece);
			}
		}
		return listPieces;
	}

	/**
	 * 在右下角生成水印图片，其实这个方法没什么意思。暂时没发现它的好处
	 * 
	 * @param bitmapScr
	 *            需要处理的原图 the bitmap object you want proecss
	 * @param bitmapWatermark
	 *            水印图片 the water mark above the src
	 * @return
	 **/
	public static Bitmap getBitmapWithWatermark(Bitmap bitmapScr,
                                                Bitmap bitmapWatermark) {
		int w = bitmapScr.getWidth();
		int h = bitmapScr.getHeight();
		int ww = bitmapWatermark.getWidth();
		int wh = bitmapWatermark.getHeight();
		// create the new blank bitmap
		Bitmap newBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newBitmap);
		// draw src into
		cv.drawBitmap(bitmapScr, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(bitmapWatermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newBitmap;
	}

	/**
	 * 把一个View的对象转换成bitmap 如果转换失败则返回null，所以利用的时候需要进行判断
	 */
	public static Bitmap getBitmapFromView(View v) {

		v.clearFocus();
		v.setPressed(false);

		// 能画缓存就返回false
		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);
		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);
		return bitmap;
	}
	/**
	 * 将bitmap图片进行旋转
	 * @param rotate
	 * 			旋转的读书，rotate>0 顺时针旋转
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int rotate){
		Matrix mMatrix = new Matrix();
		mMatrix.setRotate(rotate);
		Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), mMatrix, true);
		return bm;		
	}
	/**
	 * 将bitmap图片进行倾斜
	 * @param x
	 * 			X方向的倾斜    当x>0,图片下方向右倾斜x，当x<0，图片上方向右倾斜x
	 * @param y
	 * 			Y方向的倾斜   当y>0,图片左方向上倾斜y，当y<0，图片右方向上倾斜y
	 */
	public static Bitmap skewBitmap(Bitmap bitmap, int x, int y){
		Matrix mMatrix = new Matrix();
		mMatrix.postSkew(x, y);
		Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), mMatrix, true);
		return bm;		
	}
	/**
	 * 将bitmap图片进行旋转，X，Y轴旋转，利用Matrix配合Camera实现
	 * @param choose
	 * 			choose=0，则沿X轴旋转，choose=1，则沿Y轴旋转
	 * @param degree
	 * 			旋转的度数，可以大于0也可以小于0，只是旋转的方向不同
	 */
	public static Bitmap rotateX_Y(Bitmap bitmap, int choose, float degree){
    	Matrix mMatrix = new Matrix();


    	Camera camera = new Camera();
    	camera.save(); 
    	if(choose==0){
    		camera.rotateX(degree);
    	}else{
    		camera.rotateY(degree);
    	}
    	 
    	//camera.rotateX(50f); 
    	//camera.rotateZ(50f); 
    	camera.getMatrix(mMatrix); 
    	camera.restore(); 
    	Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
    			bitmap.getHeight(), mMatrix, true); 
		return bm;
    	
    }
    /** 
     * 转换图片成圆形 
     * @param bitmap 传入Bitmap对象 
     * @return  圆形Bitmap
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth(); 
            int height = bitmap.getHeight(); 
            float roundPx; 
            float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom; 
            if (width <= height) { 
                    roundPx = width / 2; 
                    top = 0; 
                    bottom = width; 
                    left = 0; 
                    right = width; 
                    height = width; 
                    dst_left = 0; 
                    dst_top = 0; 
                    dst_right = width; 
                    dst_bottom = width; 
            } else { 
                    roundPx = height / 2; 
                    float clip = (width - height) / 2; 
                    left = clip; 
                    right = width - clip; 
                    top = 0; 
                    bottom = height; 
                    width = height; 
                    dst_left = 0; 
                    dst_top = 0; 
                    dst_right = height; 
                    dst_bottom = height; 
            } 
              
            Bitmap output = Bitmap.createBitmap(width,
                            height, Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
              
            final int color = 0xff424242; 
            final Paint paint = new Paint();
            final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
            final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
            final RectF rectF = new RectF(dst);

            paint.setAntiAlias(true); 
              
            canvas.drawARGB(0, 0, 0, 0); 
            paint.setColor(color); 
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, src, dst, paint); 
            return output; 
    }
    
    
  

}
