package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class seller_Listed_items extends AppCompatActivity {
    RecyclerView mRecyclerView;
    admin_item_adapter myAdapter;
    ArrayList<Model> models ;
    public RequestQueue mQueue;
    public static int FLAG = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_items);
        models = new ArrayList<>();
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        mRecyclerView = findViewById(R.id.adminlistedrecy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();

        models= new ArrayList<>();
        myAdapter = new admin_item_adapter(this,models);
        mRecyclerView.setAdapter(myAdapter);

        FloatingActionButton add = findViewById(R.id.additem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),admin_edit_item.class);
                i.putExtra("type","seller");
                startActivityForResult(i,0);
            }
        });

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        String url = "https://wizzie.online/fertilizer/getData.php";

        @SuppressLint("NotifyDataSetChanged")
        StringRequest ObjRequest = new StringRequest(Request.Method.POST,url,
                response -> {

                    try {
                        JSONArray result = new JSONArray(response);

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
                        Toast.makeText(seller_Listed_items.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    myAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                },
                e -> {
                    Toast.makeText(seller_Listed_items.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();      progressDialog.dismiss();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>params=new HashMap<>();
                params.put("id",getSharedPreferences("sharedPrefs",MODE_PRIVATE).getString("id",""));
                return params;
            }
        };
        //Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();
        RequestQueue request= Volley.newRequestQueue(this);
        request.add(ObjRequest);
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