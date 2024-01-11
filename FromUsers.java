package com.example.fertilizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

public class FromUsers extends AppCompatActivity {
ArrayList<String> orderid=new ArrayList<>();
ArrayList<String> quantity=new ArrayList<>();
ArrayList<String> userid=new ArrayList<>();
ArrayList<String> url=new ArrayList<>();
ArrayList<String> cost=new ArrayList<>();
ArrayList<String> date=new ArrayList<>();
ArrayList<String> time=new ArrayList<>();
ArrayList<String> id=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_users);
        RecyclerView cycle2=findViewById(R.id.cycle2);
        cycle2.setLayoutManager(new LinearLayoutManager(this));
        StringRequest request=new StringRequest(Request.Method.POST,"https://wizzie.online/fertilizer/orderdetails.php",respose->{
            try {
                JSONArray jsonArray=new JSONArray(respose);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    orderid.add(jsonObject.getString("orderid"));
                    quantity.add(jsonObject.getString("quant"));
                    userid.add(jsonObject.getString("userid"));
                    url.add(jsonObject.getString("url"));
                    id.add(jsonObject.getString("id"));
                    cost.add(jsonObject.getString("cost"));
                    date.add(jsonObject.getString("date"));
                    time.add(jsonObject.getString("time"));
                }
                OrderView rr=new OrderView(this,orderid,
                        quantity,
                        userid,
                        url,
                        cost,
                        date,
                        time,
                        id);
                cycle2.setAdapter(rr);
            } catch (JSONException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        },
                error -> {
                    Toast.makeText(this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String>params=new HashMap<>();

if(getIntent().getStringExtra("charan")!=null){
    params.put("name",getSharedPreferences("sharedPrefs",MODE_PRIVATE).getString("id",""));
}else{
    params.put("name",getSharedPreferences("sharedPrefs",MODE_PRIVATE).getString("un",""));
}
                return params;
            }
        };
        RequestQueue re= Volley.newRequestQueue(this);
        re.add(request);

    }
}