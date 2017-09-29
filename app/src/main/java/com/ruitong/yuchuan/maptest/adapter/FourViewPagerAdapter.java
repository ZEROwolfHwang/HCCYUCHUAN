package com.ruitong.yuchuan.maptest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ruitong.yuchuan.maptest.fragment.AISFragment;
import com.ruitong.yuchuan.maptest.fragment.CPSFragment;
import com.ruitong.yuchuan.maptest.fragment.GPSFragment;
import com.ruitong.yuchuan.maptest.fragment.SETFragment;


/**
 * Created by Administrator on 2017/7/17.
 */

public class FourViewPagerAdapter extends FragmentPagerAdapter {

    private String mEnterType;

    public FourViewPagerAdapter(FragmentManager manager, String enterType) {
        super(manager);
        mEnterType = enterType;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = AISFragment.newInstance("1","2");
                break;
            case 1:
                fragment = GPSFragment.newInstance("1","2");
                break;
            case 2:
                fragment = CPSFragment.newInstance("1","2");
                break;
            case 3:
                fragment = SETFragment.newInstance("1","2");
                break;
            default:
                fragment = AISFragment.newInstance("1","2");
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "AIS列表";
            case 1:
                return "定位";
            case 2:
                return "罗盘";
            case 3:
                return "设置";

        }
        return null;
    }
}
