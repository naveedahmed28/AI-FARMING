package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class farmer_item_adapter extends RecyclerView.Adapter<farmer_item_holder> {
    Context c;
    ArrayList<Farmer> models ;
    RequestQueue mQueue;
    public farmer_item_adapter(Context c, ArrayList<Farmer> models) {
        this.c = c;
        this.models = models;
        mQueue = VolleySingleton.getInstance(c).getmRequestqueue();
    }

    @NonNull
    @Override
    public farmer_item_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_listed_item,parent,false);
        return new farmer_item_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull farmer_item_holder holder, @SuppressLint("RecyclerView") final int position) {
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mType.setText(models.get(position).getType());
        holder.mCost.setText(models.get(position).getCost());
        holder.mFName.setText(models.get(position).getFName());
        holder.mFUpi.setText(models.get(position).getUpi());
        Glide.with(c).load(Uri.parse("https://wizzie.online/fertilizer/images/"+models.get(position).getImg())).into(holder.mImgView);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c,farmer_edit_item.class);
                i.putExtra("ID",String.valueOf(models.get(position).getId()));
                i.putExtra("title",models.get(position).getTitle());
                i.putExtra("cost",models.get(position).getCost());
                i.putExtra("img",models.get(position).getImg());
                i.putExtra("type",models.get(position).getType());
                i.putExtra("fname",models.get(position).getFName());
                i.putExtra("fupid",models.get(position).getUpi());
                c.startActivity(i);
                admin_Listed_items.FLAG=1;
            }
        });
        holder.delete.setOnClickListener(view -> {

            final ProgressDialog progressDialog = new ProgressDialog(c);
            progressDialog.setMessage("Loading...");
            progressDialog.show();


            String url = "https://wizzie.online/fertilizer/farmerdeleteitem.php";

            StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonobject = new JSONObject(response);

                        String success = jsonobject.getString("success");
                        Toast.makeText(c, success, Toast.LENGTH_LONG).show();
                        if(success.equals("1")) {
                            //Toast.makeText(c, "success", Toast.LENGTH_LONG).show();

                        }
                        else{
                            //Toast.makeText(c, "delete Failed ", Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();

                    } catch (JSONException e) {
                        //Toast.makeText(c, "json Failed ", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        progressDialog.dismiss();

                    }
                    models.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,models.size());
                    notifyDataSetChanged();
                    progressDialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(c, "Error connecting check Internet Connection", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    HashMap<String,String> param = new HashMap<String,String>();
                    param.put("id",Integer.toString(models.get(position).getId()));

                    return param;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };

            mQueue.add(sr);
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
