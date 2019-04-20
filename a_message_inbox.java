package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Masol_Spinner;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.M_masolNameID;
import Model.ParamStartTicket;
import Model.SendTickMessage;
import Model.masolNameID;
import Model.sharedprefrencce;
import Model.stateReg;
import Model.tickIDString;
import Model.tickListMasolin;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class a_message_inbox extends AppCompatActivity {

    Spinner spinner;

    int id_;

    String lan;

    SharedPreferences prefs_s, prefs;

    Button btn_click;

    EditText bayan_moshkl;

    String id_ticket, StateName = "";

    TextView masol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_message_inbox);


        bayan_moshkl = (EditText) findViewById(R.id.bayan_moshkl);

        btn_click = (Button) findViewById(R.id.btn_click);

        masol = (TextView) findViewById(R.id.masol);

        spinner = (Spinner) findViewById(R.id.spin_masol);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                // Toast.makeText(a_message_inbox.this, String.valueOf(Array_class_get.array_masolNameID.get(position).masolID), Toast.LENGTH_SHORT).show();
                // post_StartTicket((Array_class_get.array_masolNameID.get(position).masolID));

                id_ = Array_class_get.array_masolNameID.get(position).masolID;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");
        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {
                bayan_moshkl.setHint("المشكلة");
                masol.setText("الاختيار المسؤول");
                btn_click.setText("تسجيل التذكرة");
            } else if (lan.equalsIgnoreCase("kingdom")) {
                bayan_moshkl.setHint("Problem");
                masol.setText("Choose");
                btn_click.setText("Send");
            } else if (lan.equalsIgnoreCase("iran")) {
                bayan_moshkl.setHint("بیان مشکل");
                masol.setText("انتخاب مسئول");
                btn_click.setText("ثبت تیکت");
            }

        }


        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

                get_StartTicket();
            } else {
                Array_class_get.array_masolNameID.clear();
                StateName = getIntent().getStringExtra("StateName").toString();
                for (int m = 0; m < Array_class_get.m_UserInfo2.size(); m++) {

                    M_masolNameID m_masolNameID = new M_masolNameID();
                    m_masolNameID.masolID = Integer.parseInt(Array_class_get.m_UserInfo2.get(m).Id);
                    m_masolNameID.masolName = Array_class_get.m_UserInfo2.get(m).nameFamily;
                    m_masolNameID.masolUserName = Array_class_get.m_UserInfo2.get(m).nameFamily;
                    Array_class_get.array_masolNameID.add(m_masolNameID);
                }
                spinner.setAdapter(new Adapter_Masol_Spinner(a_message_inbox.this, Array_class_get.array_masolNameID));

            }
        } else {
            get_StartTicket();
            // StateName = extras.getString("StateName");
        }
    }

    public void get_StartTicket() {

        Array_class_get.array_masolNameID.clear();
        final ProgressDialog mDialog = new ProgressDialog(a_message_inbox.this);

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
        service.StartTicket(new Callback<tickListMasolin>() {


            @Override
            public void success(tickListMasolin masolNameIDS, Response response) {

                mDialog.dismiss();

                for (int k = 0; k < masolNameIDS.poshtiban.size(); k++) {
                    M_masolNameID m_masolNameID = new M_masolNameID();
                    m_masolNameID.masolID = masolNameIDS.poshtiban.get(k).getMasolID();
                    m_masolNameID.masolName = masolNameIDS.poshtiban.get(k).masolName;
                    m_masolNameID.masolUserName = masolNameIDS.poshtiban.get(k).masolUserName;
                    Array_class_get.array_masolNameID.add(m_masolNameID);
                }

                spinner.setAdapter(new Adapter_Masol_Spinner(a_message_inbox.this, Array_class_get.array_masolNameID));
                mDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_message_inbox.this, errorMsg, Toast.LENGTH_SHORT).show();

                mDialog.dismiss();
            }
        });

    }

    public void send(View view) {

        post_StartTicket();


    }

    public void post_StartTicket() {

        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");


        ParamStartTicket video = new ParamStartTicket();
        video.IdMasol = id_;
        video.token = MyToken;
        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            video.IsMasol = true;
        } else {
            video.IsMasol = false;
        }

        final ProgressDialog mDialog = new ProgressDialog(a_message_inbox.this);

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
        service.StartTicket(video, new Callback<tickIDString>() {


            @Override
            public void success(tickIDString Name, Response response) {


                id_ticket = Name.tickString;


                mDialog.dismiss();


                if (
                        id_ticket != null
                        ) {
                    send_tikcet();
                } else {
                    Toast.makeText(a_message_inbox.this, "متاسفانه در انتخاب مسئول مشکلی پیش آمده.", Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_message_inbox.this, errorMsg, Toast.LENGTH_SHORT).show();

                mDialog.dismiss();
            }
        });

    }


    public void send_tikcet() {
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        SendTickMessage video = new SendTickMessage();
        video.problem = bayan_moshkl.getText().toString().trim();
        video.token = MyToken;
        video.IsMasol = false;
        video.tickID = id_ticket;

        final ProgressDialog mDialog = new ProgressDialog(a_message_inbox.this);

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
        service.SendTick(video, new Callback<stateReg>() {


            @Override
            public void success(stateReg Name, Response response) {
                mDialog.dismiss();
                if (Name.state == true) {
                    Toast.makeText(a_message_inbox.this, Name.MessageText, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_message_inbox.this, errorMsg, Toast.LENGTH_SHORT).show();

                mDialog.dismiss();
            }
        });

    }

    public void onBackPressed() {


        if (StateName.equalsIgnoreCase(""))

        {
            startActivity(new Intent(a_message_inbox.this, a_Circle_list_menu.class));
            finish();
        } else {
            if (StateName.equalsIgnoreCase("List_a_chat")) {
                startActivity(new Intent(a_message_inbox.this, List_a_chat.class));
                finish();
            } else if (StateName.equalsIgnoreCase("chat_msg"))

            {
                startActivity(new Intent(a_message_inbox.this, chat_msg.class));
                finish();
            }
        }

    }
}
