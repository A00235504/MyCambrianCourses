package com.aakash.mycambriancourses.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.mycambriancourses.CourseViewActivity;
import com.aakash.mycambriancourses.R;
import com.aakash.mycambriancourses.model.AllCourses;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllCoursesViewListAdapter extends FirebaseRecyclerAdapter<AllCourses, AllCoursesViewListAdapter.personsViewholder> {
    List<String> myListData;

    public AllCoursesViewListAdapter(
            @NonNull FirebaseRecyclerOptions<AllCourses> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull personsViewholder holder,
                     int position, @NonNull AllCourses model)
    {

        myListData = new ArrayList<>();
        holder.name.setText(model.getcoursename());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.imageurl);


        holder.imageurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();

                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot != null) {
                            String name = dataSnapshot.child(model.getcoursename()).getKey();

                            Intent i = new Intent(v.getContext(), CourseViewActivity.class);

                            i.putExtra("Title", dataSnapshot.child("Courses").child(name).child("coursename").getValue().toString());
                            i.putExtra("Description", dataSnapshot.child("Courses").child(name).child("coursedescription").getValue().toString());
                            i.putExtra("Imagelink", dataSnapshot.child("Courses").child(name).child("image").getValue().toString());
                            i.putExtra("Opportunity", dataSnapshot.child("Courses").child(name).child("opportunity").getValue().toString());

                            v.getContext().startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                }
        });

        holder.addCourseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue() != null) {
                            if (dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue().toString().contains(model.getcoursename())) {
                                Toast.makeText(v.getContext(), "Course Already added!", Toast.LENGTH_SHORT).show();
                            } else {
                                myListData.clear();
                                for(int i=0;i<dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getChildrenCount();i++){
                                    myListData.add(dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").child(String.valueOf(i)).getValue(String.class));
                                }
                                myListData.add(model.getcoursename().toString());
                                ref.child("Users").child(user.getUid()).child("MyCourses").setValue(myListData);
                                Toast.makeText(v.getContext(), "Course added Success!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            myListData.add(model.getcoursename().toString());

                            ref.child("Users").child(user.getUid()).child("MyCourses").setValue(myListData);
                            }
                        }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }


    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new AllCoursesViewListAdapter.personsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView name, addCourseTextView, age;
        ImageView imageurl;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName);
            imageurl = itemView.findViewById(R.id.imageViewurl);
            addCourseTextView = itemView.findViewById(R.id.addCourseTextView);
        }
    }
}