package com.example.fertilizer.Soilest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fertilizer.R;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String MODEL_PATH = "model2.tflite";
    private static final boolean QUANT = true;
    private static final String LABEL_PATH = "labels23.txt";
    private static final int INPUT_SIZE = 224;

    private Classifier1 classifier;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult;
    private CardView btnDetectObject;
    private ImageView imageViewResult,gallery;

    static String city,land_size,land_type,temp,hub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one);
        CardView btnToggleCamera = findViewById(R.id.btnToggleCamera);
        try {
            String type=getSharedPreferences("sharedPrefs",MODE_PRIVATE).getString("type","");

            if(Objects.equals(type, "seller")){
                btnToggleCamera.setVisibility(View.GONE);
            }






            city="Tirupati";
            land_size="20";
            land_type="20";
            temp=getIntent().getStringExtra("tempar");
            hub=getIntent().getStringExtra("humidity");

        }catch (Exception e){

        }



        ImageView capture = findViewById(R.id.capture);

        imageViewResult = findViewById(R.id.resultimage);
        textViewResult = findViewById(R.id.textViewResult);
        gallery=findViewById(R.id.gallery);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());


        btnDetectObject = findViewById(R.id.btnDetectObject);

        capture.setOnClickListener(
                f->{
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),100);
                }
        );


        try {
            initTensorFlowAndLoadModel();

        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(intent,200);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier1.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT
                    );
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
    }

    public void rec(View view) {
        if(!textViewResult.getText().toString().equals("Result")){
            Intent intent=new Intent(getApplicationContext(),Recomm.class);
            intent.putExtra("city",city.trim());
            intent.putExtra("size","29");
            intent.putExtra("temp",temp.trim());
            intent.putExtra("hum",hub.trim());
            intent.putExtra("lt",land_type.trim());
            startActivity(intent);
        }else{
            Toast.makeText(this, "Capture Image Or take from the gallery", Toast.LENGTH_SHORT).show();
        }

    }
    @SuppressLint("SetTextI18n")
    private void recognition(Bitmap bitmap){
        bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

        imageViewResult.setImageBitmap(bitmap);

        final List<Classifier1.Recognition> results = classifier.recognizeImage(bitmap);
        try {

            String f=results.get(0).toString();
            textViewResult.setText(f);

            if(f.contains("Alluvial ")){
                textViewResult.append("\n" +
                        "Alluvial soils, rich in minerals" +
                        " and nutrients, are ideal for various crops." +
                        " Recommended crops include rice, wheat, maize, sugarcane, cotton, and vegetables. These fertile soils support high yields and promote agricultural productivity. Proper irrigation and nutrient management enhance the growth of these crops, making alluvial regions crucial for sustainable and diverse agricultural practices.");
            }else if(f.contains("Black")){
                textViewResult.append("\nBlack soil, also known as black cotton soil or regur, is a type of fertile soil found in parts of India, the United States, and other regions. Rich in minerals like calcium and magnesium, it is well-suited for crops such as cotton, wheat, and soybeans. Black soil has good water-retention properties, making it valuable for agriculture," +
                        " but proper management is essential to prevent issues like waterlogging.");
            }else if(f.contains("Red")){
                textViewResult.append("\nRed soil, prevalent in tropical and subtropical regions, gets its color from iron oxide. Although it is not as fertile as black soil, red soil supports various crops, including millets, groundnuts, and tobacco. Its composition lacks organic matter, and nutrient content varies, necessitating careful fertilization. Erosion control measures " +
                        "are crucial to maintain soil fertility, as red soil is susceptible to degradation and nutrient loss.");
            }else if(f.contains("Laterite")){
                textViewResult.append("\nLaterite soil is a type of tropical soil characterized by its red or orange color and rich iron content. Found in regions" +
                        " with high temperatures and heavy rainfall, laterite soil is often acidic and lacks fertility. However, it can support certain crops like cashew, tea, and coffee with proper management. Its ability to harden into a durable layer makes it valuable for construction purposes, especially in areas with limited alternative resources.");
            }else if(f.contains("Alkaline")){
                textViewResult.append("\nAlkaline soil, also known as basic soil, has a pH level above 7. It contains high levels of calcium," +
                        " magnesium, and sodium but may lack essential nutrients. This type of soil can be challenging for many crops, as it hinders nutrient uptake. Crops like barley, beets, and asparagus are more tolerant of alkaline conditions. Amending the soil with organic matter and using suitable fertilizers can help improve its fertility and make it more conducive to agriculture.");
            }else if(f.contains("Clay")){
                textViewResult.append("\nClay soil is characterized by fine particles and high water retention capacity. While it is fertile, it tends to compact easily, hindering root growth and drainage. Adequate organic matter and proper management practices, such as crop rotation and cover cropping, can enhance its structure and fertility. Crops like wheat, corn, " +
                        "and soybeans thrive in clay soil when managed appropriately. Additionally, clay is used in pottery and construction due to its molding and adhesive properties.");
            }

        }catch ( Exception s){
            Toast.makeText(this, ""+s.getMessage(), Toast.LENGTH_SHORT).show();
        }
/*
        land_type=results.toString();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(requestCode==100){
                assert data != null;
                Bitmap data1 = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                if(data1!=null){
                    recognition(data1);
                }
            }else if(requestCode==200){
                assert data != null;
                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(Objects.requireNonNull(data.getData()).toString()));
                    recognition(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}