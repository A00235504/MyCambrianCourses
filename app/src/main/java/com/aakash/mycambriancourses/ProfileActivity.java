package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aakash.mycambriancourses.GlobalData.GlobalData;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    Button btnLogOut,removeCoursesButton,registerUserButton, editProfileButton, addCoursesButton;
    FirebaseAuth firebaseAuth;
    TextView nameTextView,emailTextView,toolBarTitle,studentIDTextView,mobileTextView,birthdateTextView;
    private FirebaseAuth.AuthStateListener authStateListener;
    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        toolBarTitle = findViewById(R.id.toolbarText);

        removeCoursesButton = findViewById(R.id.removeCoursesButton);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        emailTextView = findViewById(R.id.emailTextView);
        studentIDTextView = findViewById(R.id.studentIDTextView);
        mobileTextView = findViewById(R.id.mobileTextView);
        birthdateTextView = findViewById(R.id.birthdateTextView);
        registerUserButton = findViewById(R.id.themechangeButton);
        editProfileButton = findViewById(R.id.editProfileButton);
        profileImageView = findViewById(R.id.profilePageImageView);
        addCoursesButton = findViewById(R.id.addCourseButton);

        if(!GlobalData.showAdminOptions) {
            toolBarTitle.setText("Profile");
            registerUserButton.setVisibility(View.GONE);
            addCoursesButton.setVisibility(View.GONE);
        }
        else{
            toolBarTitle.setText("Admin Profile");
            registerUserButton.setVisibility(View.VISIBLE);
            addCoursesButton.setVisibility(View.VISIBLE);
        }

        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ProfileActivity.this, RegisterActivity.class);
                startActivity(I);
            }
        });

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

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenteditprofile = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intenteditprofile);
            }
        });

        addCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenteditprofile = new Intent(ProfileActivity.this, AddCourses.class);
                startActivity(intenteditprofile);
            }
        });

        emailTextView.setText(user.getEmail());
        nameTextView = findViewById(R.id.nameTextView);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).getValue() != null) {
                    try{
                        nameTextView.setText(snapshot.child("Users").child(user.getUid()).child("name").getValue().toString());
                        studentIDTextView.setText(snapshot.child("Users").child(user.getUid()).child("studentid").getValue().toString());
                        mobileTextView.setText(snapshot.child("Users").child(user.getUid()).child("mobilenumber").getValue().toString());
                        birthdateTextView.setText(snapshot.child("Users").child(user.getUid()).child("birthdate").getValue().toString());
                        Glide.with(ProfileActivity.this).load(snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString()).into(profileImageView);

                    }
                    catch (Exception e){
Log.e("error","error in profile page");
                    }
                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        emailTextView.setText(user.getEmail());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).child("name").getValue() != null) {
                    try{
                        nameTextView.setText(snapshot.child("Users").child(user.getUid()).child("name").getValue().toString());
                        studentIDTextView.setText(snapshot.child("Users").child(user.getUid()).child("studentid").getValue().toString());
                        mobileTextView.setText(snapshot.child("Users").child(user.getUid()).child("mobilenumber").getValue().toString());
                        birthdateTextView.setText(snapshot.child("Users").child(user.getUid()).child("birthdate").getValue().toString());
                        Glide.with(ProfileActivity.this).load(snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString()).into(profileImageView);

                    }
                    catch (Exception e){
                        Log.e("error","error in profile page");
                    }

                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }

}