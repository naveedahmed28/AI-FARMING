package com.example.fertilizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class my_orders extends AppCompatActivity {
    RecyclerView mRecyclerView;
    orderAdapter mAdapter;
    ArrayList<order> models;
    public RequestQueue mQueue;
    SharedPreferences sharedPreferences;
    String user_id;
    ArrayList<String> id=new ArrayList<>();
        int d=0;

        TextView text;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        text=findViewById(R.id.payable);
        models = new ArrayList<>();
        sharedPreferences = sharedPreferences = getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
        user_id = sharedPreferences.getString("id","");
        mRecyclerView = findViewById(R.id.orderREcycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new orderAdapter(this,models,id);
        mRecyclerView.setAdapter(mAdapter);
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        getData();
        Button feedback3=findViewById(R.id.feedback3);
                Button feedback4=findViewById(R.id.feedback4);
                feedback3.setOnClickListener(v->{
      Intent intent = new Intent(this,Feedback.class);
                intent.putExtra("orderid",getIntent().getStringExtra("id"));
              startActivity(intent);
                });
        feedback4.setOnClickListener(v->{
    Intent intent = new Intent(this,Pay.class);
                intent.putExtra("orderid",getIntent().getStringExtra("id"));
                intent.putExtra("payment",String.valueOf(d));
          startActivity(intent);
        });

    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

            String url = "https://wizzie.online/fertilizer/myorder2.php";

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray result = new JSONArray(response);
                    for(int i = 0 ; i<result.length();i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String status = jo.getString("status");
                        String date = jo.getString("date");
                        String ordeid=jo.getString("orderid");
                        StringBuilder content = new StringBuilder();
                        content.append("Status : "+status+"\n"+"Order Date : "+date+"\n"+"Orderid : "+ordeid+"\n" + "");
                  id.add(jo.getString("orderid"));
                        String[] ff = jo.getString("cost").split("Rs");
                        int dd;
                        dd = Integer.parseInt(jo.getString("quant"))*Integer.parseInt(ff[0]);
                        String s = ff[0];
                        d=Integer.parseInt(s)+d;
                        order o = new order();
                        o.setDate(date);
                        o.setStatus(status);
                        o.setContent(content.toString());
                        o.setTotal(dd +" Rs");
                        models.add(o);
                    }

                    text.setText("Total Payable ₹"+d+"/-");


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                mAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("id",getIntent().getStringExtra("id") );
                return param;
            }
        };
        mQueue.add(sr);


    }
    }
