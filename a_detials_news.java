package com.zare.karbala;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import Model.Array_class_get;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_detials_news extends AppCompatActivity {

    String id_khabr;
    TextView time, pre_title, title;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_detials_news);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        id_khabr =  (getIntent().getStringExtra("name").toString().trim());

        time = (TextView) findViewById(R.id.time);
        pre_title = (TextView) findViewById(R.id.pre_title);
        title = (TextView) findViewById(R.id.title);
        img = (ImageView) findViewById(R.id.img);

        for (int o = 0; o < Array_class_get.array_Khabar_persian.size(); o++) {
            if (id_khabr.equalsIgnoreCase( String.valueOf(Array_class_get.array_Khabar_persian.get(o).khabarID))) {
                time.setText(Array_class_get.array_Khabar_persian.get(o).date);
                pre_title.setText(Array_class_get.array_Khabar_persian.get(o).khabarTitle);
                title.setText(Array_class_get.array_Khabar_persian.get(o).khabarText);

                Glide.with(a_detials_news.this)
                        //.load(mImageUri) // Load image from assets
                        .load(Array_class_get.array_Khabar_persian.get(o).pics.get(0).picAddress) // Image URL
                        .centerCrop() // Image scale type
                        .crossFade()
                        .override(800,500) // Resize image
                        .into(img);


            }

        }


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_detials_news.this, a_news.class));
        finish();
    }
}
