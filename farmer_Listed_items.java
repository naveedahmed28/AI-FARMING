package com.example.fertilizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class farmer_Listed_items extends AppCompatActivity {
    RecyclerView mRecyclerView;
    farmer_item_adapter myAdapter;
    ArrayList<Farmer> models ;
    public RequestQueue mQueue;
    public static int FLAG = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_farmer_items);
        models = new ArrayList<>();
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        mRecyclerView = findViewById(R.id.adminlistedrecy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        models= new ArrayList<>();
        getData();


        FloatingActionButton add = findViewById(R.id.additem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),farmer_edit_item.class);
                startActivityForResult(i,0);
            }
        });

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        String url = "https://wizzie.online/fertilizer/farmergetData.php";
        StringRequest request=new StringRequest(Request.Method.GET,url,response->{

            JSONArray result = null;
            progressDialog.dismiss();
            try {
                result = new JSONArray(response);
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String title = jo.getString("title");
                    String type = jo.getString("type");
                    String cost = jo.getString("cost");
                    String fname = jo.getString("fname");
                    String fupi = jo.getString("fupid");
                    String imgstr = jo.getString("img");
                    int id = Integer.parseInt(jo.getString("itemid"));

                    Farmer m = new Farmer();
                    m.setTitle(title.substring(0, 1).toUpperCase() + title.substring(1));
                    m.setCost(cost);
                    m.setFName(fname);
                    m.setUpi(fupi);
                    m.setType(type);
                    m.setId(id);
                    m.setQuant(0);
                    m.setImg(imgstr);

                    models.add(m);
                }
                myAdapter = new farmer_item_adapter(this,models);
                mRecyclerView.setAdapter(myAdapter);

            } catch (JSONException e) {
                Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        },
                error->{
                    progressDialog.dismiss();

                    Toast.makeText(this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
RequestQueue reque= Volley.newRequestQueue(this);
reque.add(request);
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