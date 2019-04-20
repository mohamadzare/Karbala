package com.zare.karbala;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Answer_Spinner;
import Adapter.Adapter_Hadaf_Place;
import Adapter.Adapter_Hadaf_Spinner;
import Adapter.Adapter_chat_list;
import Adapter.List_Spinner_City;
import Model.Adapter_Spinner_City;
import Model.ApiService;
import Model.Array_class_get;
import Model.ClientPaymentResponseDataBase;
import Model.ConfigApi;
import Model.ListObject_Spinner_Ostan;
import Model.M_Pay_detials;
import Model.String_Hadaf;
import Model.changeRegisterParam;
import Model.goalusage;
import Model.imputPardakht;
import Model.inputhistory;
import Model.placeusage;
import Model.responseToApp;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_payment extends AppCompatActivity {


    EditText jobs, age, neeyat, sex, AmmountRials, city, state, nameFamily;

    TextView shoghl, sen, niat, jens, mablagh, hadaf, mahal_txt, ostans, nam;

    SharedPreferences prefs_s, prefs;

    Spinner spinner_Ostan, spinner_Shahr, mahale_,organ;


    RadioButton mard, zan, haghighi, Hoghoghi;

    ArrayList<ListObject_Spinner_Ostan> objects;

    ArrayList<List_Spinner_City> objects_city;

    int City_id, Ostan_ID;

    String lan, sex_te,orgName_;

    Button pay;

    String MyToken, MyMasol, MyToken_masol, neeat;


    TextView shahr, ostan, mahale_masraf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_payment);

        Mahale_Masraf();


        mard = (RadioButton) findViewById(R.id.mard);

        zan = (RadioButton) findViewById(R.id.zan);

        haghighi = (RadioButton) findViewById(R.id.haghighi);

        Hoghoghi = (RadioButton) findViewById(R.id.Hoghoghi);

        organ = (Spinner) findViewById(R.id.organ);

        organ.setVisibility(View.GONE);

        pay = (Button) findViewById(R.id.pay);

        shoghl = (TextView) findViewById(R.id.shoghl);

        sen = (TextView) findViewById(R.id.sen);

        niat = (TextView) findViewById(R.id.niat);

        mahal_txt = (TextView) findViewById(R.id.mahal_txt);

        mablagh = (TextView) findViewById(R.id.mablagh);

        mahale_ = (Spinner) findViewById(R.id.mahale_);

        nam = (TextView) findViewById(R.id.nam);

        jens = (TextView) findViewById(R.id.jensiat);


        ostan = (TextView) findViewById(R.id.ostan);

        shahr = (TextView) findViewById(R.id.shahr);


        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");
        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {
                shoghl.setText("وظيفة:");
                sen.setText("عمر:");
                niat.setText("استهداف:");
                jens.setText("جنس:");
                mablagh.setText("مبشر:");

                shahr.setText("مدينة");

                mahal_txt.setText("مكان ");

                nam.setText("الاسم والعائلة:");

                pay.setText("دفع");

                mard.setText("رجل");

                zan.setText("امرأة");

                Hoghoghi.setText("قانوني");

                haghighi.setText("العضو");



                ostan.setText("مقاطعة");

            } else if (lan.equalsIgnoreCase("kingdom")) {

                shoghl.setText("Jobs:");

                sen.setText("Age:");

                niat.setText("Target:");

                jens.setText("Sex:");

                mablagh.setText("Mount:");
                // hadaf.setText("Target:");
                mahal_txt.setText("Location pay");

                nam.setText("Name and Family");

                pay.setText("pay");

                mard.setText("Man");

                zan.setText("Women");

                Hoghoghi.setText("regular");

                haghighi.setText("Legal");



                shahr.setText("City");

                ostan.setText("Province");


            } else if (lan.equalsIgnoreCase("iran")) {

                shoghl.setText("شغل:");

                sen.setText("سن:");

                niat.setText("نییت:");

                jens.setText("جنسیت:");

                mablagh.setText("مبلغ (به ریال می باشد):");
//                hadaf.setText("هدف از کمک:");

                mahal_txt.setText("محل مصرف");

                nam.setText("نام و نام خانوادگی:");

                pay.setText("پرداخت");

                mard.setText("مرد");

                zan.setText("زن");

                Hoghoghi.setText("حقوقی");

                haghighi.setText("حقیقی");



                shahr.setText("شهر");

                ostan.setText("استان");
            }

        }
        objects = new ArrayList<ListObject_Spinner_Ostan>();

        objects_city = new ArrayList<List_Spinner_City>();

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        jobs = (EditText) findViewById(R.id.jobs);


        nameFamily = (EditText) findViewById(R.id.nameFamily);

        spinner_Ostan = (Spinner) findViewById(R.id.spinner_Ostan);

        spinner_Shahr = (Spinner)
                findViewById(R.id.spinner_Shahr);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.zan) {
                    sex_te = "female";
                } else if (checkedId == R.id.mard) {
                    sex_te = "male";
                }

            }
        });

        RadioGroup HGroup = (RadioGroup) findViewById(R.id.Hgroup);
        HGroup.clearCheck();

        HGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.Hoghoghi) {

                    organ.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.haghighi) {
                    organ.setVisibility(View.GONE);

                }

            }
        });
        age = (EditText) findViewById(R.id.age);

        neeyat = (EditText) findViewById(R.id.neeyat);

        AmmountRials = (EditText) findViewById(R.id.AmmountRials);

        Typeface face = Typeface.createFromAsset(getAssets(), "IRANSans.ttf");

        AmmountRials.setTypeface(face);

        objects_city = new ArrayList<List_Spinner_City>();

        for (int i = 0; i < Array_class_get.array_m_Ostan.size(); i++) {

            ListObject_Spinner_Ostan obj = new ListObject_Spinner_Ostan();

            obj.setAll(Array_class_get.array_m_Ostan.get(i).Name, Array_class_get.array_m_Ostan.get(i).id);

            objects.add(obj);

            spinner_Ostan.setAdapter(new Adapter_Answer_Spinner(a_payment.this, objects));

        }
        spinner_Ostan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Ostan_ID = objects.get(position).id;

                objects_city.clear();

                for (int i = 0; i < Array_class_get.array_m_Shahr.size(); i++) {

                    if ((Array_class_get.array_m_Shahr.get(i).idOstan) == Ostan_ID) {

                        List_Spinner_City obj = new List_Spinner_City();

                        obj.setAll(Array_class_get.array_m_Shahr.get(i).name, Array_class_get.array_m_Shahr.get(i).id);

                        objects_city.add(obj);

                        spinner_Shahr.setAdapter(new Adapter_Spinner_City(a_payment.this, objects_city));
                    }

                    spinner_Shahr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            City_id = objects_city.get(position).id;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        organ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                orgName_ = Array_class_get.array_placeusage.get(position).Name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mahale_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                neeat = Array_class_get.String_Hadaf.get(position).Name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_payment.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void payplace() {

        Array_class_get.array_placeusage.clear();
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");




        final ProgressDialog mDialog = new ProgressDialog(a_payment.this);
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
        service.payplace(new Callback<placeusage>() {
            @Override
            public void success(placeusage object, Response response) {

                mDialog.dismiss();

                if (object != null) {

                    for (int i = 0; i < object.list.size(); i++) {
                        M_Pay_detials hadaf = new M_Pay_detials();
                        hadaf.Name = object.list.get(i);
                        Array_class_get.array_placeusage.add(hadaf);

                    }
                    organ.setAdapter(new Adapter_Hadaf_Place(a_payment.this, Array_class_get.array_placeusage));

                } else {

                }


            }

            @Override
            public void failure(RetrofitError error) {

                mDialog.dismiss();
            }
        });


    }

    public void Mahale_Masraf() {
        Array_class_get.String_Hadaf.clear();

        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        inputhistory pardakht = new inputhistory();

        pardakht.token = MyToken;

        pardakht.IsMasol = true;

        SharedPreferences prefs_ = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

        MyToken_masol = prefs_.getString(ConfigApi.MyToken, "");

        MyMasol = prefs_.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            pardakht.IsMasol = true;
        } else {
            pardakht.IsMasol = false;
        }


        final ProgressDialog mDialog = new ProgressDialog(a_payment.this);
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
        service.mahalMasraf(pardakht, new Callback<goalusage>() {
            @Override
            public void success(goalusage object, Response response) {

                mDialog.dismiss();

                if (object != null) {

                    object.list.size();


                    for (int i = 0; i < object.list.size(); i++) {
                        String_Hadaf hadaf = new String_Hadaf();
                        hadaf.Name = object.list.get(i);
                        Array_class_get.String_Hadaf.add(hadaf);

                    }

                    mahale_.setAdapter(new Adapter_Hadaf_Spinner(a_payment.this, Array_class_get.String_Hadaf));
                    payplace();

                } else {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_payment.this);
                    dialogBuilder
                            .withTitle(" ")
                            .withMessage(" ")
                            .show();
                }


            }

            @Override
            public void failure(RetrofitError error) {

                mDialog.dismiss();
            }
        });


    }


    public void pay(View view) {


        if (
                AmmountRials.getText().toString().equalsIgnoreCase("") ||

                        nameFamily.getText().toString().equalsIgnoreCase("")
                )

        {

            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_payment.this);
            dialogBuilder
                    .withTitle(" ")
                    .withMessage("پرکردن تمامی فیلد ها اجباری است.")
                    .show();
        } else {

            Date curDate = new Date();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

            String DateToStr = format.format(curDate);

            SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
            final String MyToken = prefs.getString(ConfigApi.MyToken, "");

            imputPardakht pardakht = new imputPardakht();

            pardakht.token = MyToken;

            pardakht.IsPerson = true;

            SharedPreferences prefs_ = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

            MyToken_masol = prefs_.getString(ConfigApi.MyToken, "");

            MyMasol = prefs_.getString(ConfigApi.MyMasol, "");

            if (MyMasol.equalsIgnoreCase("true")) {
                pardakht.masolID = true;
            } else {
                pardakht.masolID = false;
            }


            pardakht.Age = age.getText().toString();

            pardakht.Job = jobs.getText().toString();

            pardakht.State = String.valueOf(Ostan_ID);

            pardakht.AmmountRials = AmmountRials.getText().toString();

            pardakht.city = String.valueOf(City_id);

            pardakht.nameFamily = nameFamily.getText().toString() ;

            pardakht.sex = sex_te;

            pardakht.GoalUsage = neeat;

            pardakht.orgName = orgName_;

            pardakht.datetime = DateToStr;

            final ProgressDialog mDialog = new ProgressDialog(a_payment.this);

            mDialog.setMessage("در حال اتصال به درگاه بانکی");

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
                    .setEndpoint("http://194.36.174.21:3003")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(requestInterceptor)
                    .build();
            ApiService service = restAdapter.create(ApiService.class);
            service.pardakht(pardakht, new Callback<ClientPaymentResponseDataBase>() {
                @Override
                public void success(ClientPaymentResponseDataBase object, Response response) {

                    mDialog.dismiss();

                    if (object != null) {

                        if (object.tokenField == 0) {
                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_payment.this);
                            dialogBuilder
                                    .withTitle(" ")
                                    .withMessage(object.statusField)
                                    .show();
                        } else {

                            Intent inn = new Intent(a_payment.this, a_webview_pay.class);
                            inn.putExtra("money", String.valueOf(object.tokenField));
                            startActivity(inn);
                            finish();

                        }

                    } else {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_payment.this);
                        dialogBuilder
                                .withTitle("هشدار")
                                .withMessage("درگاه با مشکل مواجه شده است ...")
                                .show();
                    }


                }

                @Override
                public void failure(RetrofitError error) {
                    String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                    Toast.makeText(a_payment.this, errorMsg, Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            });


        }

    }


}
