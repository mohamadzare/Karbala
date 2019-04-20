package com.zare.karbala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import Adapter.Adapter_Last_News;
import Adapter.Adapter_Title_Note;
import DataBase.db_class;
import DataBase.feilddb;
import Model.Array_class_get;
import Model.M_Notpad;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_notpad extends AppCompatActivity {

    List<feilddb> obj;

    db_class aClass;

    ImageView add;

    RecyclerView last_news_Recycle;

    Adapter_Title_Note adapter_get_last_news;

    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_notpad);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(a_notpad.this, add_note.class));
                finish();
            }
        });

        aClass = new db_class(a_notpad.this);


        getInspection();


        last_news_Recycle = (RecyclerView) findViewById(R.id.a_notpad);

        last_news_Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_notpad.this, 2);

        last_news_Recycle.setLayoutManager(mLayoutManager);


        int resId = R.anim.layout_animation_scroll_from_bottom;

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_notpad.this, resId);

        last_news_Recycle.setLayoutAnimation(animation);

        adapter_get_last_news = new Adapter_Title_Note(a_notpad.this, Array_class_get.array_M_Notpad, "1");

        last_news_Recycle.setAdapter(adapter_get_last_news);

    }


    public void getInspection() {
        Array_class_get.array_M_Notpad.clear();
        obj = aClass.getAll();
        for (feilddb tag : obj) {
            M_Notpad m_inspection = new M_Notpad();
            m_inspection.id = tag.getId();
            m_inspection.date = tag.getDate();
            m_inspection.time = tag.getTime();
            m_inspection.text = tag.getText();
            m_inspection.title = tag.getTitle();

            Array_class_get.array_M_Notpad.add(m_inspection);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_notpad.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
