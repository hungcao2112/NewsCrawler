package com.example.trungnguyen.newsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.trungnguyen.newsapp.fragment.FragmentCongNghe;
import com.example.trungnguyen.newsapp.fragment.FragmentDuLich;
import com.example.trungnguyen.newsapp.fragment.FragmentGiaiTri;
import com.example.trungnguyen.newsapp.fragment.FragmentLamDep;
import com.example.trungnguyen.newsapp.fragment.FragmentSucKhoe;
import com.example.trungnguyen.newsapp.fragment.FragmentTheGioi;
import com.example.trungnguyen.newsapp.fragment.FragmentTheThao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 2/21/2017.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{
    List<Fragment> fragments = new ArrayList<Fragment>();
    List<String> titleFragment = new ArrayList<String>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new FragmentTheGioi());
        fragments.add(new FragmentTheThao());
        fragments.add(new FragmentCongNghe());
        fragments.add(new FragmentGiaiTri());
        fragments.add(new FragmentLamDep());
        fragments.add(new FragmentSucKhoe());
        fragments.add(new FragmentDuLich());


        titleFragment.add("Thế giới");
        titleFragment.add("Thể thao");
        titleFragment.add("Công nghệ");
        titleFragment.add("Giải trí");
        titleFragment.add("Thời trang");
        titleFragment.add("Sức khỏe");
        titleFragment.add("Du lịch");
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleFragment.get(position);
    }
}
