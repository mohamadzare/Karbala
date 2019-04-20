package com.zare.karbala;

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

import java.util.Locale;

import Adapter.Adapter_detialz_qoran;
import Adapter.Adapter_qoran_title;
import Model.Array_class_get;
import Model.sharedprefrencce;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_detialz_qoran extends AppCompatActivity {

    TextView amew, samew,sacmew;
    SharedPreferences prefs_s, prefs;
    RecyclerView _Recycle;
    String lan;

    Adapter_detialz_qoran adapter_get;

    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_detialz_qoran);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        _Recycle = (RecyclerView) findViewById(R.id.qoran_khan_Recycle);

        _Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_detialz_qoran.this, 1);

        _Recycle.setLayoutManager(mLayoutManager);


        int resId = R.anim.layout_animation_scroll_from_bottom;

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_detialz_qoran.this, resId);

        _Recycle.setLayoutAnimation(animation);

        adapter_get = new Adapter_detialz_qoran(a_detialz_qoran.this, Array_class_get.m_qrReaderModel, "1");

        _Recycle.setAdapter(adapter_get);
        amew = (TextView) findViewById(R.id.amew);
        samew = (TextView) findViewById(R.id.samew);
        sacmew = (TextView) findViewById(R.id.sacmew);
        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");

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

                amew.setText("راوي");
                sacmew.setText("جز");
                samew.setText("السورة");
            } else if (lan.equalsIgnoreCase("kingdom")) {
                amew.setText("Narrator");
                samew.setText("Sura");
                sacmew.setText("Component");
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

                amew.setText("راوی");
                samew.setText("سوره");
                sacmew.setText("جز");
            }

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_detialz_qoran.this, a_qoran.class));
        finish();
    }
}
