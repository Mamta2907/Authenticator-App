package com.example.signuppage;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {
  EditText mFullName,mEmail,mPassword,mPhone;
  Button mRegister;
  TextView mLoginBtn;
  FirebaseAuth fAuth;
  ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.editTextName);
        mEmail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPassword);
        mPhone = findViewById(R.id.editTextPhone);
        mRegister = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

       if(fAuth.getCurrentUser() != null){
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
           finish();
       }

        mRegister.setOnClickListener(v -> {
          String email = mEmail.getText().toString().trim();
          String password = mPassword.getText().toString().trim();

          if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required");
          }
            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is Required");
            }

            if(password.length() < 6){
                mPassword.setError("Password must be greater than 6!");
            }

            progressBar.setVisibility(View.VISIBLE);

            // register the user in firebase

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else{
                    Toast.makeText(Register.this,"Error !!",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });

    mLoginBtn.setOnClickListener(v -> {
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    });
    }
}