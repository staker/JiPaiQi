package libs.Avos;
 /**
  * 
  * @author 小木桩（staker）
  *
  */
public abstract class HttpObjectListener {
	public static final int Get_Data_Eror = 401;//获取数据失败,可能是没有网络，可能是出了异常，具体情况不得而知
	public static final int Get_Data_End = 402;//后面已经没有数据了，到了最后一页
	public static final int Get_Data_Success = 403;//成功获取数据	
	public abstract  void onSucess(Object object);//必须实现的
	 public void onException(Exception e){

	 }

	 /**
	  * 后台有数据返回，但是返回的数据有错误，比如传参不对，或者后台服务器忙碌 等等
	  * @param dataError  错误的字符串原因
	  */
	 public void onDataError(String dataError){

	 }
}
