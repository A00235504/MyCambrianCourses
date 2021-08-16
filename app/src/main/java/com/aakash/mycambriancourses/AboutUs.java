package com.aakash.mycambriancourses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
TextView toolBarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        toolBarTitle = findViewById(R.id.toolbarText);

        toolBarTitle.setText("About Us");
    }
}