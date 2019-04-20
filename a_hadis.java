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
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.CalendarTool;
import Adapter.VerticalRecyclerViewAdapter;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.EmamList;
import Model.HadisModel;
import Model.HadisModel_arabic;
import Model.HadisModel_english;
import Model.HadisModel_persian;
import Model.HorizontalModel;

import Model.VerticalModel;
import Model._3LangHadis;
import Model.m_HadisModel_arabic;
import Model.m_authors;
import Model.paramGetHadis;
import Model.paramGetKhabar;
import Model.postKhabar;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_hadis extends AppCompatActivity {

    m_HadisModel_arabic arabic1;

    HadisModel_english english;

    HadisModel_persian persian;

    m_authors m_aurs;

    RecyclerView last_news_Recycle;

    VerticalRecyclerViewAdapter adapter_au;

    RecyclerView.LayoutManager mLayoutManager;

    List<String> get_name_p = new ArrayList<>();

    ArrayList<VerticalModel> mArrayList = new ArrayList<>();

    String lan;

    SharedPreferences prefs_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_hadis);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        get_emam();


//        last_news_Recycle = (RecyclerView) findViewById(R.id.a_Recycle);
//
//        last_news_Recycle.setHasFixedSize(true);
//
//        mLayoutManager = new GridLayoutManager(a_hadis.this, 1);
//
//        last_news_Recycle.setLayoutManager(mLayoutManager);
//        //Curse_Recycle.setLayoutManager(new LinearLayoutManager(this));

        last_news_Recycle = (RecyclerView) findViewById(R.id.a_Recycle);

        last_news_Recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter_au = new VerticalRecyclerViewAdapter(this, mArrayList);

        last_news_Recycle.setAdapter(adapter_au);


    }

    private void setDataOnVerticalRecyclerView() {
        for (int i = 0; i < Array_class_get.array_m_authors.size(); i++) {

            VerticalModel mVerticalModel = new VerticalModel();

            mVerticalModel.setTitle(Array_class_get.array_m_authors.get(i).authors);

            ArrayList<HorizontalModel> arrayList = new ArrayList<>();

            for (int j = 0; j < Array_class_get.array_hadis_persian.size(); j++) {

                if ((Array_class_get.array_m_authors.get(i).authors).equalsIgnoreCase(Array_class_get.array_hadis_persian.get(j).authors)) {

                    HorizontalModel mHorizontalModel = new HorizontalModel();

                    mHorizontalModel.setDescription(Array_class_get.array_hadis_persian.get(j).subject);

                    mHorizontalModel.setTime(Array_class_get.array_hadis_persian.get(j).time);

                    mHorizontalModel.setText(Array_class_get.array_hadis_persian.get(j).text);

                    mHorizontalModel.setAuthors(Array_class_get.array_hadis_persian.get(j).authors);

                    // mHorizontalModel.setId(Array_list_class.Menu_Subtitle.get(j).id_menu);

                    arrayList.add(mHorizontalModel);
                }

            }

            mVerticalModel.setArrayList(arrayList);

            mArrayList.add(mVerticalModel);

        }
        adapter_au.notifyDataSetChanged();
    }

    public void get_emam() {
        Array_class_get.array_m_authors.clear();

        final ProgressDialog mDialog = new ProgressDialog(a_hadis.this);

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
        service.getEmamname(new Callback<EmamList>() {
            @Override
            public void success(EmamList object, Response response) {


                prefs_s = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                String My_La = prefs_s.getString(sharedprefrencce.MyToken, "");


                if ((My_La.isEmpty())) {

                    for (int i = 0; i < object.persian.size(); i++) {
                        m_aurs = new m_authors();
                        m_aurs.authors = object.persian.get(i);
                        Array_class_get.array_m_authors.add(m_aurs);

                    }

                } else {

                    SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    lan = prefs.getString(sharedprefrencce.MyToken, "");


                    if (lan.equalsIgnoreCase("arab")) {
                        for (int i = 0; i < object.Arabic.size(); i++) {
                            m_aurs = new m_authors();
                            m_aurs.authors = object.Arabic.get(i);
                            Array_class_get.array_m_authors.add(m_aurs);

                        }

                    } else if (lan.equalsIgnoreCase("kingdom")) {

                        for (int i = 0; i < object.English.size(); i++) {
                            m_aurs = new m_authors();
                            m_aurs.authors = object.English.get(i);
                            Array_class_get.array_m_authors.add(m_aurs);

                        }
                    } else if (lan.equalsIgnoreCase("iran")) {

                        for (int i = 0; i < object.persian.size(); i++) {
                            m_aurs = new m_authors();
                            m_aurs.authors = object.persian.get(i);
                            Array_class_get.array_m_authors.add(m_aurs);

                        }
                    }
                }


                get_hadis();

                mDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_hadis.this, errorMsg, Toast.LENGTH_SHORT).show();

                mDialog.dismiss();
            }
        });

    }

    public void get_hadis() {
        Array_class_get.array_hadis_persian.clear();
        Calendar c = Calendar.getInstance();

        int yeard = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        CalendarTool ct = new CalendarTool(yeard, month, day);

        String Day_Month_Year = ct.getIranianDate();

        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        Array_class_get.array_hadis_persian.clear();

        paramGetHadis mokeb = new paramGetHadis();

        mokeb.token = MyToken;

        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true"))
        {
            mokeb.masolID = true;
        }
        else
        {
            mokeb.masolID = false;
        }


        final ProgressDialog mDialog = new ProgressDialog(a_hadis.this);

        mDialog.setMessage("شکیبا باشید");

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
        service.hadis(mokeb, new Callback<List<_3LangHadis>>() {
            @Override
            public void success(List<_3LangHadis> object, Response response) {

                int m = 0;

                if(object == null)
                { mDialog.dismiss();
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_hadis.this);
                    dialogBuilder
                            .withTitle("پیغام")
                            .withMessage("اطلاعاتی در دسترس نیست...")
                            .show();}
                else {
                    try {


                        prefs_s = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                        String My_La = prefs_s.getString(sharedprefrencce.MyToken, "");


                        if ((My_La.isEmpty())) {

                            for (int i = 0; i < object.size(); i++) {


                                persian = new HadisModel_persian();
                                persian.hadisID = object.get(i).persian.hadisID;
                                persian.Title = object.get(i).persian.Title;
                                persian.text = object.get(i).persian.text;
                                persian.date = object.get(i).persian.date;
                                persian.time = object.get(i).persian.time;
                                persian.subject = object.get(i).persian.subject;
                                persian.tags = object.get(i).persian.tags;
                                persian.authors = object.get(i).persian.authors;
                                persian.MasolID = object.get(i).persian.getMasolID();

                                Array_class_get.array_hadis_persian.add(persian);
                            }

                        } else {

                            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                            lan = prefs.getString(sharedprefrencce.MyToken, "");


                            if (lan.equalsIgnoreCase("arab")) {
                                for (int i = 0; i < object.size(); i++) {

                                    persian = new HadisModel_persian();
                                    persian.hadisID = object.get(i).arabic.hadisID;
                                    persian.Title = object.get(i).arabic.Title;
                                    persian.text = object.get(i).arabic.text;
                                    persian.date = object.get(i).arabic.date;
                                    persian.time = object.get(i).arabic.time;
                                    persian.subject = object.get(i).arabic.subject;
                                    persian.tags = object.get(i).arabic.tags;
                                    persian.authors = object.get(i).arabic.authors;
                                    persian.MasolID = object.get(i).arabic.getMasolID();

                                    Array_class_get.array_hadis_persian.add(persian);
                                }

                            } else if (lan.equalsIgnoreCase("kingdom")) {

                                for (int i = 0; i < object.size(); i++) {


                                    persian = new HadisModel_persian();
                                    persian.hadisID = object.get(i).english.hadisID;
                                    persian.Title = object.get(i).english.Title;
                                    persian.text = object.get(i).english.text;
                                    persian.date = object.get(i).english.date;
                                    persian.time = object.get(i).english.time;
                                    persian.subject = object.get(i).english.subject;
                                    persian.tags = object.get(i).english.tags;
                                    persian.authors = object.get(i).english.authors;
                                    persian.MasolID = object.get(i).english.getMasolID();

                                    Array_class_get.array_hadis_persian.add(persian);
                                }
                            } else if (lan.equalsIgnoreCase("iran")) {

                                for (int i = 0; i < object.size(); i++) {


                                    persian = new HadisModel_persian();
                                    persian.hadisID = object.get(i).persian.hadisID;
                                    persian.Title = object.get(i).persian.Title;
                                    persian.text = object.get(i).persian.text;
                                    persian.date = object.get(i).persian.date;
                                    persian.time = object.get(i).persian.time;
                                    persian.subject = object.get(i).persian.subject;
                                    persian.tags = object.get(i).persian.tags;
                                    persian.authors = object.get(i).persian.authors;
                                    persian.MasolID = object.get(i).persian.getMasolID();

                                    Array_class_get.array_hadis_persian.add(persian);
                                }
                            }
                        }


                    } catch (Exception e) {

                    }

                }


                mDialog.dismiss();
                try {

                } catch (Exception e) {
                }

                setDataOnVerticalRecyclerView();
                mDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_hadis.this, errorMsg, Toast.LENGTH_SHORT).show();

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
        startActivity(new Intent(a_hadis.this, a_Circle_list_menu.class));
        finish();
    }
}
