package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
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
import android.widget.Toast;

import com.aakash.mycambriancourses.adapters.AllCoursesRecyclerViewAdapter;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
TextView allCoursesButton,profilenameTextView, noticeTextView;
ImageView profileImage,profileImageNavigationdrawerImageView;
    private RecyclerView popularCoursesRecyclerView,allCoursesRecyclerView;
    PopularCoursesRecyclerViewAdapter adapter;
    AllCoursesRecyclerViewAdapter allCoursesAdapter;
    DatabaseReference mbase, allCoursesDatabaseRef;


    public ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView menuImageViewButton;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        noticeTextView = findViewById(R.id.noticeTextView);
        menuImageViewButton = findViewById(R.id.menuButton);
        profileImage = findViewById(R.id.profileImageToolbar);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);

        if(GlobalData.showAdminOptions == true){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.main_menu_admin);
        }
        else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.main_menu);
        }
//        navigationView.setNavigationItemSelectedListener(this);

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
                        noticeTextView.setText(snapshot.child("Notice").child("notice").getValue().toString());
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


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));

            }
        });

        mbase = FirebaseDatabase.getInstance().getReference().child("Popularcourses");

        popularCoursesRecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager popularcoursesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularCoursesRecyclerView.setLayoutManager(popularcoursesLayoutManager);
        //popularCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        FirebaseRecyclerOptions<AllCourses> options
                = new FirebaseRecyclerOptions.Builder<AllCourses>()
                .setQuery(mbase, AllCourses.class)
                .build();

        adapter = new PopularCoursesRecyclerViewAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        popularCoursesRecyclerView.setAdapter(adapter);

        
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.all_courses:
                startActivity(new Intent(MainActivity.this,CoursesListActivity.class));
                break;
            case R.id.profile_page:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                break;
            case R.id.register_page:
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                break;
            case R.id.about_us:
                startActivity(new Intent(MainActivity.this,AboutUs.class));
                break;
            default:
                break;
        }

        drawerLayout.close();

        return true;
    }

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
    public void onBackPressed() {
        //super.onBackPressed();
        //this.finish();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit Alert");
        alertDialog.setIcon(R.drawable.profile_icon);

        alertDialog.setMessage("Do you really want to exit the App?");
        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
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