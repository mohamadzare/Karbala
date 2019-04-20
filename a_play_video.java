package com.zare.karbala;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import DataBase.db_class;
import DataBase.field_chat;
import Model.Array_class_get;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_play_video extends Activity {
    private android.widget.TextView textview;

    boolean flag = false;
    List<field_chat> fieldkalas;


    private android.widget.Button button;

    private FullscreenVideoLayout videoLayout;

    String audioFile, voice_chek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_play_video);

        audioFile = getIntent().getStringExtra("addrs").toString().trim();
        voice_chek = getIntent().getStringExtra("voice_text").toString().trim();

        this.button = (Button) findViewById(R.id.button);
//        this.textview = (TextView) findViewById(R.id.textview);
        this.videoLayout = (FullscreenVideoLayout) findViewById(R.id.videoview);

        videoLayout.setActivity(this);
        videoLayout.setShouldAutoplay(false);

        loadVideo();

        db_class aCl_db = new db_class(a_play_video.this);


        flag = aCl_db.ExistFile(audioFile);

        if (!flag) {


        } else {


            fieldkalas = aCl_db.getAll_Chat(audioFile);

            for (field_chat tag : fieldkalas) {

                audioFile = tag.getLink();
            }

        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        videoLayout.resize();
    }

    public void Download(View view) {
        downloadFile();

    }

    public void downloadFile() {

        Random generator = new Random();
        final String x = String.valueOf(generator.nextInt(26) + 10);

        db_class aCl_db = new db_class(a_play_video.this);

        final field_chat cht_fld = new field_chat();

        if (audioFile.toLowerCase().indexOf("storage/emulated".toLowerCase()) == -1) {

            int m = 0;

            DownloadManager.Request request1 = new DownloadManager.Request(Uri.parse(audioFile));

            request1.setDescription("");   //appears the same in Notification bar while downloading

            request1.setTitle(x);

            request1.setVisibleInDownloadsUi(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request1.allowScanningByMediaScanner();
                request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            }
            request1.setDestinationInExternalFilesDir(getApplicationContext(), "/File", x + ".mp4");

            DownloadManager manager1 = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Objects.requireNonNull(manager1).enqueue(request1);
            if (DownloadManager.STATUS_SUCCESSFUL == 8) {

                Toast.makeText(this, "فایل مورد نظر دانلود شده است.", Toast.LENGTH_SHORT).show();


                cht_fld.type = "Video";

                cht_fld.link = Environment.getExternalStorageDirectory().getPath()
                        + "/ " + x + ".mp4";

                cht_fld.from_id = audioFile;

                aCl_db.addchat(cht_fld);

                aCl_db.close();
            }
        } else {

            int m = 0;

        }

    }

    public void loadVideo() {
        Uri videoUri = Uri.parse(audioFile);
        try {
            videoLayout.setVideoURI(videoUri);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset(View v) {
        if (this.videoLayout != null) {
            this.videoLayout.reset();
            loadVideo();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (voice_chek.equalsIgnoreCase("1")) {
            Array_class_get.m_chat.clear();
            startActivity(new Intent(a_play_video.this, a_chat.class));
            finish();
        } else {
            startActivity(new Intent(a_play_video.this, a_detial_video.class));
            finish();
        }

    }
}
