package libs.glide;

/**
 * Created by Staker on 2017/7/26.
 * 监听图片下载过程的一个抽象类
 */

public abstract  class ImageLoadLisetner {
    public  void onLoadStart(){}
    public  abstract void onLoadFaild();
    public  abstract  void onLoadSuccess();
}
