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
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<AllCourses, RecyclerViewAdapter.personsViewholder> {

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


        holder.firstname.setText(model.getFirstname());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.imageurl);

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
        TextView firstname, lastname, age;
        ImageView imageurl;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);

            firstname
                    = itemView.findViewById(R.id.textViewName);
            imageurl = itemView.findViewById(R.id.imageViewurl);
//            lastname = itemView.findViewById(R.id.lastname);
//            age = itemView.findViewById(R.id.age);
        }
    }
}