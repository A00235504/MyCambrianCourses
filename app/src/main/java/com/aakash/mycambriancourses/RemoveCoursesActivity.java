package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class RemoveCoursesActivity extends AppCompatActivity {
ArrayList<String> dl;
ListView ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_courses);

        ls = findViewById(R.id.mylistview);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("MyCourses");

        dl = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()){
                    dl.clear();
                    for(DataSnapshot dss:snapshot.getChildren()){
                        String nn = dss.getValue(String.class);
                        dl.add(nn);

                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i=0;i<dl.size();i++){
                        stringBuilder.append(dl.get(i) + ",");
                    }
                    Log.e("dldata",stringBuilder.toString());
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RemoveCoursesActivity.this, android.R.layout.simple_list_item_1, dl );

                    ls.setAdapter(arrayAdapter);

                    ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(RemoveCoursesActivity.this, "the position:"+position, Toast.LENGTH_SHORT).show();
                            //DatabaseReference item = ref.child("Users").child(user.getUid()).child("MyCourses").child(String.valueOf(position)) ;
                            //item.removeValue();
                            dl.remove(position);
//                            ref.child("Users").child(user.getUid()).child("MyCourses");
                            ref.setValue(null);
                            ref.setValue(dl);
                            //Toast.makeText(RemoveCoursesActivity.this, "item:"+item, Toast.LENGTH_SHORT).show();
                            //ref.child("Users").child(user.getUid()).child("MyCourses").remove();

                            ls.setAdapter(arrayAdapter);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
