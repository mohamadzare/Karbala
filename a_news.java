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
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Archive;
import Adapter.Adapter_Last_News;
import Adapter.CalendarTool;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.Khabar_arabic;
import Model.Khabar_english;
import Model.Khabar_persian;
import Model.Last_News;
import Model.M_mobes;
import Model._3langKhabar;
import Model.m_HadisModel_arabic;
import Model.mokebs;
import Model.paramAllMokeb;
import Model.paramGetKhabar;
import Model.postKhabar;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_news extends AppCompatActivity implements SearchView.OnQueryTextListener {


    RecyclerView last_news_Recycle,Archive_Recycle;

    Adapter_Last_News adapter_get_last_news;

    RecyclerView.LayoutManager mLayoutManager;

    Adapter_Archive adapter_archive;

    String lan,StateName;

    SharedPreferences prefs_s;
    SearchView mSearch_View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_news);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        mSearch_View = (SearchView) findViewById(R.id.mSearch_View);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                StateName = "AllState";

            } else {
                StateName = extras.getString("StateName");

            }
        } else {
           // StateName = extras.getString("StateName");
        }


        get_news();


        last_news_Recycle = (RecyclerView) findViewById(R.id.Curse_Recycle);

        last_news_Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_news.this, 1);

        last_news_Recycle.setLayoutManager(mLayoutManager);




        Archive_Recycle = (RecyclerView) findViewById(R.id.Archive_Recycle);

        Archive_Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_news.this, 1);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        Archive_Recycle.setLayoutManager(layoutManager);

        adapter_archive = new Adapter_Archive(a_news.this, Array_class_get.array_m_Ostan);

        Archive_Recycle.setAdapter(adapter_archive);

        setupSearchView();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private void setupSearchView() {
        mSearch_View.setIconifiedByDefault(false);
        mSearch_View.setOnQueryTextListener(this);
        mSearch_View.setSubmitButtonEnabled(true);

    }

    public boolean onQueryTextChange(String newText) {
        adapter_get_last_news.filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    public void get_news() {

        Array_class_get.array_Last_News.clear();

        Array_class_get.array_Khabar_persian.clear();

        Array_class_get.array_m_Khabar_arabic.clear();

        Array_class_get.array_Khabar_english.clear();


        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");
        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        Calendar c = Calendar.getInstance();

        int yeard = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        CalendarTool ct = new CalendarTool(yeard, month, day);

        String Day_Month_Year = ct.getIranianDate();

        paramGetKhabar mokeb = new paramGetKhabar();

        mokeb.token = MyToken;

         if (MyMasol.equalsIgnoreCase("true"))
        {
            mokeb.ISmasol = true;
        }
        else
        {
            mokeb.ISmasol = false;
        }


        mokeb.StateName = StateName;


        final ProgressDialog mDialog = new ProgressDialog(a_news.this);

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
        service.khabarByState(mokeb, new Callback<List<_3langKhabar>>() {
            @Override
            public void success(List<_3langKhabar> object, Response response) {

                int m = 0;

                mDialog.dismiss();

                if (object == null)
                {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_news.this);
                    dialogBuilder
                            .withTitle("پیغام")
                            .withMessage("اطلاعاتی در دسترس نیست...")
                            .show();
                }
                else
                {
                    for (int i = 0; i < object.size(); i++) {

                        Khabar_persian kh_per = new Khabar_persian();
                        kh_per.author = object.get(i).persian.author;
                        kh_per.date = object.get(i).persian.date;
                        kh_per.khabarText = object.get(i).persian.khabarText;
                        kh_per.khabarTitle = object.get(i).persian.khabarTitle;
                        kh_per.khabarID = object.get(i).persian.khabarID;
                        kh_per.masolID = object.get(i).persian.masolID;
                        kh_per.pics = object.get(i).persian.pics;
                        Array_class_get.array_Khabar_persian.add(kh_per);


                        Khabar_arabic kh_arabic = new Khabar_arabic();
                        kh_arabic.author = object.get(i).arabic.author;
                        kh_arabic.date = object.get(i).arabic.date;
                        kh_arabic.khabarText = object.get(i).arabic.khabarText;
                        kh_arabic.khabarTitle = object.get(i).arabic.khabarTitle;
                        kh_arabic.khabarID = object.get(i).arabic.khabarID;
                        kh_arabic.masolID = object.get(i).arabic.masolID;
                        kh_arabic.pics = object.get(i).arabic.pics;
                        Array_class_get.array_m_Khabar_arabic.add(kh_arabic);

                        Khabar_english kh_eng = new Khabar_english();
                        kh_eng.author = object.get(i).english.author;
                        kh_eng.date = object.get(i).english.date;
                        kh_eng.khabarText = object.get(i).english.khabarText;
                        kh_eng.khabarTitle = object.get(i).english.khabarTitle;
                        kh_eng.khabarID = object.get(i).english.khabarID;
                        kh_eng.masolID = object.get(i).english.masolID;
                        kh_eng.pics = object.get(i).english.pics;
                        Array_class_get.array_Khabar_english.add(kh_eng);

                    }


                    prefs_s = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    String My_La = prefs_s.getString(sharedprefrencce.MyToken, "");

                    if ((My_La.isEmpty())) {
                        int resId = R.anim.layout_animation_scroll_from_bottom;

                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_news.this, resId);

                        last_news_Recycle.setLayoutAnimation(animation);

                        adapter_get_last_news = new Adapter_Last_News(a_news.this, Array_class_get.array_Khabar_persian, "1");

                        last_news_Recycle.setAdapter(adapter_get_last_news);

                    } else {

                        SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                        lan = prefs.getString(sharedprefrencce.MyToken, "");


                        if (lan.equalsIgnoreCase("arab")) {

                            Array_class_get.array_Khabar_persian.clear();

                            for (int pe = 0; pe < Array_class_get.array_m_Khabar_arabic.size(); pe++) {
                                Khabar_persian kh_per = new Khabar_persian();
                                kh_per.author = Array_class_get.array_m_Khabar_arabic.get(pe).author;
                                kh_per.date = Array_class_get.array_m_Khabar_arabic.get(pe).date;
                                kh_per.khabarText = Array_class_get.array_m_Khabar_arabic.get(pe).khabarText;
                                kh_per.khabarTitle = Array_class_get.array_m_Khabar_arabic.get(pe).khabarTitle;
                                kh_per.khabarID = Array_class_get.array_m_Khabar_arabic.get(pe).khabarID;
                                kh_per.masolID = Array_class_get.array_m_Khabar_arabic.get(pe).masolID;
                                kh_per.pics = Array_class_get.array_m_Khabar_arabic.get(pe).pics;
                                Array_class_get.array_Khabar_persian.add(kh_per);
                            }


                            int resId = R.anim.layout_animation_scroll_from_bottom;

                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_news.this, resId);

                            last_news_Recycle.setLayoutAnimation(animation);

                            adapter_get_last_news = new Adapter_Last_News(a_news.this, Array_class_get.array_Khabar_persian, "1");

                            last_news_Recycle.setAdapter(adapter_get_last_news);


                        } else if (lan.equalsIgnoreCase("kingdom")) {

                            Array_class_get.array_Khabar_persian.clear();

                            for (int pe = 0; pe < Array_class_get.array_Khabar_english.size(); pe++) {
                                Khabar_persian kh_per = new Khabar_persian();
                                kh_per.author = Array_class_get.array_Khabar_english.get(pe).author;
                                kh_per.date = Array_class_get.array_Khabar_english.get(pe).date;
                                kh_per.khabarText = Array_class_get.array_Khabar_english.get(pe).khabarText;
                                kh_per.khabarTitle = Array_class_get.array_Khabar_english.get(pe).khabarTitle;
                                kh_per.khabarID = Array_class_get.array_Khabar_english.get(pe).khabarID;
                                kh_per.masolID = Array_class_get.array_Khabar_english.get(pe).masolID;
                                kh_per.pics = Array_class_get.array_Khabar_english.get(pe).pics;
                                Array_class_get.array_Khabar_persian.add(kh_per);
                            }


                            int resId = R.anim.layout_animation_scroll_from_bottom;

                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_news.this, resId);

                            last_news_Recycle.setLayoutAnimation(animation);

                            adapter_get_last_news = new Adapter_Last_News(a_news.this, Array_class_get.array_Khabar_persian, "1");

                            last_news_Recycle.setAdapter(adapter_get_last_news);


                        } else if (lan.equalsIgnoreCase("iran")) {


                            int resId = R.anim.layout_animation_scroll_from_bottom;

                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_news.this, resId);

                            last_news_Recycle.setLayoutAnimation(animation);

                            adapter_get_last_news = new Adapter_Last_News(a_news.this, Array_class_get.array_Khabar_persian, "1");

                            last_news_Recycle.setAdapter(adapter_get_last_news);


                        }
                    }
                }







                try {

                } catch (Exception e) {
                }

                mDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_news.this, errorMsg, Toast.LENGTH_SHORT).show();

                mDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(a_news.this, a_Circle_list_menu.class));
        finish();
    }
}
