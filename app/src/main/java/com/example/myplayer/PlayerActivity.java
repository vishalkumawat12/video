package com.example.myplayer;

import static com.example.myplayer.VideoAdapter.videoModelArrayList;
import static com.example.myplayer.VideoFolderAdapter.foldervideoModelArrayList;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myplayer.databinding.ActivityPlayerBinding;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    ActivityPlayerBinding binding;
    AlertDialog.Builder builder;
    int position=-1;
    SimpleExoPlayer simpleExoPlayer;
    ArrayList<videoModel> myFile=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setFullScreen();
        getSupportActionBar().hide();
        binding=ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String sender=getIntent().getStringExtra("sender");

        position=getIntent().getIntExtra("position",-1);
        if (sender.equals("folderIsSending")){
            myFile=foldervideoModelArrayList;
        }
        else {
            myFile=videoModelArrayList;
        }
        try {


            String path = myFile.get(position).getPath();
            MediaMetadataRetriever mRetriever = new MediaMetadataRetriever();

            mRetriever.setDataSource(path);
            Bitmap frame = mRetriever.getFrameAtTime();

            int width = frame.getWidth();
            int height = frame.getHeight();
            float it=(float)width/height;
            if (1.2<it) {

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }





            if (path != null) {
                Uri uri = Uri.parse(path);
                simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
                DataSource.Factory factory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "My App name"));
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory, extractorsFactory).createMediaSource(uri);
                binding.Exoplayer.setPlayer(simpleExoPlayer);
                binding.Exoplayer.setKeepScreenOn(true);
                simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.setPlayWhenReady(true);
                simpleExoPlayer.addListener(new Player.Listener() {


                });

            }
        }catch (Exception e){
            Toast.makeText(PlayerActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            setError();
        }

    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
   public void setError(){
        builder
                = new AlertDialog
                .Builder(PlayerActivity.this);
        builder.setTitle("Error");
        builder.setMessage("Something went wrong !!!" );
        builder.setCancelable(false);
        builder
                .setPositiveButton(
                        "ok",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                finish();
                            }
                        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        simpleExoPlayer.stop();
    }
}