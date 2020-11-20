package com.staker.main.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


import com.staker.main.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Staker on 2017/6/30.
 * 这是一个给ViewPager用的通用型适配器，只要传入对应的 Fragment数组即可  title数组可以传可以不传
 * 如果这个适配器对应的viewpager是给TabLayout用的话，那么必须传listTitle数据，
 * 且  两个数组的  长度必须相等，否则会出异常
 *
 * 这个适配器里面的listTitle数据是配合TabLayout用的，因为里面的listTitle刚好就是TabLayout使用的title（如果这个适配器仅仅只是给普通的viewpager
 * 使用的话，那么listTitle可以传null）
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> listFragment; // 每个Fragment对应一个Page
    private ArrayList<String> listTitle;
    private FragmentManager fragmentManager;
    private ViewPager viewPager; // viewPager对象
    private int currentPageIndex = 0; // 当前page索引（切换之前）

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<String> listTitle, ArrayList<BaseFragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
        this.listTitle = listTitle;
    }


    /**
     * 其实这个方法只会执行一次，当父类拿到了这个里面的实例之后，
     * 父类里面的方法会自动保存这些实例，即不会再从下面的方法里面获取实例
     * @param position
     * @return   Fragment
     */
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }
    @Override
    public int getCount() {
        return listFragment==null?0:listFragment.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(listTitle!=null){
            return listTitle.get(position);
        }
        return "无题";
    }
}
