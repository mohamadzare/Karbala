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

import java.util.Locale;

import Adapter.Adapter_detial_gallery;
import Adapter.Adapter_name_vide_gallery;
import Adapter.Adapter_video_gallery;
import Model.Array_class_get;
import Model.sharedprefrencce;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_detial_video extends AppCompatActivity {

    SharedPreferences prefs_s, prefs;

    String lan;

    RecyclerView _Recycle;

    Adapter_video_gallery adapter_get;

    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_detial_video);

        _Recycle = (RecyclerView) findViewById(R.id.d_f_Recycle);

        _Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_detial_video.this, 1);

        _Recycle.setLayoutManager(mLayoutManager);

        int resId = R.anim.layout_animation_slide_from_right;

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_detial_video.this, resId);

        _Recycle.setLayoutAnimation(animation);

        adapter_get = new Adapter_video_gallery(a_detial_video.this, Array_class_get.array_m_SentModel, "1");

        _Recycle.setAdapter(adapter_get);

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
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_detial_video.this, a_gallery.class));
        finish();
    }
}
