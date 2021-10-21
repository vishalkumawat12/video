package com.example.myplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
    ArrayList<String> folderName;
    ArrayList<videoModel> videoModels;
    Context context;

    public FolderAdapter(ArrayList<String> folderName, ArrayList<videoModel> videoModels, Context context) {
        this.folderName = folderName;
        this.videoModels = videoModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.folder_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index=folderName.get(position).lastIndexOf("/");
        String folname=folderName.get(position).substring(index+1);
    holder.folderName.setText(folname);
    holder.conter.setText(String.valueOf(NumberOfFiles(folderName.get(position))));
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent =new Intent(context,VideoFolderActivity.class);
        intent.putExtra("folderName",folderName.get(position));
        intent.putExtra("sender","foldersend");
        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return folderName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView folderName,conter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName=itemView.findViewById(R.id.folName);
            conter=itemView.findViewById(R.id.child);
        }
    }
    int NumberOfFiles(String name){
        int countFile=0;
        for (videoModel v:videoModels
             ) {

            if (v.getPath().substring(0,v.getPath().lastIndexOf("/")).endsWith(name)){
                countFile++;
            }

        }
        return countFile;

    }
}
