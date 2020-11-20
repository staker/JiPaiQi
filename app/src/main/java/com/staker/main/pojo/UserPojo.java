package com.staker.main.pojo;

public class UserPojo {
	public long id;//主键
	public long userId;//当前登录股票管家用户的id，(当前登录用户的id) （为多用户登录设置的）
	public String avatarUrl;//用户的头像的url（前面还要加上前缀url地址）

	public String category;//类别，  代表是虚拟组合用户还是代表真是用户   投资者：INVESTOR  组合：PORTFOLIO   操盘手：MASTER
	public String userName;//用户的账户名
	public String userPassword;//
	public String userNickname;
	public String userDesc;//用户的描述信息（属性）
	public String userToken;
	public String userObjectId;//这个是后台数据库  用户的主键
	public String phoneType; //Android   IOS
	public String deviceInfo;//
	public String registerTime;//注册时间
	public String endTime="2018-01-01";//软件到期时间



	public int userLevel;//用户等级
	public int userGold;//
    public int vedioCollectLimit;//视频收藏数量
	public int userType;//1.用户 2.群


	public long modifiedTime;




	/**
	 * device_imei
	 * device_info
	 * phone_type
	 * user_desc
	 * user_gold
	 * user_level
	 * user_nickname
	 * user_portrait_url
	 * vedio_collect_limit
	 *
	 *
	 */
}
