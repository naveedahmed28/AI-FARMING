package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class farmer_edit_item extends AppCompatActivity {
    ImageView img ,retake;
    Button save ;
    Spinner category;
    EditText title , rs, unit, fname, fupiid;
    TextView alert;
    String name , cost , imgstring, item, fn, fu;
    Bitmap bitmap;
    RequestQueue mQueue;
    Intent i;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_edit_item);

        img = findViewById(R.id.editimage);
        retake = findViewById(R.id.editpicicon);
        save = findViewById(R.id.saveitem);
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        title = findViewById(R.id.edittitle);
        category = findViewById(R.id.cat);
        rs = findViewById(R.id.costRs);
        unit = findViewById(R.id.costUnit);
        fname = findViewById(R.id.farmername);
        fupiid = findViewById(R.id.upi);
        alert = findViewById(R.id.alertitem);
        i = getIntent();
        DrawableCompat.setTint(
                DrawableCompat.wrap(retake.getDrawable()),
                ContextCompat.getColor(this, R.color.colorAccent)
        );
        fname.setText(getSharedPreferences("sharedPrefs",MODE_PRIVATE).getString("un",""));
        if(i.hasExtra("ID")){
            title.setText(i.getStringExtra("title"));
            category.setSelected(Boolean.parseBoolean(i.getStringExtra("type")));
            String cost = i.getStringExtra("cost");
            String Rs = cost.substring(0,cost.indexOf("R")).trim();
            String Unit = cost.substring(cost.indexOf("/")+1).trim();
            Uri uri=Uri.parse("https://wizzie.online/fertilizer/images/"+i.getStringExtra("img"));
            fname.setText(getIntent().getStringExtra("fname"));
                    fupiid.setText(getIntent().getStringExtra("fupid"));
            retake.setVisibility(View.GONE);
            Glide.with(this).load(uri).into(img);
            rs.setText(Rs);
            unit.setText(Unit);
        }else{

            save.setText("Add Item");
        }
        retake.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 96);
            intent.putExtra("outputY", 96);
            startActivityForResult(intent,0);
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.setVisibility(View.GONE);
                name  = title.getText().toString();
                fn  = fname.getText().toString();
                fu = fupiid.getText().toString();
                item  = category.getSelectedItem().toString();
                cost = rs.getText().toString()+"Rs/ "+unit.getText().toString();

                if(name.isEmpty()){
                    alert.setText("Name cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(rs.getText().toString().isEmpty()){
                    alert.setText("Cost cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(unit.getText().toString().isEmpty()){
                    alert.setText("Unit cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(fname.getText().toString().isEmpty()){
                    alert.setText("farmer name cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(fupiid.getText().toString().isEmpty()){
                    alert.setText("UPI ID cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(img.getDrawable() == null){
                    alert.setText("Thumbnail Cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                }else if(item.isEmpty()){
                    alert.setText("Category cannot be Empty");
                    alert.setVisibility(View.VISIBLE);
                } else{
                    senditem();
                }
            }
        });

    }

    private void senditem() {
        if(getIntent().getStringExtra("img")==null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            imgstring = Base64.encodeToString(bytes, Base64.DEFAULT);
        }else{
            imgstring=getIntent().getStringExtra("img");
        }



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "https://wizzie.online/fertilizer/farmeradditem.php";

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(farmer_edit_item.this, ""+response, Toast.LENGTH_SHORT).show();
          progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error connecting in check Internet Connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> param = new HashMap<String,String>();
                if(getIntent().getStringExtra("img")==null){
                    param.put("id","-1");
                }else{
                    param.put("id",getIntent().getStringExtra("ID"));
                }

                param.put("title",name);
                param.put("type","farmer");
                param.put("cost",cost);
                param.put("fname",fn);
                param.put("fupid",fu);
                param.put("image",imgstring);

                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        mQueue.add(sr);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap)data.getExtras().get("data");
        img.setImageBitmap(bitmap);
    }

}