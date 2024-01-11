package com.example.fertilizer.Soilest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fertilizer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Recomm extends AppCompatActivity {
    static String city,land_size,land_type,temp,hub;
    String crop;

    TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        textView=findViewById(R.id.testgross);

        city=getIntent().getStringExtra("city");
        land_size=getIntent().getStringExtra("size");
        temp=getIntent().getStringExtra("temp");
        hub=getIntent().getStringExtra("hum");
        land_type=getIntent().getStringExtra("lt");
        StringBuilder j= new StringBuilder();
        StringBuilder jhf=new StringBuilder();

        if(temp!=null&&hub!=null){
            appender(j,temp);
        appender(jhf,hub);
        }
        String temparature=j.toString();
        if(temparature.chars().allMatch(Character::isDigit)){
            if(!temparature.trim().isEmpty()) {
                Log.i("MyVokjasdf", temparature);
                if (Integer.parseInt(temparature) > 30) {
                    textView.setText(HtmlCompat.fromHtml("<big>Your soil have a Moisture</big>", HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS));
                } else if (Integer.parseInt(temparature) > 20) {
                    textView.setText(HtmlCompat.fromHtml("<big>Your soil Can be irrigable </big>", HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS));
                } else if (Integer.parseInt(temparature) > 10) {
                    textView.setText(HtmlCompat.fromHtml("<big>Snow Temperature is  Two Low</big>", HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS));
                } else if (Integer.parseInt(temparature) > 40) {
                    textView.setText(HtmlCompat.fromHtml("<big>Cool down the Sand \nand \nwarm the farm land</big>", HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS));
                }
            }}






        // Toast.makeText(this, ":"+city+":"+temp+":"+hub+":"+land_type, Toast.LENGTH_SHORT).show();
   /*             getData();
   */ }

    private void appender(StringBuilder j,String string) {
        if(string!=null) {
            for (int i = 0; i < string.length(); i++) {
                Character the = string.charAt(i);
                if (the.toString().chars().allMatch(Character::isDigit)) {
                    j.append(the);
                }
            }
        }}
/*    private void getData() {
        final ProgressDialog pPd = new ProgressDialog(Recomm.this);
        pPd.setMessage("Loading");
        pPd.setCancelable(false);
        pPd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://wizzie.tech/croprecommendation/getrecommendations.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        pPd.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(Recomm.this, "No Recommendation Found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        crop=jsonObject.getString("crop").trim();
                                        // Toast.makeText(Recommendation.this, ""+crop, Toast.LENGTH_SHORT).show();
                                    }

                                    textView.setText(crop);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("city", city.trim());
                params.put("temp", temp.trim());
                params.put("hum", hub.trim());
                params.put("lt", land_type.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }*/
}