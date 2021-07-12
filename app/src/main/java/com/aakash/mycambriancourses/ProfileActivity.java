package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.res.Configuration.UI_MODE_NIGHT_MASK;

public class ProfileActivity extends AppCompatActivity {
    Button btnLogOut,removeCoursesButton,themechangeButton;
    FirebaseAuth firebaseAuth;
    TextView nameTextView,emailTextView,myCoursesTextView,toolBarTitle;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        toolBarTitle = findViewById(R.id.toolbarText);
        toolBarTitle.setText("Profile");
        removeCoursesButton = findViewById(R.id.removeCoursesButton);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        emailTextView = findViewById(R.id.emailTextView);
        myCoursesTextView = findViewById(R.id.myCoursesTextView);


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(I);

            }
        });

        removeCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentremovecourses = new Intent(ProfileActivity.this, RemoveCoursesActivity.class);
                startActivity(intentremovecourses);
            }
        });


        emailTextView.setText(user.getEmail());
        nameTextView = findViewById(R.id.nameTextView);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).child("Profile").getValue() != null) {
                    nameTextView.setText(snapshot.child("Users").child(user.getUid()).child("Profile").child("name").toString());
                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });




        getFirebaseCourses(user, ref);



    }

    private void getFirebaseCourses(FirebaseUser user, DatabaseReference ref) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue() != null) {
                        String olddata = dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue().toString();
                        Log.e("snapmain", dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue().toString());

                    myCoursesTextView.setText(olddata);

                }else{
                    myCoursesTextView.setText("No courses Here!");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        emailTextView.setText(user.getEmail());
        nameTextView.setText(ref.child("Users").child(user.getUid()).child("Profile").child("name").toString());
        getFirebaseCourses(user, ref);
    }
}