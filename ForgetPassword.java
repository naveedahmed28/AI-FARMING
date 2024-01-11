package com.example.fertilizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        EditText editText=findViewById(R.id.editText);
                Button button3=findViewById(R.id.button3);
        EditText editText2=findViewById(R.id.editText2);
        button3.setOnClickListener(b->{
            String tt=editText2.getText().toString();
            if(editText2.getVisibility()== View.VISIBLE){
                if(tt.isEmpty()){
                    Toast.makeText(this, "Please Enter Your New Password", Toast.LENGTH_SHORT).show();
                }else {
                    ProgressDialog dialog = new ProgressDialog(this);

                    dialog.setTitle("Loading.........");
                    dialog.setCancelable(false);
                    dialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, "https://wizzie.online/fertilizer/newfile/update.php", response -> {
                        Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }, error -> {
                        Toast.makeText(this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }) {
                        @Override
                        protected Map<String, String> getParams()   {
                            HashMap<String,String> params=new HashMap<>();
                            params.put("email", editText.getText().toString());
                            params.put("pass",editText2.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(this);
                    queue.add(request);
                }
            }else {
                if (!editText.getText().toString().isEmpty()) {
                    ProgressDialog p = new ProgressDialog(this);
                    p.setCancelable(false);
                    p.setTitle("Loading.......");
                    p.show();
                    @SuppressLint("SetTextI18n") StringRequest request = new StringRequest(Request.Method.POST, "https://www.wizzie.online/fertilizer/newfile/verify.php", response -> {
                        p.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.toString().isEmpty()) {
                                Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                            } else {
                                editText2.setVisibility(View.VISIBLE);
                                editText.setVisibility(View.GONE);
                                button3.setText("Update");
                            }
                        } catch (JSONException e) {
                            Toast.makeText(this, "" + e
                                    , Toast.LENGTH_SHORT).show();
                        }

                    }, error -> p.dismiss()) {
                        @Override
                        protected Map<String, String> getParams() {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("email", editText.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(this);
                    queue.add(request);
                } else {
                    Toast.makeText(this, "Please Type a Mail", Toast.LENGTH_SHORT).show();
                }
            } });
    }
}