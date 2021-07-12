package com.aakash.mycambriancourses;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import java.util.Objects;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<AllCourses, RecyclerViewAdapter.personsViewholder> {
    List<String> myListData;
    public RecyclerViewAdapter(
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
        holder.firstname.setText(model.getcoursename());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.imageurl);

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
                                Log.e("snapmain", "data exist");
                                Toast.makeText(v.getContext(), "Course Already added!", Toast.LENGTH_SHORT).show();
                            } else {
                                String olddata = dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue().toString();
                                //Log.e("snapmain", dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue().toString());
//                                ref.child("Users").child(user.getUid()).child("MyCourses").
//                                        setValue(String.valueOf(model.getFirstname() + " ,\n" + olddata));
                                myListData.clear();
                                for(int i=0;i<dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getChildrenCount();i++){
                                    Log.e("getdata",dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").child(String.valueOf(i)).getValue(String.class));
                                    myListData.add(dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").child(String.valueOf(i)).getValue(String.class));
                                }


                                myListData.add(model.getcoursename().toString());

                                ref.child("Users").child(user.getUid()).child("MyCourses").setValue(myListData);
                                Toast.makeText(v.getContext(), "Course added Success!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            myListData.add(model.getcoursename().toString());

                            ref.child("Users").child(user.getUid()).child("MyCourses").setValue(myListData);
//                                ref.child("Users").child(user.getUid()).child("MyCourses").
//                                        setValue(String.valueOf(model.getFirstname()));
                            }
                        }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                String datas = String.valueOf(ref.child("Users").child(user.getUid()).child("MyCourses").);
//                Log.e("datas",datas);
//                ref.child("Users").child(user.getUid()).child("MyCourses").updateChildren()
//                        setValue(String.valueOf(model.getFirstname()));

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
        return new RecyclerViewAdapter.personsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView firstname, addCourseTextView, age;
        ImageView imageurl;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);

            firstname = itemView.findViewById(R.id.textViewName);
            imageurl = itemView.findViewById(R.id.imageViewurl);
            addCourseTextView = itemView.findViewById(R.id.addCourseTextView);
//            lastname = itemView.findViewById(R.id.lastname);
//            age = itemView.findViewById(R.id.age);
        }
    }
}