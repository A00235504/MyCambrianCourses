package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aakash.mycambriancourses.model.AllCourses;
import com.aakash.mycambriancourses.adapters.PopularCoursesRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
TextView allCoursesButton,profilenameTextView;
ImageView profileImage,profileImageNavigationdrawerImageView;
    private RecyclerView recyclerView;
    PopularCoursesRecyclerViewAdapter adapter;
    DatabaseReference mbase;
    NavigationView nav;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView menuImageViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuImageViewButton = findViewById(R.id.menuButton);
        profileImage = findViewById(R.id.profileImage);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        //TextView user = (TextView) headerView.findViewById(R.id.loginTextId);
        profileImageNavigationdrawerImageView = headerView.findViewById(R.id.img_profilenavigationheader);
        profilenameTextView = headerView.findViewById(R.id.profilenameTextView);
        menuImageViewButton.setVisibility(View.VISIBLE);
        profileImage.setVisibility(View.VISIBLE);
        menuImageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        // to make the Navigation drawer icon always appear on the action bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                try {
                    if (snapshot != null) {
                        String data = snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString();
                        Uri uri = Uri.parse(data);
                        Log.e("imagedata",data);
                        Glide.with(MainActivity.this)
                                .load(uri)
                                .into(profileImage);

                        Glide.with(MainActivity.this)
                                .load(uri)
                                .into(profileImageNavigationdrawerImageView);
                        profilenameTextView.setText(snapshot.child("Users").child(user.getUid()).child("name").getValue().toString());
                        //profileImage.setImageURI(Uri.parse(snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString()));
                    }
                } catch (Exception e) {
                    Log.e("imagedata",e.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



    allCoursesButton = findViewById(R.id.allcourses);

    allCoursesButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,CoursesListActivity.class));
        }
    });
        profileImage = findViewById(R.id.profileImage);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        mbase = FirebaseDatabase.getInstance().getReference().child("Popularcourses");

        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        FirebaseRecyclerOptions<AllCourses> options
                = new FirebaseRecyclerOptions.Builder<AllCourses>()
                .setQuery(mbase, AllCourses.class)
                .build();

        adapter = new PopularCoursesRecyclerViewAdapter(options);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //this.finish();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit Alert");
        alertDialog.setIcon(R.drawable.profile_icon);

        alertDialog.setMessage("Do you really want to exit the App?");
        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                return;
            } });
        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                return;
            }});
        alertDialog.show();

    }

}