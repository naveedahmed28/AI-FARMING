package com.example.fertilizer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoHolder extends RecyclerView.ViewHolder {
    //ImageView mImgView;
    TextView mtitle,mtext;
    ImageView imageView;
    YouTubePlayerView youTubePlayerView;
    ImageView delete,edit;


    public VideoHolder(@NonNull View itemView) {
        super(itemView);
        //this.mImgView = itemView.findViewById(R.id.image);
        this.mtitle = itemView.findViewById(R.id.video_title);
        this.mtext = itemView.findViewById(R.id.video_description);
        this.youTubePlayerView = itemView.findViewById(R.id.youtube);
        this.imageView = itemView.findViewById(R.id.image23);
        // this.mtype = itemView.findViewById(R.id.type);
//        this.delete = itemView.findViewById(R.id.delete_item);
//        this.edit = itemView.findViewById(R.id.edit_item);


    }
}
