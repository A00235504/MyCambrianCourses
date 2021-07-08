package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ProfileActivity extends AppCompatActivity {
    Button btnLogOut,removeCoursesButton;
    FirebaseAuth firebaseAuth;
    TextView profileName,myCoursesTextView;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        removeCoursesButton = findViewById(R.id.removeCoursesButton);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        profileName = findViewById(R.id.name);
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

        fb = FirebaseAuth.getInstance().getCurrentUser();
        profileName.setText(fb.getEmail());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

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
        profileName.setText(user.getEmail());

        getFirebaseCourses(user, ref);
    }
}