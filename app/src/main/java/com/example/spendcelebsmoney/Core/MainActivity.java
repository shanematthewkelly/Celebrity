package com.example.spendcelebsmoney.Core;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.spendcelebsmoney.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spendcelebsmoney.ui.tabs.SectionsPagerAdapter;


public class MainActivity extends AppCompatActivity {

    AppBarLayout appbar;
    Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = findViewById(R.id.myToolbar);

        setSupportActionBar(myToolbar);

        //init views
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);

        appbar = findViewById(R.id.appbar);


        //Instantiate a new sectionPageAdapter
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        //set the adapter to the viewpager
        viewPager.setAdapter(sectionsPagerAdapter);
        //attach the viewpager to the tabs
        tabs.setupWithViewPager(viewPager);

        //Lets' get started
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean showAppBar = true;
            int scroll = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                if (scroll == -1) {
                    scroll = appbar.getTotalScrollRange();
                }
                if (scroll + offset == 0) {
                    showAppBar = true;
                    myToolbar.setVisibility(View.VISIBLE);
                    Animation BottomToTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_to_top);
                    myToolbar.setAnimation(BottomToTop);
                }
                else if(showAppBar) {
                    myToolbar.setVisibility(View.INVISIBLE);
                    showAppBar = true;
                }
            }
        });


    }
}