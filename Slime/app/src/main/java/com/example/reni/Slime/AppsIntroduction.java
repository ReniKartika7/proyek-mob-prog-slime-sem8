package com.example.reni.Slime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.reni.Slime.fragment.FragmentIntro1;
import com.example.reni.Slime.fragment.FragmentIntro2;
import com.example.reni.Slime.fragment.FragmentIntro3;

public class AppsIntroduction extends AppCompatActivity {

    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_introduction);

        viewPager = findViewById(R.id.introductionPager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FragmentIntro1 tab1 = new FragmentIntro1();
                    return tab1;
                case 1:
                    FragmentIntro2 tab2 = new FragmentIntro2();
                    return tab2;
                case 2:
                    FragmentIntro3 tab3 = new FragmentIntro3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}