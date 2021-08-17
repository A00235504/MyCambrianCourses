package com.aakash.mycambriancourses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.mycambriancourses.model.AllCourses;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.os.Bundle;
import android.widget.TextView;

public class CoursesListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerViewAdapter
            adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database
    TextView toolBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);

        toolBarTitle = findViewById(R.id.toolbarText);

        toolBarTitle.setText("Courses");

        mbase = FirebaseDatabase.getInstance().getReference().child("Courses");

        recyclerView = findViewById(R.id.recyclerView);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data

        FirebaseRecyclerOptions<AllCourses> options
                = new FirebaseRecyclerOptions.Builder<AllCourses>()
                .setQuery(mbase, AllCourses.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new RecyclerViewAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}
