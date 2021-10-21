package com.example.myplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.myplayer.databinding.ActivityVideoFolderBinding;

import java.util.ArrayList;

public class VideoFolderActivity extends AppCompatActivity {
VideoFolderAdapter videoFolderAdapter;
ActivityVideoFolderBinding binding;
String folderName;
ArrayList<videoModel> videoModelArrayListFolder=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityVideoFolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        folderName=getIntent().getStringExtra("folderName");
        if (folderName!=null){
            videoModelArrayListFolder=getVideoModelArrayList(this,folderName);

        }
        if (videoModelArrayListFolder.size()>0){
            Toast.makeText(this,  videoModelArrayListFolder.size()+"e", Toast.LENGTH_SHORT).show();
            videoFolderAdapter =new VideoFolderAdapter(this,videoModelArrayListFolder);
            binding.folderVideoRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            binding.folderVideoRv.setAdapter(videoFolderAdapter);
        }
    }
    public ArrayList<videoModel> getVideoModelArrayList(Context context,String folderName) {
        ArrayList<videoModel> videoModelArrayListTemp = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME

        };
        String selection=MediaStore.Video.Media.DATA+" like?";
        String[] selectionArg=new String[]{"%"+folderName+"%"};
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArg, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String title = cursor.getString(2);
                String size = cursor.getString(3);


                String dateAdded = cursor.getString(4);
                String duration = cursor.getString(5);
                String fileName = cursor.getString(6);
                String bucketNmae = cursor.getString(7);
                videoModel videoModel1 = new videoModel(id, path, title, fileName, size, dateAdded, duration);
                Log.e("path" ,path );
                if (folderName.endsWith(bucketNmae))

                videoModelArrayListTemp.add(videoModel1);
            }cursor.close();
        }
        return videoModelArrayListTemp;
    }
}