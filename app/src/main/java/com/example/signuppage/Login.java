package com.example.signuppage;

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

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);


        mEmail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPassword);
        mCreateBtn = findViewById(R.id.createText);
        mLoginBtn = findViewById(R.id.registerBtn);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mLoginBtn.setOnClickListener(v -> {

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

           // progressBar.setVisibility(View.VISIBLE);

            //  Login the user

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(Login.this,"Error !!" + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                  //  progressBar.setVisibility(View.GONE);
                }
            });
        });

        mCreateBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),Register.class));
            finish();
        });
    }
}