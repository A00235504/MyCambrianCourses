package com.aakash.mycambriancourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aakash.mycambriancourses.GlobalData.GlobalData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public EditText loginEmailId, logInpasswd;
    Button btnLogIn;

    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        loginEmailId = findViewById(R.id.loginEmail);
        logInpasswd = findViewById(R.id.loginpaswd);
        btnLogIn = findViewById(R.id.btnLogIn);

        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(LoginActivity.this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
                    if(user.getEmail().contentEquals("test@gmail.com")){
                        GlobalData.showAdminOptions = true;
                        Toast.makeText(LoginActivity.this, "Admin Login sucess", Toast.LENGTH_SHORT).show();
                        Intent I = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(I);
                    }
                    else{

                    Toast.makeText(LoginActivity.this, "User logged in "+ user, Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(I);

                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String userEmail = loginEmailId.getText().toString();
                    String userPaswd = logInpasswd.getText().toString();
                    if (userEmail.isEmpty()) {
                        loginEmailId.setError("Provide your Email first!");
                        loginEmailId.requestFocus();
                    } else if (userPaswd.isEmpty()) {
                        logInpasswd.setError("Enter Password!");
                        logInpasswd.requestFocus();
                    } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                    } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                        firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //this.finish();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit Alert");
        alertDialog.setIcon(R.drawable.profile_icon);

        alertDialog.setMessage("Do you really want to exit the App?");
        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
                return;
            } });
        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                return;
            }});
        alertDialog.show();

    }

}