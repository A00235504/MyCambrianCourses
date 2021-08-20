package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class AddCourses extends AppCompatActivity {
TextView toolBarTitle;
EditText courseNameEditText, courseDescriptionEditText, courseOpportunityEditText,courseImageLinkEditText;
    Button courseAddButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

        //calling a function for all ids
        getIDs();

        //firebase database reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Courses");

        toolBarTitle.setText("Add Courses");

        courseAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.orderByChild("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Toast.makeText(getApplicationContext(), String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                        int value = Math.toIntExact(dataSnapshot.getChildrenCount() + 1);
                        String mKey = UUID.randomUUID().toString();
                        ref.child(String.valueOf(courseNameEditText.getText())).child("coursename").setValue(String.valueOf(courseNameEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("coursedescription").setValue(String.valueOf(courseDescriptionEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("image").setValue(String.valueOf(courseImageLinkEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("opportunity").setValue(String.valueOf(courseOpportunityEditText.getText()));

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

//all ids defined here
    public void getIDs(){
        toolBarTitle = findViewById(R.id.toolbarText);
        courseAddButton = findViewById(R.id.courseAddButton);
        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseDescriptionEditText = findViewById(R.id.courseDescriptionEditText);
        courseOpportunityEditText = findViewById(R.id.courseOpportunityEditText);
        courseImageLinkEditText = findViewById(R.id.courseImageLinkEditText);
    }

}