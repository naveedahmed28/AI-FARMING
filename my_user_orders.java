    package com.example.fertilizer;

    import android.app.ProgressDialog;
    import android.content.SharedPreferences;
    import android.os.Bundle;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.android.volley.AuthFailureError;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.StringRequest;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Map;

    public class my_user_orders extends AppCompatActivity {
    RecyclerView mRecyclerView;
    orderAdapter mAdapter;
    ArrayList<order> models;
    public RequestQueue mQueue;
    SharedPreferences sharedPreferences;
    String user_id;
    ArrayList<String> id=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        models = new ArrayList<>();
        sharedPreferences = sharedPreferences = getSharedPreferences(Login.SHARED_PREFS,MODE_PRIVATE);
        user_id = sharedPreferences.getString("id","");
        mRecyclerView = findViewById(R.id.orderREcycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new orderAdapter(this,models, id);
        mRecyclerView.setAdapter(mAdapter);
        mQueue = VolleySingleton.getInstance(this).getmRequestqueue();
        getData();

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "https://wizzie.online/fertilizer/myuserorders.php";

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    for(int i = 0 ; i<result.length();i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String status = jo.getString("status");
                        String date = jo.getString("date");

                        StringBuilder content = new StringBuilder();

                        int total=0;



                        order o = new order();
                        o.setDate(date);
                        o.setStatus(status);
                        o.setContent(content.toString());
                        o.setTotal(total+"₹/-");

                        models.add(o);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                mAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("user_id",user_id);
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        mQueue.add(sr);


    }
    }
