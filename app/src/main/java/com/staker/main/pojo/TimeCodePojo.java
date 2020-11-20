package com.staker.main.pojo;

public class TimeCodePojo {

    public String objectId;//AVos里面的主键


    public String code;


    public String startTime;//
    public String endTime;//一系列的剧照url  中间用~隔开

    public int days;
    public int codeLevel;//激活码的级别  分为  1 2 3 4等  1设置1到3  2设置1到6  3设置1到20  4没有限制
    public boolean canUse;
}
