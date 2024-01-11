package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class orderAdapter extends RecyclerView.Adapter<orderHolder> {
    @NonNull
    Context c;
    ArrayList<order> models;
    ArrayList<String>id;

    public orderAdapter(@NonNull Context c, ArrayList<order> models, ArrayList<String> id) {
        this.c = c;
        this.models = models;
        this.id=id;
    }

    @Override
    public orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card,parent,false);
        return new orderHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull orderHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.date.setText(models.get(position).getDate());
        holder.total.setText(models.get(position).getTotal());
        holder.content.setText(models.get(position).getContent());


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c,Feedback.class);
                intent.putExtra("orderid",id.get(position));
                c.startActivity(intent);

            }
        });

        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c,Pay.class);
                intent.putExtra("orderid",id.get(position));
                c.startActivity(intent);

            }
        });

        if(models.get(position).getStatus().equals("1")){
            holder.status.setTextColor(R.color.colorPrimary);
            holder.status.setText("Delivered");
            holder.status.setTextColor(ContextCompat.getColor(c,R.color.newgreen));
            holder.parent.setBackgroundResource( R.drawable.order_card_compleate);
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
