package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class  user_cart_activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public RequestQueue mQueue;
    public int order_id;
    HashMap<Integer,Integer> cartlist ;
    ArrayList<Model> list;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        Bundle extra =  getIntent().getBundleExtra("cart");
        list = (ArrayList<Model>) extra.getSerializable("objects");
        if(list.isEmpty()){
            setContentView(R.layout.empty_cart);
            Button shop = findViewById(R.id.shopnow);
            shop.setOnClickListener(view -> finish());
        }else{
            setContentView(R.layout.activity_cart_activity);
            TextView totalPrice = findViewById(R.id.totalprice);
            ListView listcart = findViewById(R.id.cartlist);
            int Price = 0;
            mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
            CartListAdapter adapter = new CartListAdapter(this,R.layout.cart_list,list);
            listcart.setAdapter(adapter);
            cartlist = new HashMap<>();
            for(Model m : list){
                Price+=Integer.parseInt(m.getCost().substring(0,m.getCost().indexOf("R")).trim())*m.getQuant();
                cartlist.put(m.getId(),m.getQuant());
            }
            //Toast.makeText(this,Integer.toString(adapter.totalprice),Toast.LENGTH_LONG).show();
            totalPrice.setText(Integer.toString(Price)+" Rs");
            String address = sharedPreferences.getString("add","");
            String Name = sharedPreferences.getString("un","");
            TextView cartName = findViewById(R.id.cartName);
            TextView add = findViewById(R.id.addresscart);
            cartName.setText(Name);
            add.setText(address);
            Button order = findViewById(R.id.order);
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Random random=new Random();
                    int oererid=random.nextInt(100000000);
                    for (Model model : list) {
                        Order(model.getCost(),model.getFname(),model.getId(),model.getFUpi(),model.getQuant(),model.getImg(),model.getTitle(),oererid);
                    }

                }
            });
            Button edit = findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),edit_profile.class);
                    startActivity(i);
                }
            });
        }

    }


    private void Order(final String cost, final String modelFname, int id, final String fUpi, final int quant, final String modelImg, String title, int oererids) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final String url = "https://wizzie.online/fertilizer/addorder.php";

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(user_cart_activity.this, ""+response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                if(response.equals("success")){
                   list.clear();
                   cartlist.clear();
                   MainActivity.CLEAR_CART=1;
                   setContentView(R.layout.order_placed);
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error Logging in check Internet Connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> param = new HashMap<>();
                param.put("userid",sharedPreferences.getString("id",""));
                param.put("fname",modelFname);
                param.put("url",modelImg);
                param.put("quant", String.valueOf(quant));
                param.put("upi",fUpi);
                param.put("cost",cost);
                param.put("date", String.valueOf(LocalDate.now()));
                param.put("time",String.valueOf(LocalTime.now()));
                param.put("status","Process");
                param.put("orderid", String.valueOf(oererids));
                    return param;
            }

        };

        mQueue.add(sr);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void Checkout(View view) {

    }

    public void myordersIntent(View view) {
        Intent i = new Intent(this,my_user_orders.class);
        startActivity(i);
    }
}




