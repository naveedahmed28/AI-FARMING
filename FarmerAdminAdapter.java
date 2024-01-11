package com.example.fertilizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FarmerAdminAdapter extends RecyclerView.Adapter<FarmerAdminAdapter.ViewHolder> {


    ArrayList disease=new ArrayList();
    ArrayList medicine=new ArrayList();
    ArrayList name=new ArrayList();
    ArrayList age=new ArrayList();
    Context context;
    ArrayAdapter adapter;
    ArrayList sdf=new ArrayList();
    String[]ff;
    String ss;




    public FarmerAdminAdapter(FarmersList mainActivity, ArrayList disease, ArrayList name, ArrayList age, ArrayList medicine) {
        this.context=mainActivity;
        this.disease=disease;
        this.medicine=medicine;
        this.name=name;
        this.age=age;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.farmer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        for(int i=0;i<disease.size();i++){
            ff= String.valueOf(i).split(",");
            ss=ff[0];

        }
        sdf.add(ss);

        adapter=new ArrayAdapter(context,android.R.layout.simple_spinner_item,sdf);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        holder.spinner.setAdapter(adapter);
        holder.dis.setText(disease.get(position).toString().trim());
        holder.med.setText(medicine.get(position).toString().trim());
        holder.na.setText(name.get(position).toString().trim());
        holder.ag.setText(age.get(position).toString().trim());
    }


    @Override
    public int getItemCount() {
        return disease.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dis,na,ag,med;
        Spinner spinner;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spinner=itemView.findViewById(R.id.m);
            dis=itemView.findViewById(R.id.dis);
            med=itemView.findViewById(R.id.med);
            na=itemView.findViewById(R.id.n);
            ag=itemView.findViewById(R.id.ag);

        }
    }
}
