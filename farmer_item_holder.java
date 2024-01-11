package com.example.fertilizer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class farmer_item_holder extends RecyclerView.ViewHolder {
    ImageView mImgView;
    TextView mTitle,mCost,mType,mFName,mFUpi;
    ImageView delete,edit;

    public farmer_item_holder(@NonNull View itemView) {
        super(itemView);
        this.mImgView = itemView.findViewById(R.id.image);
        this.mTitle = itemView.findViewById(R.id.title);
        this.mType = itemView.findViewById(R.id.type);
        this.mFName = itemView.findViewById(R.id.fname);
        this.mFUpi = itemView.findViewById(R.id.fupi);
        this.mCost = itemView.findViewById(R.id.cost);
        this.delete = itemView.findViewById(R.id.delete_item);
        this.edit = itemView.findViewById(R.id.edit_item);


    }
}
