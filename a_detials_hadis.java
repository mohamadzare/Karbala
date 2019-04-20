package com.zare.karbala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.Locale;

import Adapter.Adapter_authors;
import Adapter.Adapter_detials_hadis;
import Model.Array_class_get;
import Model.Last_HadisModel;
import Model.sharedprefrencce;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_detials_hadis extends AppCompatActivity {

    TextView authors_txt;

    String time,authors_nam;

    RecyclerView last_news_Recycle;

      Adapter_detials_hadis adapter_au;

    RecyclerView.LayoutManager mLayoutManager;

    String lan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_detials_hadis);

        Array_class_get.array_hadis.clear();

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

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




        time = getIntent().getStringExtra("name").toString().trim();
        authors_nam = getIntent().getStringExtra("authors").toString().trim();

        //authors_txt.setText(authors_nam);

        for (int i = 0; i < Array_class_get.array_hadis_persian.size(); i++) {

            if(authors_nam.equalsIgnoreCase(Array_class_get.array_hadis_persian.get(i).authors))
            {
                if (time.equalsIgnoreCase(Array_class_get.array_hadis_persian.get(i).time)) {
                    Last_HadisModel model = new Last_HadisModel();
                    model.time = Array_class_get.array_hadis_persian.get(i).time;
                    model.Title = Array_class_get.array_hadis_persian.get(i).Title;
                    model.text = Array_class_get.array_hadis_persian.get(i).text;
                    model.tags = Array_class_get.array_hadis_persian.get(i).tags;
                    model.date = Array_class_get.array_hadis_persian.get(i).date;
                    model.subject = Array_class_get.array_hadis_persian.get(i).subject;
                    Array_class_get.array_hadis.add(model);

                }
            }

        }

        last_news_Recycle = (RecyclerView) findViewById(R.id.d_Recycle);

        last_news_Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_detials_hadis.this, 1);

        last_news_Recycle.setLayoutManager(mLayoutManager);

        adapter_au = new Adapter_detials_hadis(a_detials_hadis.this, Array_class_get.array_hadis, "1");

        last_news_Recycle.setAdapter(adapter_au);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_detials_hadis.this,a_hadis.class));
        finish();
    }
}
