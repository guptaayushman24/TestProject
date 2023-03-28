package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivitySignIn extends AppCompatActivity {
    EditText email;
    EditText passowrd;
    Button btn;
    FirebaseAuth mAuth;
    ProgressBar progressbar;
    TextView login;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_in);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        passowrd = findViewById(R.id.password);
        btn = findViewById(R.id.button);
        progressbar = findViewById(R.id.progressBar);
        login = findViewById(R.id.loginnow);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);

                String email1, passowrd1;
                email1 = email.getText().toString();
                passowrd1 = passowrd.getText().toString();
                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(MainActivitySignIn.this, "Enter the email", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(passowrd1)) {
                    Toast.makeText(MainActivitySignIn.this, "Enter the email", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email1, passowrd1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivitySignIn.this, "Account Created.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivitySignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }


        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySignIn.this,MainActivityCamera.class);
                startActivity(intent);
            }
        });

    }
}