package com.zare.karbala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Locale;

import Adapter.Adapter_Last_News;
import Adapter.Adapter_detial_gallery;
import Model.Array_class_get;
import Model.sharedprefrencce;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_detial_gallery_activity extends AppCompatActivity {

    RecyclerView _Recycle;

    Adapter_detial_gallery adapter_get;

    RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences prefs_s, prefs;

    String lan,MyToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_detial_activity);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
         MyToken = prefs.getString(sharedprefrencce.MyToken, "");

        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {
                String languageToLoad1 = "ar"; // your language

                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();

                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());


            } else if (lan.equalsIgnoreCase("kingdom")) {
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


            }
        }

        _Recycle = (RecyclerView) findViewById(R.id.d_g_Recycle);

        _Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_detial_gallery_activity.this, 2);

        _Recycle.setLayoutManager(mLayoutManager);

        adapter_get = new Adapter_detial_gallery(a_detial_gallery_activity.this,   Array_class_get.array_m_SentModel, "1");

        _Recycle.setAdapter(adapter_get);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_detial_gallery_activity.this,a_gallery.class));
        finish();
    }
}
