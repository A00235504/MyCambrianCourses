package com.aakash.mycambriancourses.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.mycambriancourses.CourseViewActivity;
import com.aakash.mycambriancourses.R;
import com.aakash.mycambriancourses.model.AllCourses;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AllCoursesRecyclerViewAdapter extends FirebaseRecyclerAdapter<AllCourses, AllCoursesRecyclerViewAdapter.allcoursesViewholder> {
    public AllCoursesRecyclerViewAdapter(
            @NonNull FirebaseRecyclerOptions<AllCourses> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull allcoursesViewholder holder, int position, @NonNull AllCourses model)
    {
        holder.allcoursetextTextView.setText(model.getcoursename());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.allcourseimageImageView);

        holder.allcourseimageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CourseViewActivity.class);
                v.getContext().startActivity(i);
            }
        });

    }


    @NonNull
    @Override
    public allcoursesViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allcourses_list_item, parent, false);
        return new AllCoursesRecyclerViewAdapter.allcoursesViewholder(view);
    }

    class allcoursesViewholder
            extends RecyclerView.ViewHolder {
        TextView allcoursetextTextView;
        ImageView allcourseimageImageView;
        public allcoursesViewholder(@NonNull View itemView)
        {
            super(itemView);

            allcoursetextTextView = itemView.findViewById(R.id.allcoursetextTextView);
            allcourseimageImageView = itemView.findViewById(R.id.allcourseimageImageView);
        }
    }
}