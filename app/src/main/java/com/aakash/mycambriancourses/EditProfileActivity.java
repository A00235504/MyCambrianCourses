package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    TextView toolBarTitle;
    EditText nameEditText, emailEditText, studentIdEditText, mobileEditText, birthdateEditText;
    Button submitButton;
    ImageView profileImageView;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    final Calendar myCalendar = Calendar.getInstance();

    String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //firebase storage and iinstance defined
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        getID();

        toolBarTitle.setText("Edit Profile");



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

        //setting the database with the values from edit text
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).child("name").getValue() != null) {
                    nameEditText.setText(snapshot.child("Users").child(user.getUid()).child("name").getValue().toString());
                    studentIdEditText.setText(snapshot.child("Users").child(user.getUid()).child("studentid").getValue().toString());
                    mobileEditText.setText(snapshot.child("Users").child(user.getUid()).child("mobilenumber").getValue().toString());
                    birthdateEditText.setText(snapshot.child("Users").child(user.getUid()).child("birthdate").getValue().toString());

                    if (!isDestroyed()) {
                        Glide.with(EditProfileActivity.this).load(snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString()).into(profileImageView);
                    }}
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
                try {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                    ref.child("Users").child(user.getUid()).child("name").setValue(String.valueOf(nameEditText.getText()));
                    ref.child("Users").child(user.getUid()).child("studentid").setValue(String.valueOf(studentIdEditText.getText()));
                    ref.child("Users").child(user.getUid()).child("mobilenumber").setValue(String.valueOf(mobileEditText.getText()));
                    ref.child("Users").child(user.getUid()).child("birthdate").setValue(String.valueOf(birthdateEditText.getText()));


                    StorageReference storageRef = storage.getReference();
                    String uuid = UUID.randomUUID().toString();
                    StorageReference mountainsRef = storageRef.child("images/" + uuid);

                    profileImageView.setDrawingCacheEnabled(true);
                    profileImageView.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            ref.child("Users").child(user.getUid()).child("profileimage").setValue("https://firebasestorage.googleapis.com/v0/b/courses-app-9c592.appspot.com/o/images%2Fca27f007-d38f-4e67-9314-bfe554df145c?alt=media&token=ba6057a7-4dd6-4ab8-9b0f-29bc625d5b47");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String downloadUrl = taskSnapshot.getMetadata().getPath();
                            StorageMetadata metada = taskSnapshot.getMetadata();
                            Task<Uri> down = mountainsRef.getDownloadUrl();
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ref.child("Users").child(user.getUid()).child("profileimage").setValue(uri.toString());


                                }
                            });

                        }
                    });

            }catch(
            Exception e)
            {
                e.printStackTrace();
            }
        }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        birthdateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //handle button click
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }

            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            profileImageView.setImageURI(data.getData());

            imageURL = data.getData().toString();
        }
    }

    public void getID(){
        toolBarTitle = findViewById(R.id.toolbarText);
        nameEditText = findViewById(R.id.nameEditTextView);
        emailEditText = findViewById(R.id.emailEditTextView);
        studentIdEditText = findViewById(R.id.studentidEditTextView);
        mobileEditText = findViewById(R.id.mobileEditTextView);
        birthdateEditText = findViewById(R.id.birthdateEditText);
        profileImageView = findViewById(R.id.profileImageView);

        submitButton = findViewById(R.id.saveProfileButton);
        birthdateEditText= (EditText) findViewById(R.id.birthdateEditText);
    }
}