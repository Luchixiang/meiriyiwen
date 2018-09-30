package com.example.passage.tab;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.passage.R;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void initView()
    {
        tabLayout=(TabLayout) findViewById(R.id.tab);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        final Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("文章");
        setSupportActionBar(toolbar);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),3));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                toolbar.setTitle(FragmentGenerator.mTabTitles[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setCustomView(FragmentGenerator.getTabView(this, i)));}
    }
}
