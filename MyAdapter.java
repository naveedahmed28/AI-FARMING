package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> implements Filterable {

    Context c;
    ArrayList<Model> models ;
    ArrayList<Model> list ;
    ArrayList<Model> cart = new ArrayList<>();

    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
        this.list = new ArrayList<>(models);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.card,parent,false);
        return new MyHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mCost.setText(models.get(position).getCost());
        holder.mFname.setText(models.get(position).getFname());
        holder.mFupid.setText(models.get(position).getFUpi());
        Uri uri=Uri.parse(models.get(position).getImg());
        Glide.with(c).load(uri).into(holder.mImgView);



        holder.Qty.setText(Integer.toString(models.get(position).getQuant()));
        holder.add.setOnClickListener(view -> {
            holder.Qty.setText("1");
            holder.add.setVisibility(View.INVISIBLE);
            holder.plus.setVisibility(View.VISIBLE);
            holder.minus.setVisibility(View.VISIBLE);
            holder.Qty.setVisibility(View.VISIBLE);
            models.get(position).setQuant(1);
            cart.add(models.get(position));
            notifyDataSetChanged();


        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt((String) holder.Qty.getText());
                quant++;
                holder.Qty.setText(Integer.toString(quant));
                models.get(position).setQuant(quant);
                int Quant = Integer.parseInt((String) holder.Qty.getText());
                if(Quant>0){
                    holder.add.setVisibility(View.INVISIBLE);
                    holder.plus.setVisibility(View.VISIBLE);
                    holder.minus.setVisibility(View.VISIBLE);
                    holder.Qty.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {int Quant = Integer.parseInt((String) holder.Qty.getText());
                if(Quant>0){
                    holder.add.setVisibility(View.INVISIBLE);
                    holder.plus.setVisibility(View.VISIBLE);
                    holder.minus.setVisibility(View.VISIBLE);
                    holder.Qty.setVisibility(View.VISIBLE);
                }
                int quant = Integer.parseInt((String) holder.Qty.getText());
                quant--;
                if(quant<1){
                    holder.add.setVisibility(View.VISIBLE);
                    holder.plus.setVisibility(View.INVISIBLE);
                    holder.minus.setVisibility(View.INVISIBLE);
                    holder.Qty.setVisibility(View.INVISIBLE);
                    models.get(position).setQuant(0);
                    cart.remove(models.get(position));
                    notifyDataSetChanged();
                }else{
                    holder.Qty.setText(Integer.toString(quant));
                    models.get(position).setQuant(quant);
                    notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public Filter getFilter() {

        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            //list=new ArrayList<>(models);
            ArrayList<Model> filterlist = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filterlist.addAll(list);

            }else{
                for(Model item : list){
                    if(item.getTitle().toLowerCase().startsWith(charSequence.toString().toLowerCase())){
                        filterlist.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterlist;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            models.clear();
            models=(ArrayList<Model>) filterResults.values;
            notifyDataSetChanged();
        }


    };
   public class MyHolder extends RecyclerView.ViewHolder {
        ImageView mImgView;
        TextView mTitle,mCost,Qty,mFname,mFupid;
        Button add,plus,minus;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mImgView = itemView.findViewById(R.id.image);
          mTitle = itemView.findViewById(R.id.title);
           mCost = itemView.findViewById(R.id.cost);
     mFname = itemView.findViewById(R.id.fname);
    mFupid = itemView.findViewById(R.id.fupid);

   Qty =itemView.findViewById(R.id.quantity);
         add = itemView.findViewById(R.id.addtocart);
       minus = itemView.findViewById(R.id.minus);
       plus = itemView.findViewById(R.id.plus);




        }
    }

}
