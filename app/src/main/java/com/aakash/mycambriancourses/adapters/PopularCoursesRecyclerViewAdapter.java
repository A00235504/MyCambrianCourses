package com.aakash.mycambriancourses.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.mycambriancourses.R;
import com.aakash.mycambriancourses.model.Popularcourses;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PopularCoursesRecyclerViewAdapter extends FirebaseRecyclerAdapter<Popularcourses, PopularCoursesRecyclerViewAdapter.popularcoursesViewholder> {
    public PopularCoursesRecyclerViewAdapter(
            @NonNull FirebaseRecyclerOptions<Popularcourses> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull popularcoursesViewholder holder, int position, @NonNull Popularcourses model)
    {
        holder.coursetextTextView.setText(model.getcoursename());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.courseimageImageView);

    }


    @NonNull
    @Override
    public popularcoursesViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_courses_list_item, parent, false);
        return new PopularCoursesRecyclerViewAdapter.popularcoursesViewholder(view);
    }

    class popularcoursesViewholder
            extends RecyclerView.ViewHolder {
        TextView coursetextTextView;
        ImageView courseimageImageView;
        public popularcoursesViewholder(@NonNull View itemView)
        {
            super(itemView);

            coursetextTextView = itemView.findViewById(R.id.coursetextTextView);
            courseimageImageView = itemView.findViewById(R.id.courseimageImageView);
        }
    }
}