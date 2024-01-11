package com.example.fertilizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class admin_Listed_items extends AppCompatActivity {
    RecyclerView mRecyclerView;
    admin_item_adapter myAdapter;
    ArrayList<Model> models ;
    public RequestQueue mQueue;
    public static int FLAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_items);
        models = new ArrayList<>();
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        mRecyclerView = findViewById(R.id.adminlistedrecy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        models= new ArrayList<>();
        getData();
        myAdapter = new admin_item_adapter(admin_Listed_items.this,models);
        mRecyclerView.setAdapter(myAdapter);

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        String url = "https://wizzie.online/fertilizer/getData.php";

        StringRequest ObjRequest = new StringRequest(Request.Method.GET,url,
                response -> {
                    try {

                        JSONArray result =new  JSONArray(response);

                        for (int i = 0; i < result.length(); i++) {
                            JSONObject jo = result.getJSONObject(i);
                            String title = jo.getString("title");
                            String cost = jo.getString("cost");
                            String imgstr = jo.getString("img");
                            int id = Integer.parseInt(jo.getString("itemid"));

                            Model m = new Model();
                            m.setTitle(title.substring(0, 1).toUpperCase() + title.substring(1));
                            m.setCost(cost);
                            m.setId(id);
                            m.setQuant(0);
                            m.setImg(imgstr);


                            models.add(m);
                        }


                    } catch (JSONException e) {
                        Toast.makeText(admin_Listed_items.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();

                },
                e -> {
                    Toast.makeText(admin_Listed_items.this, ""+e, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }) {

        };


        RequestQueue rquest= Volley.newRequestQueue(admin_Listed_items.this);
        rquest.add(ObjRequest);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0){
            models.clear();
            getData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(FLAG==1){
            models.clear();
            getData();
            FLAG=0;
        }


    }
}