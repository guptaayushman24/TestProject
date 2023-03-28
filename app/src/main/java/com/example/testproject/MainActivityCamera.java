package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityCamera extends AppCompatActivity {
Button camera;
Button landmark;

Button detect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera);



        camera = findViewById(R.id.camera);
        landmark = findViewById(R.id.landmark);





    }
}