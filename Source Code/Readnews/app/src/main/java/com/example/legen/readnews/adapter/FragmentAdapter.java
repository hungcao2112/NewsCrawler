package com.example.legen.readnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.legen.readnews.fragment.FragmentCongNghe;
import com.example.legen.readnews.fragment.FragmentDuLich;
import com.example.legen.readnews.fragment.FragmentGiaiTri;
import com.example.legen.readnews.fragment.FragmentGiaoDuc;
import com.example.legen.readnews.fragment.FragmentGoiY;
import com.example.legen.readnews.fragment.FragmentSucKhoe;
import com.example.legen.readnews.fragment.FragmentTheGioi;
import com.example.legen.readnews.fragment.FragmentTheThao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by legen on 3/23/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter{
    List<Fragment> fragments = new ArrayList<>();
    List<String> title = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new FragmentGoiY());
        fragments.add(new FragmentTheGioi());
        fragments.add(new FragmentTheThao());
        fragments.add(new FragmentCongNghe());
        fragments.add(new FragmentGiaiTri());
        fragments.add(new FragmentGiaoDuc());
        fragments.add(new FragmentSucKhoe());
        fragments.add(new FragmentDuLich());


        title.add("Gợi Ý");
        title.add("Thế giới");
        title.add("Thể thao");
        title.add("Công nghệ");
        title.add("Giải trí");
        title.add("Giáo Dục");
        title.add("Sức khỏe");
        title.add("Du lịch");
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
        return title.get(position);
    }
}