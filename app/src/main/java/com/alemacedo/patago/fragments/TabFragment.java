package com.alemacedo.patago.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alemacedo.patago.MeuDia;
import com.alemacedo.patago.MinhasRotinas;
import com.alemacedo.patago.R;
import com.alemacedo.patago.Rotinas;

public class TabFragment extends Fragment {

    public static int int_items = 3 ;
    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.tab_layout, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return v;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if (position == 0) return new Rotinas();
            else if (position == 1) return new MinhasRotinas();
            else if (position == 2) return new MeuDia();
            else if (position == 3) return new MeuDia();
            else return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return getResources().getString(R.string.tab1);
                case 1 :
                    return getResources().getString(R.string.tab2);
                case 2 :
                    return getResources().getString(R.string.tab3);
            }
            return null;
        }
    }

}

