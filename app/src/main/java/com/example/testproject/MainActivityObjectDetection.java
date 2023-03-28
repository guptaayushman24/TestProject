package com.example.testproject;

import static com.example.testproject.R.id.button2;
import static com.example.testproject.R.id.ig;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import com.google.mlkit.vision.objects.defaults.PredefinedCategory;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class MainActivityObjectDetection extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_object_detection);
        String ans = " ";
        ImageView ig;
        Button btn;
        editText = findViewById(R.id.editText);
        btn=findViewById(R.id.button2);
        Intent i= getIntent();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ans = bundle.getString("data");
        }
        String finalAns = ans;

        Uri uri= Uri.parse(finalAns);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                ObjectDetectorOptions options =
                        new ObjectDetectorOptions.Builder()
                                .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
                                .enableClassification()  // Optional
                                .build();
                ObjectDetector objectDetector = ObjectDetection.getClient(options);
                String image =finalAns;
                Bitmap bitmap=StringtoBitmap(image);
                //image.getMediaImage();
                try {
                    image = String.valueOf(InputImage.fromFilePath(MainActivityObjectDetection.this, uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Object obj=(Object)image;

//                assert bitmap != null;
                objectDetector.process(bitmap,2)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<DetectedObject>>() {
                                    @Override
                                    public void onSuccess(List<DetectedObject> detectedObjects) {
                                        Toast.makeText(MainActivityObjectDetection.this,"SUCESSFUL",Toast.LENGTH_LONG).show();
                                        for (DetectedObject detectedObject : detectedObjects) {
                                            Rect boundingBox = detectedObject.getBoundingBox();
                                            Integer trackingId = detectedObject.getTrackingId();
                                            for (DetectedObject.Label label : detectedObject.getLabels()) {
                                                String text = label.getText();
//                                                if (PredefinedCategory..equals(text)) {
//
//                                                }
                                                int index = label.getIndex();
                                                if (PredefinedCategory.FOOD_INDEX == index) {
                                                    Toast.makeText(MainActivityObjectDetection.this,"FOOD",Toast.LENGTH_LONG).show();
                                                }
                                                if (PredefinedCategory.PLACE_INDEX == index) {
                                                    Toast.makeText(MainActivityObjectDetection.this,"PLACE",Toast.LENGTH_LONG).show();
                                                }
                                                if (PredefinedCategory.PLANT_INDEX == index) {
                                                    Toast.makeText(MainActivityObjectDetection.this,"PLANT",Toast.LENGTH_LONG).show();
                                                }editText.setText(index);
                                                if (PredefinedCategory.FASHION_GOOD_INDEX == index) {
                                                    Toast.makeText(MainActivityObjectDetection.this,"FASHION",Toast.LENGTH_LONG).show();
                                                }

                                                else{
//                                                    Intent intent= new Intent(MainActivityObjectDetection.this,alert.class);
//                                                    startActivity(intent);

                                                }
                                                float confidence = label.getConfidence();

                                            }
                                        }
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivityObjectDetection.this,"NOT SUCESSFUL",Toast.LENGTH_LONG).show();
                                    }
                                });
                // The list of detected objects contains one item if multiple
// object detection wasn't enabled.

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityObjectDetection.this,MainActivityCamera2.class);
                startActivity(intent);
            }
        });
    }

    private Bitmap StringtoBitmap(String image) {
        try{
            byte [] encodeByte = Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
}

    }

}