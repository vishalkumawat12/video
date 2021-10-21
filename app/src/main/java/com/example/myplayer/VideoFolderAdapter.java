package com.example.myplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class VideoFolderAdapter extends RecyclerView.Adapter<VideoFolderAdapter.ViewHolder> {

    Context context;
    static ArrayList<videoModel> foldervideoModelArrayList;
    public VideoFolderAdapter(Context context, ArrayList<videoModel> foldervideoModelArrayList) {
        this.context = context;
        this.foldervideoModelArrayList = foldervideoModelArrayList;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.video_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fileName.setText(foldervideoModelArrayList.get(position).getTitle());
        Glide.with(context).load(new File(foldervideoModelArrayList.get(position).getPath())).into(holder.tham);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PlayerActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("sender","folderIsSending");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foldervideoModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView tham,more;
        TextView fileName,Duration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tham=itemView.findViewById(R.id.them);
            more=itemView.findViewById(R.id.more);
            fileName=itemView.findViewById(R.id.name);
            Duration=itemView.findViewById(R.id.duration);

        }
    }
}

