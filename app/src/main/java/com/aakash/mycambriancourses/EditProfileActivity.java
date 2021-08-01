package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {
TextView toolBarTitle;
EditText nameEditText, emailEditText, studentIdEditText, mobileEditText, birthdateEditText;
Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        toolBarTitle = findViewById(R.id.toolbarText);
        toolBarTitle.setText("Edit Profile");

        nameEditText = findViewById(R.id.nameEditTextView);
        emailEditText = findViewById(R.id.emailEditTextView);
        studentIdEditText = findViewById(R.id.studentidEditTextView);
        mobileEditText = findViewById(R.id.mobileEditTextView);
        birthdateEditText = findViewById(R.id.birthdateEditText);

        submitButton = findViewById(R.id.saveProfileButton);

        if(user.getEmail() != null){
            try{
            emailEditText.setText(user.getEmail());
        }
            catch (Exception e){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            emailEditText.setText("Loading..");
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).child("name").getValue() != null) {
                    nameEditText.setText(snapshot.child("Users").child(user.getUid()).child("name").getValue().toString());
                    studentIdEditText.setText(snapshot.child("Users").child(user.getUid()).child("studentid").getValue().toString());
                    mobileEditText.setText(snapshot.child("Users").child(user.getUid()).child("mobilenumber").getValue().toString());
                    birthdateEditText.setText(snapshot.child("Users").child(user.getUid()).child("birthdate").getValue().toString());
                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailID = emailEditText.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            }
        });
    }
}