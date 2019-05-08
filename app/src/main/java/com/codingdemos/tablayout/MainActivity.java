package com.codingdemos.tablayout;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    private static final int SELECTED_COLOR = Color.GREEN;
    private static final int UNSELECTED_COLOR = Color.RED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                changeTabViewDotColor(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabViewDotColor(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void changeTabViewDotColor(TabLayout.Tab tab, boolean isSelected) {
        LayerDrawable backgroundDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.default_dot);
        GradientDrawable dotDrawable = (GradientDrawable) backgroundDrawable.findDrawableByLayerId(R.id.item_dot);
        dotDrawable.setColor(isSelected ? SELECTED_COLOR : UNSELECTED_COLOR);

        try {
            Object tabView = tab.view;
            Field field = tab.getClass().getDeclaredField("baseBackgroundDrawable");
            field.setAccessible(true);
            field.set(tabView, backgroundDrawable);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        tabLayout.invalidate();
    }
}
