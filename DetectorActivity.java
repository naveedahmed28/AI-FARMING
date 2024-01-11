package com.example.fertilizer.PestDetection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fertilizer.R;
import com.example.fertilizer.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.util.Objects;

public class DetectorActivity extends AppCompatActivity {
    private Model model;
    ImageView captures;
    TextView textView;
    TextView resultVIew;
    MutableLiveData<String> liveData= new MutableLiveData<>("");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);
        captures=findViewById(R.id.captures);
        resultVIew=findViewById(R.id.resultVIew);
        captures.setOnClickListener(
                f->{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,100);
                }
        );
        textView=findViewById(R.id.result);
        liveData.setValue("Result");
        liveData.observe(this, s -> {
            if(s!=null){
                textView.setText(s);
            }
        });

        try {
            model=Model.newInstance(this);


        } catch (IOException e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void funtion(String s) {
        resultVIew.setText("");
        if(s.contains("Powdery Mildew")){
            resultVIew.setText("\n\nCombat powdery mildew by mixing 1 part milk with 9 parts water and spraying on" +
                    " affected plants; milk's proteins act as an antifungal. Alternatively, use a baking soda solution (1 tablespoon baking soda, 1 gallon water). Apply neem oil or sulfur-based fungicides. Regularly prune and space plants for better airflow, reducing humidity that favors mildew growth.");
        }else if(s.contains("Botrytis cinerea")){
            resultVIew.setText("\n\nTo control Botrytis cinerea, or gray mold, employ cultural and" +
                    " chemical measures. Prune affected plant parts, maintain proper spacing for airflow, and reduce humidity. Apply fungicides like copper-based or biological agents. Employ good sanitation practices, removing infected plant debris. Regular monitoring and early intervention are crucial to prevent and manage Botrytis outbreaks effectively.");
        }else if(s.contains("Rust")){
            resultVIew.setText("\n\nCombat rust on plants by implementing various strategies. Use fungicides containing copper or sulfur, applying them as a preventive measure. Practice good garden hygiene by removing infected leaves and debris. Ensure proper spacing for airflow, and water plants at the base to avoid leaf wetness. Choose rust-resistant plant varieties and consider applying neem oil as a natural remedy. Regular monitoring is key to early detection and control.\n");
        }else if(s.contains("Anthracnose")){
            resultVIew.setText("\n\nManage anthracnose, a fungal disease affecting plants, with several strategies. Employ fungicides containing copper" +
                    " or chlorothalonil. Ensure proper plant spacing for ventilation and promptly remove infected plant debris. Water at the base to avoid leaf wetness. Apply organic solutions like neem oil. Opt for resistant plant varieties and practice crop rotation to mitigate the disease's impact. Regular monitoring and early intervention are crucial for effective anthracnose control.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            functions((Bitmap) Objects.requireNonNull(data.getExtras()).get("data"));
        }
    }

    @SuppressLint("SetTextI18n")
    private void functions(Bitmap bitmap) {
        captures.setImageBitmap(bitmap);
        liveData.setValue("");
        Bitmap realbimatp=Bitmap.createScaledBitmap(bitmap,224,224,true);
        TensorBuffer tensor = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
        TensorImage gg = new TensorImage(DataType.UINT8);
        gg.load(realbimatp);
        tensor.loadBuffer(gg.getBuffer());
        Model.Outputs ff = model.process(tensor);
        TensorBuffer sd = ff.getOutputFeature0AsTensorBuffer();
        String[] d = new String[]{
                "Powdery Mildew"
                ,"Botrytis cinerea"
                , "Rust"
                , "Anthracnose"
        };
        int num=0;
        float value=0.0f;

        for (float fine :sd.getFloatArray()){
            if(fine>value){
                Log.i("kajhdsfhsdkfhksd",""+value);
                value=fine;
                funtion(d[num]);
            }
            liveData.setValue(liveData.getValue()+d[num]+" ->"+fine+"\n");
            num++;
        }


    }
    public String max( float[] outputs) {
        String result="";
        if ((outputs[0] > outputs[1]) && (outputs[0] > outputs[2]) && (outputs[0] > outputs[3])
        ) {
            result="Powdery Mildew";
        } else if ( (outputs[1] > outputs[0]) && (outputs[1] > outputs[2]) && (outputs[1] > outputs[3])
        ) {
            result="Botrytis cinerea";
        } else if ( (outputs[2] > outputs[0]) && (outputs[2] > outputs[1]) && (outputs[2] > outputs[3])
        ) {
            result="Rust";
        } else if ((outputs[3] > outputs[0]) && (outputs[3] > outputs[1]) && (outputs[3] > outputs[2])
        ) {
            result="Anthracnose";
        } else {
            result="Nothing";
        }
        return result;

    }
}