package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderView extends RecyclerView.Adapter<OrderView.view2>{
    ArrayList<String>orderid;
            ArrayList<String>quantity;
    ArrayList<String>userid;
            ArrayList<String>url;
    ArrayList<String>cost;
            ArrayList<String>date;
    ArrayList<String>time;
    ArrayList<String>id;
    Context context;

    public OrderView(Context context,
                     ArrayList<String> orderid,
                     ArrayList<String> quantity,
                     ArrayList<String> userid,
                     ArrayList<String> url, ArrayList<String> cost,
                     ArrayList<String> date,
                     ArrayList<String> time,
                     ArrayList<String>id) {
        this.orderid=orderid;
        this.context=context;
                this.quantity=quantity;
        this.userid=userid;
                this.url=url;
        this.cost=cost;
                this.date=date;
        this.time=time;
        this.id=id;
    }

    @NonNull
    @Override
    public view2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new view2(LayoutInflater.from(context).inflate(R.layout.maincard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull view2 holder, @SuppressLint("RecyclerView") int position) {
        String[] tt=cost.get(position).split("Rs/");
        int total=Integer.parseInt(quantity.get(position))*Integer.parseInt(tt[0]);
        Glide.with(context).load(Uri.parse(url.get(position))).into(holder.imageView);
        String text="<b>Oder id :</b>"+orderid.get(position)+"<br></br>"+
                "<b>Quantity :</b>"+quantity.get(position)+"<br></br>"+
                "<b>Cost :</b>"+cost.get(position)+"<br></br>" +
                "<b>Total :</b>"+total+"" ;
        holder.textView.setText(HtmlCompat.fromHtml(text,HtmlCompat.FROM_HTML_MODE_LEGACY));

holder.itemView.setOnClickListener(f->{
    ProgressDialog p=new ProgressDialog(context);
    p.setCancelable(false);
    p.setTitle("Loading.....");
    p.show();
    StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://wizzie.online/fertilizer/checkdetails.php",
            response -> {
p.dismiss();
        AlertDialog.Builder n=new AlertDialog.Builder(context);
String o="";
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        o="Name :"+jsonObject.getString("Name")+"\n"+
                                "Mobile Number : "+jsonObject.getString("no")+"\n"+
                                "Address : "+jsonObject.getString("address");
                    }
                    n.setTitle("userDetails");
                    n.setMessage(o);
                    n.setPositiveButton("Done", (dialogInterface, i) -> {
update(context,id.get(position));
                    });
                    n.setNegativeButton("Not Yet", (dialogInterface, i) -> {
dialogInterface.dismiss();
                    });
                    if(jsonArray.length()!=0){
                        n.show();
                    }else{
                        Toast.makeText(context, "Invalid User", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            },
            error -> p.dismiss()){
        @Override
        protected Map<String, String> getParams()  {
            HashMap<String,String>params=new HashMap<>();
            params.put("id",userid.get(position));
            return params;
        }
    };
    RequestQueue requestQueue= Volley.newRequestQueue(context);
    requestQueue.add(stringRequest);
});
    }

    private void update(Context context, String s) {
        ProgressDialog p=new ProgressDialog(context);
        p.setCancelable(false);
        p.setTitle("Loading.....");
        p.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://wizzie.online/fertilizer/updateoderid.php",
                response -> {
                 p.dismiss();
                    Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                }
                ,error -> {
            Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            p.dismiss();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>params=new HashMap<>();
                params.put("id",s);
                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return orderid.size();
    }

    public class view2 extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public view2(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.userdetails);
            imageView=itemView.findViewById(R.id.imagedata);
        }
    }
}
