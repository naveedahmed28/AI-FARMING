package com.example.fertilizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderidVise extends AppCompatActivity {
ArrayList<String> id=new ArrayList<>();
ArrayList<String> dd=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderid_vise);
        ListView listView=findViewById(R.id.cycle);
      ProgressDialog p=new ProgressDialog(OrderidVise.this);
        p.setCancelable(false);
        p.setTitle("Loading.........");
        StringRequest request=new StringRequest(Request.Method.POST, "https://wizzie.online/fertilizer/orderidvise.php", response -> {
            p.dismiss();

            try {
                JSONArray jsonArray=new JSONArray(response);
for (int i=0;i<jsonArray.length();i++){
    JSONObject jsonObject=jsonArray.getJSONObject(i);
    id.add("Order-ID : "+jsonObject.getString("orderid"));
    dd.add(jsonObject.getString("orderid"));
}
            } catch (JSONException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
           listView.setAdapter(new ArrayAdapter<>(OrderidVise.this, android.R.layout.simple_dropdown_item_1line,id));
listView.setOnItemClickListener((adapterView, view, i, l) -> {
    Intent intent=new Intent(getApplicationContext(),my_orders.class);
    intent.putExtra("id",dd.get(i));
    startActivity(intent);
});
        }, error -> {
            p.dismiss();
            Toast.makeText(this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("id",getSharedPreferences("sharedPrefs",MODE_PRIVATE).getString("id","NON"));
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);



        p.show();

    }
}