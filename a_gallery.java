package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Last_News;
import Adapter.Adapter_name_gallery;
import Adapter.Adapter_name_vide_gallery;
import Adapter.VerticalRecyclerViewAdapter;
import Model.ApiService;
import Model.Array_class_get;
import Model.EmamList;
import Model.HadisModel_persian;
import Model.Khabar_arabic;
import Model.Khabar_english;
import Model.Khabar_persian;
import Model.ParameterPics;
import Model._3langKhabar;
import Model.m_authors;
import Model.m_sentNamesVideo;
import Model.name_gallery_per;
import Model.paramGetKhabar;
import Model.sentNames;
import Model.sentNamesVideo;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_gallery extends AppCompatActivity {

    RecyclerView g_Recycle;

    GridLayoutManager mLayoutManager;

    Adapter_name_gallery adapter_get_name;

    RecyclerView f_Recycle;

    GridLayoutManager mLayoutManager_video;

    Adapter_name_vide_gallery adapter_get_name_video;

    String lan,MyToken;

    SharedPreferences prefs_s,prefs;

    TextView film, pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_gallery);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        get_name_pic();

        film = (TextView)findViewById(R.id.film);

        pic = (TextView)findViewById(R.id.pic);


        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
          MyToken = prefs.getString(sharedprefrencce.MyToken, "");

        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {
                film.setText("فيلم");
                pic.setText("صورة");

                String languageToLoad1 = "ar"; // your language

                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();

                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());

            } else if (lan.equalsIgnoreCase("kingdom")) {
                film.setText("Movie");
                pic.setText("Picture");
                String languageToLoad1 = "en"; // your language

                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();

                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());

            } else if (lan.equalsIgnoreCase("iran")) {
                String languageToLoad1 = "fa"; // your language

                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();

                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());

                film.setText("فیلم");
                pic.setText("تصاویر");
            }
        }


        g_Recycle = (RecyclerView) findViewById(R.id.g_Recycle);

        g_Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_gallery.this, 1);

        g_Recycle.setLayoutManager(mLayoutManager);

        f_Recycle = (RecyclerView) findViewById(R.id.f_Recycle);

        f_Recycle.setHasFixedSize(true);

        mLayoutManager_video = new GridLayoutManager(a_gallery.this, 1);

        f_Recycle.setLayoutManager(mLayoutManager_video);

    }

    public void get_name_pic() {
        Array_class_get.array_name_gallery_per.clear();

        final ProgressDialog mDialog = new ProgressDialog(a_gallery.this);

        mDialog.setMessage("");

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
        service.PicGallaryByAlbum(new Callback<sentNames>() {
            @Override
            public void success(sentNames object, Response response) {


                int m = 0;


                prefs_s = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                String My_La = prefs_s.getString(sharedprefrencce.MyToken, "");


                if ((My_La.isEmpty())) {

                    if (object.namePersian.size() > 0) {
                        for (int i = 0; i < object.namePersian.size(); i++) {
                            name_gallery_per galleryPer = new name_gallery_per();

                            galleryPer.name = object.namePersian.get(i);

                            Array_class_get.array_name_gallery_per.add(galleryPer);
                        }
                    }


                } else {

                    SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    lan = prefs.getString(sharedprefrencce.MyToken, "");


                    if (lan.equalsIgnoreCase("arab")) {

                        if (object.nameArabic.size() > 0) {
                            for (int i = 0; i < object.nameArabic.size(); i++) {
                                name_gallery_per galleryPer = new name_gallery_per();

                                galleryPer.name = object.nameArabic.get(i);

                                Array_class_get.array_name_gallery_per.add(galleryPer);
                            }
                        }


                    } else if (lan.equalsIgnoreCase("kingdom")) {
                        if (object.nameEnglish.size() > 0) {
                            for (int i = 0; i < object.nameEnglish.size(); i++) {
                                name_gallery_per galleryPer = new name_gallery_per();

                                galleryPer.name = object.nameEnglish.get(i);

                                Array_class_get.array_name_gallery_per.add(galleryPer);
                            }
                        }
                    } else if (lan.equalsIgnoreCase("iran")) {
                        if (object.namePersian.size() > 0) {
                            for (int i = 0; i < object.namePersian.size(); i++) {
                                name_gallery_per galleryPer = new name_gallery_per();

                                galleryPer.name = object.namePersian.get(i);

                                Array_class_get.array_name_gallery_per.add(galleryPer);
                            }
                        }
                    }
                }


                int resId = R.anim.layout_animation_slide_from_right;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_gallery.this, resId);
                g_Recycle.setLayoutAnimation(animation);

                adapter_get_name = new Adapter_name_gallery(a_gallery.this, Array_class_get.array_name_gallery_per, "1");

                g_Recycle.setAdapter(adapter_get_name);

                mDialog.dismiss();
                get_name_video();
            }

            @Override
            public void failure(RetrofitError error) {
                prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                if ((MyToken.isEmpty())) {
                    String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                    Toast.makeText(a_gallery.this, errorMsg, Toast.LENGTH_SHORT).show();

                } else {

                    SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    lan = prefs.getString(sharedprefrencce.MyToken, "");

                    if (lan.equalsIgnoreCase("arab")) {

                        String errorMsg = "حاول مرة أخرى في وقت لاحق";
                        Toast.makeText(a_gallery.this, errorMsg, Toast.LENGTH_SHORT).show();


                    } else if (lan.equalsIgnoreCase("kingdom")) {
                        String errorMsg = "Try Again Later...";
                        Toast.makeText(a_gallery.this, errorMsg, Toast.LENGTH_SHORT).show();

                    } else if (lan.equalsIgnoreCase("iran")) {

                        String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                        Toast.makeText(a_gallery.this, errorMsg, Toast.LENGTH_SHORT).show();


                    }
                }


                mDialog.dismiss();
            }
        });

    }

    public void get_name_video() {

        Array_class_get.array_m_sentNamesVideo.clear();

        final ProgressDialog mDialog = new ProgressDialog(a_gallery.this);

        mDialog.setMessage("");

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
        service.VideoGallaryByAlbum(new Callback<sentNamesVideo>() {
            @Override
            public void success(sentNamesVideo object, Response response) {


                int m = 0;

                prefs_s = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                String My_La = prefs_s.getString(sharedprefrencce.MyToken, "");

                if ((My_La.isEmpty())) {

                    if (object.namePersian.size() > 0) {
                        for (int i = 0; i < object.namePersian.size(); i++) {
                            m_sentNamesVideo galleryPer = new m_sentNamesVideo();

                            galleryPer.name = object.namePersian.get(i);

                            Array_class_get.array_m_sentNamesVideo.add(galleryPer);
                        }
                    }


                } else {

                    SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    lan = prefs.getString(sharedprefrencce.MyToken, "");


                    if (lan.equalsIgnoreCase("arab")) {

                        if (object.nameArabic.size() > 0) {
                            for (int i = 0; i < object.nameArabic.size(); i++) {
                                m_sentNamesVideo galleryPer = new m_sentNamesVideo();

                                galleryPer.name = object.nameArabic.get(i);

                                Array_class_get.array_m_sentNamesVideo.add(galleryPer);
                            }
                        }


                    } else if (lan.equalsIgnoreCase("kingdom")) {
                        if (object.nameEnglish.size() > 0) {
                            for (int i = 0; i < object.nameEnglish.size(); i++) {
                                m_sentNamesVideo galleryPer = new m_sentNamesVideo();

                                galleryPer.name = object.nameEnglish.get(i);

                                Array_class_get.array_m_sentNamesVideo.add(galleryPer);
                            }
                        }
                    } else if (lan.equalsIgnoreCase("iran")) {
                        if (object.namePersian.size() > 0) {
                            for (int i = 0; i < object.namePersian.size(); i++) {
                                m_sentNamesVideo galleryPer = new m_sentNamesVideo();

                                galleryPer.name = object.namePersian.get(i);

                                Array_class_get.array_m_sentNamesVideo.add(galleryPer);
                            }
                        }
                    }
                }


                int resId = R.anim.layout_animation_slide_from_right;

                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_gallery.this, resId);

                f_Recycle.setLayoutAnimation(animation);

                adapter_get_name_video = new Adapter_name_vide_gallery(a_gallery.this, Array_class_get.array_m_sentNamesVideo, "1");

                f_Recycle.setAdapter(adapter_get_name_video);

                mDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {
                prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                if ((MyToken.isEmpty())) {
                    String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                    Toast.makeText(a_gallery.this, errorMsg, Toast.LENGTH_SHORT).show();

                } else {

                    SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    lan = prefs.getString(sharedprefrencce.MyToken, "");

                    if (lan.equalsIgnoreCase("arab")) {

                        String errorMsg = "حاول مرة أخرى في وقت لاحق";
                        Toast.makeText(a_gallery.this, errorMsg, Toast.LENGTH_SHORT).show();


                    } else if (lan.equalsIgnoreCase("kingdom")) {
                        String errorMsg = "Try Again Later...";
                        Toast.makeText(a_gallery.this, errorMsg, Toast.LENGTH_SHORT).show();

                    } else if (lan.equalsIgnoreCase("iran")) {

                        String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                        Toast.makeText(a_gallery.this, errorMsg, Toast.LENGTH_SHORT).show();


                    }
                }
                mDialog.dismiss();
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_gallery.this, a_Circle_list_menu.class));
        finish();
    }
}
