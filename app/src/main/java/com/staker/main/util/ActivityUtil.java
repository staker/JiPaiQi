package com.staker.main.util;



import com.staker.main.activity.BaseActivity;

import java.util.LinkedList;

/**
 * Created by XJ on 2015/12/17.<br>
 * ${Desc}<br>
 * ${Todo}
 */
public class ActivityUtil {
    private static ActivityUtil mInstance;
    protected LinkedList<BaseActivity> mActivitys;
    public static ActivityUtil getInstance()
    {
        if(mInstance==null)
        {
            synchronized (ActivityUtil.class)
            {
                if(mInstance==null)
                {
                    mInstance=new ActivityUtil();
                }
            }
        }
        return mInstance;
    }
    private ActivityUtil() {
        mActivitys= new LinkedList();
    }

    public void add(BaseActivity activity)
    {
        mActivitys.add(activity);
    }
    public void remove(BaseActivity activity)
    {
        mActivitys.remove(activity);
    }
    public void justActvitiy(Class<? extends BaseActivity> clazz)
    {

        int n=mActivitys.size();
        mActivitys.getLast().startActivity(clazz);
//        for (BaseActivity activity:mActivitys) {
//            LogUtil.i(activity.getClass().getName());
//        }
        for (int i=0;i<n;i++)
        {
            BaseActivity activity=mActivitys.get(0);
            killActivity(activity);
        }

    }
    public void killActivity(BaseActivity activity)
    {
        activity.finish();
        mActivitys.remove(activity);
        activity=null;
    }
    public  <T extends BaseActivity> T getTop()
    {
        return (T) mActivitys.getLast();
    }
    public int getCount(){
        return mActivitys==null?0:mActivitys.size();
    }

    public boolean isEmpty()
    {
        return getCount()==0;
    }
    public boolean exist(Class<? extends BaseActivity> clazz)
    {
        for (BaseActivity activity:mActivitys) {
            if(activity.getClass().getName().equals(clazz.getName()))
            {
                return true;
            }
        }
        return false;
    }
    public void exit()
    {
        int n=mActivitys.size();
        for (int i=0;i<n;i++)
        {
            killActivity(mActivitys.getFirst());
        }
    }

    public void rollBack(Class<? extends BaseActivity> clazz){
        if(!exist(clazz)){
            return;
        }
        LinkedList<BaseActivity> activities = new LinkedList<>();
        activities.addAll(mActivitys);
        boolean isfind = false;
        for(BaseActivity activity : activities){
            if(activity.getClass().getName().equals(clazz.getName())){
                isfind = true;
                continue;
            }
            if(isfind) killActivity(activity);
        }
    }
}
