package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivityLandmark extends AppCompatActivity {
    //ImageView ig;
    Button btn;
    String ans = " ";
    FirebaseFunctions mFunctions;
    EditText ed1;
    EditText ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landmark);
        //ig=findViewById(R.id.imageView3);
        btn=findViewById(R.id.buttonlandmark);
        ed1=findViewById(R.id.editTextTextPersonName);
        ed2=findViewById(R.id.editTextTextPersonName2);
        Intent i= getIntent();

        mFunctions = FirebaseFunctions.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ans = bundle.getString("data");
        }
        String finalAns = ans;
        Uri uri= Uri.parse(finalAns);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Convert bitmap to base64 encoded string
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                assert bitmap != null;
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                String base64encoded = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                JsonObject request = new JsonObject();

                JsonObject image = new JsonObject();
                image.add("content", new JsonPrimitive(base64encoded));
                request.add("image", image);

                JsonObject feature = new JsonObject();
                feature.add("maxResults", new JsonPrimitive(5));
                feature.add("type", new JsonPrimitive("LANDMARK_DETECTION"));
                JsonArray features = new JsonArray();
                features.add(feature);
                request.add("features", features);
                annotateImage(request.toString())
                        .addOnCompleteListener(new OnCompleteListener<JsonElement>() {
                            @Override
                            public void onComplete(@NonNull Task<JsonElement> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivityLandmark.this,"GOT LOCATION",Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivityLandmark.this,"could not found",Toast.LENGTH_LONG).show();
                                }
                                for (JsonElement label : task.getResult().getAsJsonArray().get(0).getAsJsonObject().get("landmarkAnnotations").getAsJsonArray()) {
                                    JsonObject labelObj = label.getAsJsonObject();
                                    String landmarkName = labelObj.get("description").getAsString();
                                    String entityId = labelObj.get("mid").getAsString();
                                    float score = labelObj.get("score").getAsFloat();
                                    JsonObject bounds = labelObj.get("boundingPoly").getAsJsonObject();
                                    // Multiple locations are possible, e.g., the location of the depicted
                                    // landmark and the location the picture was taken.
                                    for (JsonElement loc : labelObj.get("locations").getAsJsonArray()) {
                                        JsonObject latLng = loc.getAsJsonObject().get("latLng").getAsJsonObject();
                                        double latitude = latLng.get("latitude").getAsDouble();
                                        double longitude = latLng.get("longitude").getAsDouble();
                                        ed1.setText((int) latitude);
                                        ed1.setText((int) longitude);
                                    }
                                }
                            }

                        });





            }
        });

    }
    private Task<JsonElement> annotateImage(String obj) {

        Object String;
        return mFunctions
                .getHttpsCallable("annotateImage")
                .call(obj)
                .continueWith(new Continuation<HttpsCallableResult, JsonElement>() {
                    @Override
                    public JsonElement then(@NonNull Task<HttpsCallableResult> task) {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        return JsonParser.parseString(new Gson().toJson(task.getResult().getData()));
                    }
                });



    }
}