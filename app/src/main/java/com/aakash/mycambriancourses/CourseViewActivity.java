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
TextView titleTextView, descriptionTextView;
ImageView courseImageView;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);



        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager);

//        titleTextView = (TextView)findViewById(R.id.courseTitleTextView);
//        descriptionTextView = (TextView)findViewById(R.id.courseDescriptionTextView1);
//        courseImageView = findViewById(R.id.courseViewImageView);
//
//        String title = getIntent().getStringExtra("Title");
//        String description = getIntent().getStringExtra("Description");
//        String imagelink = getIntent().getStringExtra("Imagelink");
//
//        titleTextView.setText(title);
//        descriptionTextView.setText(description);
//        Glide.with(getApplicationContext()).load(imagelink).into(courseImageView);

    }
}