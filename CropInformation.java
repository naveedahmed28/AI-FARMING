package com.example.fertilizer;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public  class CropInformation extends Activity {


    private static final String REGISTER_URL ="https://wizzie.online/fertilizer/getdata1.php";

    ArrayList data=new ArrayList();

   Set data1=new LinkedHashSet();
    Set imageurls=new LinkedHashSet();


    List<String> aList ;

    Set mySet;

    List name=new ArrayList();

    List url=new ArrayList();

    String[] android_version_names;

    String a[];
    ArrayList<String> linkedHashSetToArrayList;
    ArrayList<String> imagelistToArray;

    static String s,d,season,st,area,production,avg_rain,wr;
    double rain_below,rain_above;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cropinfo);

        s=SeasonsActivity.s;

        d=SeasonsActivity.d;
        season=SeasonsActivity.season;
        st=SeasonsActivity.st;

     //   area=CropPrediction.area;
        production=SeasonsActivity.production;

        wr=SeasonsActivity.waterres;


        Toast.makeText(this, "wr=="+wr, Toast.LENGTH_LONG).show();


     getdetails();


    }

    private void getdetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);



                        data1.add(jsonObject.getString("CROP"));
                        imageurls.add("http://wizzie.tech/gowthami/"+jsonObject.getString("imageurl"));


                        Pojo pojo=new Pojo();
                        pojo.setCrop(jsonObject.getString("CROP"));
                      //  pojo.setState(jsonObject.getString("STATE"));
                        pojo.setState("Andhrapradesh");
                        pojo.setDistrict(jsonObject.getString("DISTRICT"));
                        pojo.setSoiltype(jsonObject.getString("SOILTYPE"));
                        pojo.setSeason(jsonObject.getString("SEASON"));
                        pojo.setAverage_rainfall(jsonObject.getString("RAINFALL"));

                        data.add(pojo);

                    }

                    android_version_names=new String[name.size()];
                   for (int i =0; i < name.size(); i++) {

                        android_version_names[i] =name.get(i).toString();

                    }

                    progressDialog = new ProgressDialog(CropInformation.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(1000);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();


                   customlist();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("d",d);
                params.put("s",season);
                params.put("st",st);
                params.put("wr",wr);


                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void customlist() {


        linkedHashSetToArrayList = new ArrayList<>(data1);
        imagelistToArray = new ArrayList<>(imageurls);

        System.out.println("Crop names=========="+linkedHashSetToArrayList);
        System.out.println("imageurls==========="+imagelistToArray);


        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,  linkedHashSetToArrayList,imagelistToArray);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private ArrayList<AndroidVersion> prepareData2() {

        ArrayList<AndroidVersion> android_version2 = new ArrayList<>();
        for (int i = 0; i < android_version_names.length; i++)
        {
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
//            androidVersion.setAndroid_image_url(android_image_urls[i]);

            android_version2.add(androidVersion);
        }
        return android_version2;


    }

}
