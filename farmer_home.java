package com.example.fertilizer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fertilizer.PestDetection.DetectorActivity;
import com.example.fertilizer.Soilest.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class farmer_home extends AppCompatActivity {
    TextView tempar,humidity,presure;
    String weatherApi;
    String keywe;
    ArrayList<String> list=new ArrayList<>();
     CardView govt,more;
    String lat,lan;
    String last = "";
    Spinner city;
 String URLW="https://api.openweathermap.org/data/2.5/weather?q=";
    FusedLocationProviderClient mFusedLocationClient;
    @SuppressLint({"MissingPermission", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);
        CardView order = findViewById(R.id.orders1);
        CardView listed1 = findViewById(R.id.listed1);
        CardView listed  = findViewById(R.id.listed);
        CardView dd=findViewById(R.id.userdd);

        tempar = findViewById(R.id.temp);
        humidity = findViewById(R.id.humid);
        presure = findViewById(R.id.presure);
        CardView videos=findViewById(R.id.videos);
        more=findViewById(R.id.more);
        videos.setOnClickListener(l-> startActivity(new Intent(this,VideoPlayer.class)));
        keywe = "92865be03e415c7d6b5d96dfd063f137";
more.setOnClickListener(i->startActivity(new Intent(this,StrategyInfo.class)));
        list.add("Tirupati");
        list.add("Nellore");
        list.add("Visakhapatnam");
        list.add("Chennai");
        list.add("Anantapur");
        requestNewLocationData();
/*        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        city.setAdapter(adapter);*/
        getWeather();

        govt=findViewById(R.id.govt);
        govt.setOnClickListener(l->{
            startActivity(new Intent(this, GovernmentSchemes.class));
        });
CardView myview=findViewById(R.id.soiltesting);

CardView pest=findViewById(R.id.pest);
pest.setOnClickListener(v->{
    startActivity(new Intent(this, DetectorActivity.class));
});

        myview.setOnClickListener(v->{
            try {
if(tempar.getText().toString().isEmpty()&&presure.getText().toString().isEmpty()){
    Toast.makeText(this, "Wait while fetch the temperature details", Toast.LENGTH_SHORT).show();
}else {
    Intent tt = new Intent(this, MainActivity.class);

    tt.putExtra("tempar", tempar.getText().toString());
    tt.putExtra("presure", presure.getText().toString());
    tt.putExtra("humidity", humidity.getText().toString());
    tt.putExtra("city", "Tirupati");
    startActivity(tt);
}       }catch (Exception e){
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
});
        order.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),farmer_Listed_items.class);
            startActivity(i);
        });
        listed1.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),OrderidVise.class);
            startActivity(i);
        });
        listed.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Myview.class);
            startActivity(i);
        });
        dd.setOnClickListener(o->{
            startActivity(new Intent(this,FromUsers.class));
        });



        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    112);
        }
   mFusedLocationClient.getLastLocation().addOnCompleteListener(
                new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {

                            lat = String.valueOf(location.getLatitude());
                            lan = String.valueOf(location.getLongitude());
                            last = "http://api.openweathermap.org/data/2.5/find?lat=" + lat + "&lon=" + lan + "&appid=5d273274cb150c21d06ebd4a64fd9d7e";

                        }
                    }
                }
        );
    }
    int backButtonCount=0;
    @Override
    public void onBackPressed()
    {

        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminmenu,menu);
        MenuItem logout = menu.findItem(R.id.adminlogout);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.adminlogout){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );


    }
    private void getWeather() {


        weatherApi=URLW+"Tirupati"+"&appid="+keywe;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, weatherApi,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Toast.makeText(Iaq.this, ""+response, Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject js=jsonObject.getJSONObject("main");

                            double t=0,p=0,h=0;
                            t= Double.parseDouble(js.getString("temp").trim());
                            p= Double.parseDouble(js.getString("pressure").trim());
                            h= Double.parseDouble(js.getString("humidity").trim());

                            tempar.setText((int) ((t - 32) / 9.0) +"ºC");
                            presure.setText((int) (p) +"hpa");
                            humidity.setText((int) (h) +"%");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(farmer_home.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = String.valueOf(mLastLocation.getLatitude());
            lan = String.valueOf(mLastLocation.getLongitude());
            last = "http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lan+"&appid=5d273274cb150c21d06ebd4a64fd9d7e";
        }
    };


}
