package com.aakash.mycambriancourses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aakash.mycambriancourses.adapters.ViewPagerAdapter;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

public class CourseViewActivity extends AppCompatActivity {
TextView titleTextView, descriptionTextView, toolBarTitle;
ImageView courseImageView;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        getID();
        viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),getIntent().getStringExtra("Description"),
                getIntent().getStringExtra("Title"), getIntent().getStringExtra("Imagelink"),
                getIntent().getStringExtra("Opportunity"));

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        String title = getIntent().getStringExtra("Title");
        toolBarTitle.setText(title);


    }

    public void getID(){
        toolBarTitle = findViewById(R.id.toolbarText);
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);
    }
}