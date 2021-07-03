package com.aakash.mycambriancourses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    Button btnLogOut;
    FirebaseAuth firebaseAuth;
    TextView profileName;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        profileName = findViewById(R.id.name);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(I);

            }
        });

        fb = FirebaseAuth.getInstance().getCurrentUser();
        profileName.setText(fb.getEmail());

    }
}