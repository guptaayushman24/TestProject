package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivityCamera extends AppCompatActivity {
Button camera;
Button landmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera);



        camera = findViewById(R.id.camera);
        landmark = findViewById(R.id.landmark);
    }
}