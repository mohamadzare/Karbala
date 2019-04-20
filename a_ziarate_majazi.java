package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Last_News;
import Adapter.Adapter_List_ziarat;
import Model.ApiService;
import Model.Array_class_get;
import Model.Khabar_arabic;
import Model.Khabar_english;
import Model.Khabar_persian;
import Model.PlaceIdName_Arabic;
import Model.PlaceIdName_English;
import Model._3langKhabar;
import Model.paramGetKhabar;
import Model.ziaratMajazi2Lang;
import bg.devlabs.fullscreenvideoview.FullscreenVideoView;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class a_ziarate_majazi extends AppCompatActivity {

    RecyclerView last_news_Recycle;

    Adapter_List_ziarat adapter;

    RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_ziarate_majazi);



        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        get_ziarat();

        last_news_Recycle = (RecyclerView) findViewById(R.id.ziarat_Recycle);

        last_news_Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_ziarate_majazi.this, 1);

        last_news_Recycle.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_ziarate_majazi.this, a_Circle_list_menu.class));
        finish();
    }

    public void get_ziarat() {
        Array_class_get.m_PlaceIdName_Arabic.clear();
        Array_class_get.m_PlaceIdName_English.clear();
        final ProgressDialog mDialog = new ProgressDialog(a_ziarate_majazi.this);

        mDialog.setMessage(" ");

        mDialog.setCancelable(false);

        mDialog.show();


        final OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.setReadTimeout(600, TimeUnit.SECONDS);

        okHttpClient.setConnectTimeout(600, TimeUnit.SECONDS);

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-encoding", "gzip");
                request.addHeader("Accept", "application/json");
            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.mazandatabat.org:3001")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .build();
        ApiService service = restAdapter.create(ApiService.class);
        service.ziaratMajazi(new Callback<ziaratMajazi2Lang>() {
            @Override
            public void success(ziaratMajazi2Lang object, Response response) {

                int m = 0;

                for (int ar = 0; ar < object.Arabic.size(); ar++) {
                    PlaceIdName_Arabic arabic = new PlaceIdName_Arabic();
                    arabic.PicUrl = object.Arabic.get(ar).PicUrl;
                    arabic.placeID = object.Arabic.get(ar).placeID;
                    arabic.placeName = object.Arabic.get(ar).placeName;

                    Array_class_get.m_PlaceIdName_Arabic.add(arabic);
                }
                for (int are = 0; are < object.English.size(); are++) {
                    PlaceIdName_English english = new PlaceIdName_English();
                    english.PicUrl = object.English.get(are).PicUrl;
                    english.placeID = object.English.get(are).placeID;
                    english.placeName = object.English.get(are).placeName;
                    Array_class_get.m_PlaceIdName_English.add(english);
                }



                int resId = R.anim.layout_animation_scroll_from_bottom;

                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_ziarate_majazi.this, resId);

                last_news_Recycle.setLayoutAnimation(animation);

                adapter = new Adapter_List_ziarat(a_ziarate_majazi.this,    Array_class_get.m_PlaceIdName_Arabic, "1");

                last_news_Recycle.setAdapter(adapter);

                mDialog.dismiss();
                try {

                } catch (Exception e) {
                }

                mDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {

                mDialog.dismiss();
            }
        });

    }


}
