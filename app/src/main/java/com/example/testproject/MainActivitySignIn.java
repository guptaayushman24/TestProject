package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

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
                Intent intent = new Intent(MainActivitySignIn.this,MainActivityOpenCamera.class);
                startActivity(intent);
            }
        });
        final String[] value = new String[1];
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        EditText ar = new EditText(this);
        ArrayAdapter adapter= new ArrayAdapter<String>((Context) this,R.layout.activity_main_sign_in, (List<String>) ar);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("raghav");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //ar.clear();
                for(DataSnapshot i:snapshot.getChildren()){
                    value[0] = ar.getText().toString();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (value[0].equals("Theft Detected")){
            Intent intent = new Intent(MainActivitySignIn.this,MainActivityOpenCamera.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(MainActivitySignIn.this,MainActivityEverythingFine.class);
            startActivity(intent);
        }

    }
}