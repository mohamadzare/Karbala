package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import Adapter.Adapter_qoran_title;
import Model.ApiService;
import Model.Array_class_get;
import Model.Khabar_arabic;
import Model.Khabar_english;
import Model.Khabar_persian;
import Model.M_mobes;
import Model.SignerSore_Arabic;
import Model.SignerSore_English;
import Model.SignerSore_Persian;
import Model._3langKhabar;
import Model.paramGetKhabar;
import Model.qrReader3Lang;
import Model.qrReader3LangNewGet;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_qoran extends AppCompatActivity {

    RecyclerView last_news_Recycle;

    Adapter_qoran_title adapter_get_last_news;

    RecyclerView.LayoutManager mLayoutManager;

    String lan;

    TextView amew,samew;
    SharedPreferences prefs_s,prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_qoran);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");

        get_goran();

        amew = (TextView) findViewById(R.id.amew);
        samew = (TextView) findViewById(R.id.samew);

        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {

                amew.setText("راوي");
                samew.setText("السورة");
            } else if (lan.equalsIgnoreCase("kingdom")) {
                amew.setText("Narrator");
                samew.setText("Sura");

            } else if (lan.equalsIgnoreCase("iran")) {

                amew.setText("راوی");
                samew.setText("سوره");
            }

        }
        last_news_Recycle = (RecyclerView) findViewById(R.id.qoran_Recycle);

        last_news_Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_qoran.this, 1);

        last_news_Recycle.setLayoutManager(mLayoutManager);
    }
    public void get_goran() {

        Array_class_get.SignerSore_Persian.clear();

        final ProgressDialog mDialog = new ProgressDialog(a_qoran.this);

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
        service.qrReader(new Callback<qrReader3LangNewGet>() {
            @Override
            public void success(qrReader3LangNewGet object, Response response) {

                int m = 0;
                if(object != null)
                {
                    prefs_s = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    String My_La = prefs_s.getString(sharedprefrencce.MyToken, "");


                    if ((My_La.isEmpty())) {

                        if (object.persian.size() > 0) {
                            for (int i = 0; i < object.persian.size(); i++) {

                                SignerSore_Persian kh_per = new SignerSore_Persian();
                                kh_per.id = object.persian.get(i).id;
                                kh_per.signerName = object.persian.get(i).signerName;
                                kh_per.SoreName = object.persian.get(i).SoreName;
                                Array_class_get.SignerSore_Persian.add(kh_per);

                            }
                        }


                    } else {

                        SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                        lan = prefs.getString(sharedprefrencce.MyToken, "");


                        if (lan.equalsIgnoreCase("arab")) {

                            if (object.Arabic.size() > 0) {
                                for (int i = 0; i < object.Arabic.size(); i++) {
                                    SignerSore_Persian kh_per = new SignerSore_Persian();
                                    kh_per.id = object.Arabic.get(i).id;
                                    kh_per.signerName = object.Arabic.get(i).signerName;
                                    kh_per.SoreName = object.Arabic.get(i).SoreName;
                                    Array_class_get.SignerSore_Persian.add(kh_per);

                                }
                            }

                        } else if (lan.equalsIgnoreCase("kingdom")) {
                            if (object.English.size() > 0) {
                                for (int i = 0; i < object.English.size(); i++) {

                                    SignerSore_Persian kh_per = new SignerSore_Persian();
                                    kh_per.id = object.English.get(i).id;
                                    kh_per.signerName = object.English.get(i).signerName;
                                    kh_per.SoreName = object.English.get(i).SoreName;

                                    Array_class_get.SignerSore_Persian.add(kh_per);

                                }
                            }
                        } else if (lan.equalsIgnoreCase("iran")) {

                            if (object.persian.size() > 0) {
                                for (int i = 0; i < object.persian.size(); i++) {
                                    SignerSore_Persian kh_per = new SignerSore_Persian();
                                    kh_per.id = object.persian.get(i).id;
                                    kh_per.signerName = object.persian.get(i).signerName;
                                    kh_per.SoreName = object.persian.get(i).SoreName;

                                    Array_class_get.SignerSore_Persian.add(kh_per);

                                }
                            }
                        }
                    }


//                for (int i = 0; i < object.Arabic.size(); i++) {
//
//                    SignerSore_Arabic kh_per = new SignerSore_Arabic();
//                    kh_per.id = object.Arabic.get(i).id;
//                    kh_per.signerName = object.Arabic.get(i).signerName;
//                    kh_per.SoreName = object.Arabic.get(i).SoreName;
//
//                    Array_class_get.array_SignerSore_Arabic.add(kh_per);
//
//                }
//                for (int i = 0; i < object.English.size(); i++) {
//
//                    SignerSore_English kh_per = new SignerSore_English();
//                    kh_per.id = object.English.get(i).id;
//                    kh_per.signerName = object.English.get(i).signerName;
//                    kh_per.SoreName = object.English.get(i).SoreName;
//                    Array_class_get.SignerSore_English.add(kh_per);
//
//                }


                    int resId = R.anim.layout_animation_scroll_from_bottom;

                    LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_qoran.this, resId);

                    last_news_Recycle.setLayoutAnimation(animation);

                    adapter_get_last_news = new Adapter_qoran_title(a_qoran.this, Array_class_get.SignerSore_Persian, "1");

                    last_news_Recycle.setAdapter(adapter_get_last_news);

                    mDialog.dismiss();
                    try {

                    } catch (Exception e) {
                    }
                }


                mDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_qoran.this, errorMsg, Toast.LENGTH_SHORT).show();

                mDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(a_qoran.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
