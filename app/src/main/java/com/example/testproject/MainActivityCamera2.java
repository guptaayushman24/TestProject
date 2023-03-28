package com.example.testproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.testproject.R;
import com.github.dhaval2404.imagepicker.ImagePicker;


public class MainActivityCamera2 extends AppCompatActivity {
    Button btn;
    ImageView ig;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera2);
        btn=findViewById(R.id.button);
        ig=findViewById(R.id.ig);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImagePicker.with(camera.this)
//                        .crop()
//                        .compress(1024)
//                        .maxResultSize(1080, 1080)
//                        .start();

                ImagePicker.with(MainActivityCamera2.this)
                        .cameraOnly()	//User can only capture image using Camera
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        ig.setImageURI(uri);

//        Intent intent= new Intent(MainActivityCamera2.this,objectdetection.class);
//
//        intent.putExtra("data",uri.toString());
//        startActivity(intent);
    }
}