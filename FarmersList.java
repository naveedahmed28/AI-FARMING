package com.example.fertilizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FarmersList extends AppCompatActivity {


    Typeface tfRegular;
    ImageView logout;
    SearchView searchthem;
    RecyclerView recyclerView;
    FarmerAdminAdapter searchAdapter;
    ProgressDialog progressDialog;
    private static final String URL="https://wizzie.online/fertilizer/appointment.php";

    ArrayList disease=new ArrayList();
    ArrayList name=new ArrayList();
    ArrayList age=new ArrayList();
    ArrayList medicine=new ArrayList();

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phoneKey";
    static String m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmers_list);
        logout = findViewById(R.id.logout);

        recyclerView=findViewById(R.id.rec);
        progressDialog=new ProgressDialog(FarmersList.this);


//        searchthem = findViewById(R.id.searchthem);
        search();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Phone)) {

            m=sharedpreferences.getString(Phone, "");
            //Toast.makeText(this, ""+m, Toast.LENGTH_SHORT).show();

        }else {
            Intent i = getIntent();
            m = i.getStringExtra("mob");
        }


        /*searchthem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void search() {

//        if(searchthem.getQuery().toString().trim().isEmpty()){
//            Snackbar.make(AdminActivity.this.getWindow().getDecorView().findViewById(android.R.id.content), "Symptoms", Snackbar.LENGTH_SHORT).show();
//        }
//        else {
//            progressDialog.setMessage("Loading Please Wait");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            medicine.clear();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                           // Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                if (jsonArray.length() == 0) {
                                    Toast.makeText(FarmersList.this, "No Data Found", Toast.LENGTH_LONG).show();
                                } else {

                                    try {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                                            name.add(jsonObject.getString("name"));
                                            age.add(jsonObject.getString("productname"));
                                            disease.add(jsonObject.getString("feedback"));
                                            medicine.add(jsonObject.getString("rating"));




                                        }

                                        searchAdapter = new FarmerAdminAdapter(FarmersList.this,disease,name,age,medicine);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        recyclerView.setAdapter(searchAdapter);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("s", searchthem.getQuery().toString().trim());
//                    return params;
//                }
          };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    //}


}