package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    public EditText emailId, passwd, userNameEditText,birthdateEditText,mobileEditText,profileimageEditText, studentIDEditText;
    Button btnSignUp;
    FirebaseAuth firebaseAuth;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.ETemail);
        passwd = findViewById(R.id.ETpassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        mobileEditText = findViewById(R.id.mobileNumberEditText);
        profileimageEditText = findViewById(R.id.profileImageEditText);
        studentIDEditText = findViewById(R.id.studentIDEditText);

        userNameEditText = findViewById(R.id.username);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = emailId.getText().toString();
                String paswd = passwd.getText().toString();
                if (emailID.isEmpty()) {
                    emailId.setError("Provide your Email first!");
                    emailId.requestFocus();
                } else if (paswd.isEmpty()) {
                    passwd.setError("Set your password");
                    passwd.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(
                            RegisterActivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            } else {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                                ref.child("Users").child(user.getUid()).child("name").setValue(String.valueOf(userNameEditText.getText()));
                                ref.child("Users").child(user.getUid()).child("mobilenumber").setValue(String.valueOf(mobileEditText.getText()));
                                if(profileimageEditText.getText().toString().isEmpty()){
                                    ref.child("Users").child(user.getUid()).child("profileimage").setValue("\"https://www.pearsoncollege.ca/wp-content/uploads/2019/12/placeholder-profile.jpg\"");

                                }
                                else{
                                    ref.child("Users").child(user.getUid()).child("profileimage").setValue(String.valueOf(profileimageEditText.getText()));

                                }
                                ref.child("Users").child(user.getUid()).child("studentid").setValue(String.valueOf(studentIDEditText.getText()));
                                ref.child("Users").child(user.getUid()).child("birthdate").setValue(String.valueOf(birthdateEditText.getText()));

                                Toast.makeText(RegisterActivity.this.getApplicationContext(),
                                        "Registration sucess ",
                                        Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RegisterActivity.this, MainActivity.class));


                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });




        birthdateEditText= (EditText) findViewById(R.id.birthdateEditText);
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
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdateEditText.setText(sdf.format(myCalendar.getTime()));
    }
    }
