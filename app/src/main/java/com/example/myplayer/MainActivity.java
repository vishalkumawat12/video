package com.example.myplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myplayer.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUESTED_CODE = 120;
    ActivityMainBinding binding;
    static ArrayList<videoModel> videoModelArrayList = new ArrayList<>();
    static ArrayList<String> folderList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        permission();
        binding.bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.folderList:
                        Toast.makeText(MainActivity.this, "folder", Toast.LENGTH_SHORT).show();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.replaceFragment,new FolderFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        break;
                    case R.id.FileList:
                        Toast.makeText(MainActivity.this, "file", Toast.LENGTH_SHORT).show();
                        FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.replaceFragment,new FileFragment());
                        fragmentTransaction1.commit();
                        item.setChecked(true);
                        break;
                }
                return false;
            }
        });
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTED_CODE);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.replaceFragment,new FileFragment());
            fragmentTransaction.commit();
        } else {
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
            videoModelArrayList=getVideoModelArrayList(this);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.replaceFragment,new FolderFragment());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTED_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "permition grantrd", Toast.LENGTH_SHORT).show();
                videoModelArrayList=getVideoModelArrayList(this);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTED_CODE);

            }
        }

    }

    public ArrayList<videoModel> getVideoModelArrayList(Context context) {
        ArrayList<videoModel> videoModelArrayListTemp = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME

        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String title = cursor.getString(2);
                String size = cursor.getString(3);


                String dateAdded = cursor.getString(4);
                String duration = cursor.getString(5);
                String fileName = cursor.getString(6);
                videoModel videoModel1 = new videoModel(id, path, title, fileName, size, dateAdded, duration);
                Log.e("path" ,path );
                int slashIndex=path.lastIndexOf("/");
                String sunString=path.substring(0,slashIndex);

                if (!folderList.contains(sunString)){
                    folderList.add(sunString);
                }
                videoModelArrayListTemp.add(videoModel1);
            }cursor.close();
        }
        return videoModelArrayListTemp;
    }
}
