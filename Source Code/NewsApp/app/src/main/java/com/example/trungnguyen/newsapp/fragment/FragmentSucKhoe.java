package com.example.trungnguyen.newsapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trungnguyen.newsapp.R;

/**
 * Created by Trung Nguyen on 2/21/2017.
 */
public class FragmentSucKhoe extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.ragment_congnghe, container, false);

            return rootView;
        }
}
