package com.example.fertilizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    EditText disease,symptoms,medicine;

    Spinner type;
    Button add;
    ProgressDialog progressDialog;

    private static final String URL="https://wizzie.online/fertilizer/newfile/retrivedata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        progressDialog=new ProgressDialog(Feedback.this);

        disease=findViewById(R.id.dname);
        medicine=findViewById(R.id.dmed);
        symptoms=findViewById(R.id.dsympt);
        add=findViewById(R.id.btnadd);
        type = findViewById(R.id.medicine_type);

        add.setOnClickListener(v -> {

       if(symptoms.getText().toString().trim().isEmpty()){
                Snackbar.make(Feedback.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Symptoms", Snackbar.LENGTH_SHORT).show();
            }
            else{
                progressDialog.setMessage("Loading Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String dd=getIntent().getStringExtra("orderid");

                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Feedback.this, ""+response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Feedback.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params=new HashMap<String, String>();

                     /*   params.put("d",disease.getText().toString().trim());
                        params.put("s",symptoms.getText().toString().trim());
                        params.put("m",medicine.getText().toString().trim());
                        params.put("t",type.getSelectedItem().toString().trim());*/
params.put("review",symptoms.getText().toString());
params.put("orderid",dd);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


            }
        });

    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}