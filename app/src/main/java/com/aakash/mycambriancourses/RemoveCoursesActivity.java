package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aakash.mycambriancourses.adapters.CustomAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class RemoveCoursesActivity extends AppCompatActivity {
ArrayList<String> arrayList;
ListView listview;
TextView noCoursesTextView, toolBarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_courses);

        getID();

        toolBarTitle.setText("Remove Courses");

        getFirebaseData();



}
 public void getID(){
     toolBarTitle = findViewById(R.id.toolbarText);
     noCoursesTextView = findViewById(R.id.noCourseTextView);
     listview = findViewById(R.id.mylistview);
     noCoursesTextView = findViewById(R.id.noCourseTextView);
 }

 public void getFirebaseData(){
     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

     DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("MyCourses");

     arrayList = new ArrayList<>();

     ref.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull  DataSnapshot snapshot) {
             if(snapshot.exists()){
                 noCoursesTextView.setVisibility(View.GONE);
                 arrayList.clear();
                 for(DataSnapshot dss:snapshot.getChildren()){
                     String nn = dss.getValue(String.class);
                     arrayList.add(nn);

                 }

                 StringBuilder stringBuilder = new StringBuilder();
                 for(int i=0;i<arrayList.size();i++){
                     stringBuilder.append(arrayList.get(i) + ",");
                 }
                 Log.e("dldata",stringBuilder.toString());
                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RemoveCoursesActivity.this, android.R.layout.simple_list_item_1, arrayList );
                 CustomAdapter adapter = new CustomAdapter(getApplicationContext(),  arrayList);

                 listview.setAdapter(adapter);

                 listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         Toast.makeText(RemoveCoursesActivity.this, "the position:"+position, Toast.LENGTH_SHORT).show();
                         arrayList.remove(position);
                         ref.setValue(null);
                         ref.setValue(arrayList);
                         listview.setAdapter(arrayAdapter);
                     }
                 });
             }
             else{
                 noCoursesTextView.setVisibility(View.VISIBLE);
             }

         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });

 }
}
