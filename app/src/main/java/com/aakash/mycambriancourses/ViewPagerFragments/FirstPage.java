package com.aakash.mycambriancourses.ViewPagerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aakash.mycambriancourses.CourseViewActivity;
import com.aakash.mycambriancourses.ProfileActivity;
import com.aakash.mycambriancourses.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstPage extends Fragment {
TextView getdescription;

    public FirstPage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_page, container, false);

        getdescription = v.findViewById(R.id.getdescription);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


        try {
            String myValue = getArguments().getString("Description");
            String title = getArguments().getString("Title");
            String imagelink = getArguments().getString("Imagelink");
            getdescription.setText(myValue);
        }
        catch (Exception e){

        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).getValue() != null) {
                    try{
                        //getdescription.setText(snapshot.child("Courses").child("Course1").child("coursedescription").getValue().toString());
                        //studentIDTextView.setText(snapshot.child("Users").child(user.getUid()).child("studentid").getValue().toString());
                        //mobileTextView.setText(snapshot.child("Users").child(user.getUid()).child("mobilenumber").getValue().toString());
                        //birthdateTextView.setText(snapshot.child("Users").child(user.getUid()).child("birthdate").getValue().toString());
                        //Glide.with(ProfileActivity.this).load(snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString()).into(profileImageView);

                    }
                    catch (Exception e){
                        Log.e("error","error in profile page");
                    }
                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}