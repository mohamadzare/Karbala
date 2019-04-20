package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;
import com.squareup.okhttp.OkHttpClient;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.GetFirst;
import Model.loginModel;
import Model.m_Ostan;
import Model.m_Shahr;
import Model.sharedprefrencce;
import Model.stateReg;
import Model.userRegister;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_login extends AppCompatActivity {

    EditText nik_name, pass, Ramz;

    CheckBox check_masol;

    int state = 1;

    String lan, userName_txt, MyToken;

    SharedPreferences prefs;

    TextView name_app;

    Button lg, rg;

    ImageView image;

    LinearLayout cbox4;

    ConnectivityManager connec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login);

        image = (ImageView) findViewById(R.id.image);
        cbox4 = (LinearLayout) findViewById(R.id.cbox4);
        cbox4.setVisibility(View.INVISIBLE);


        // ejbari bodan field ha _ phone 12,pass 8 va mard o zan bar sabte name va shomare hamrah va sabet ,english persian arabic login // name masol va name app

        // moarafi ostan,bayad shahrestan biad, descrption dar morede shahr va bad y dokme bara google bashe
        // size dokme ha dorost she va dar tasavir design baz  tarahi shavvad

        // design ertebat ba ma,back ground
        // add bara ijad yadasht rozane
        //film ha ,image ,size , image bara video ,taghr design
        // ,rangi kardan trexta ha goran
        // rang va font chat
        // size bozorg kardan text
        // back kardan chatt,hub seda bezan
        // last chat bara namaysh pm ha
        // dastarasi hatman click bshe,va nazar e bere to app
        // update app
        // menu nim dayere
//        Glide.with(this)
//                .load(R.raw.g_k)
//                .into(new GlideDrawableImageViewTarget(
//                        (ImageView) findViewById(R.id.image)));
//

        lg = (Button) findViewById(R.id.lg);

        rg = (Button) findViewById(R.id.rg);

        name_app = (TextView) findViewById(R.id.name_app);


        nik_name = (EditText) findViewById(R.id.editnik_log);

        Ramz = (EditText) findViewById(R.id.Ramz);

        pass = (EditText) findViewById(R.id.editpass_log);


        check_masol = (CheckBox) findViewById(R.id.masol_checkbox);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        get_city();

        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
          MyToken = prefs.getString(sharedprefrencce.MyToken, "");

        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {
                String name_user_arabic = getResources().getString(R.string.name_user_arabic);

                String mystring = getResources().getString(R.string.name_app_arabic);

                String name_pass = getResources().getString(R.string.name_pass_arabic);

                String name_log = getResources().getString(R.string.name_btn_arabic_vorod);

                String rgs_log = getResources().getString(R.string.name_rg_arab);


                lg.setText(name_log);

                pass.setHint(name_pass);

                nik_name.setHint(name_user_arabic);

                name_app.setText(mystring);
                Ramz.setHint("كلمة المرور لمرة واحدة");
                check_masol.setText("أنا مسؤول");
                rg.setText(rgs_log);


            } else if (lan.equalsIgnoreCase("kingdom")) {

                String rgs_log = getResources().getString(R.string.name_rg_en);

                String mystring = getResources().getString(R.string.name_app_en);

                String name_user_en = getResources().getString(R.string.name_user_en);

                String name_pass = getResources().getString(R.string.name_pass_en);

                String name_log = getResources().getString(R.string.name_login_en);

                lg.setText(name_log);

                pass.setHint(name_pass);
                Ramz.setHint("One-time Password");
                nik_name.setHint(name_user_en);

                name_app.setText(mystring);

                check_masol.setText("I'm responsible");
                rg.setText(rgs_log);

            } else if (lan.equalsIgnoreCase("iran")) {

                String rgs_log = getResources().getString(R.string.name_btn_rg);

                String mystring = getResources().getString(R.string.name_app);

                String name_user = getResources().getString(R.string.name_user);

                String name_pass = getResources().getString(R.string.name_pass);

                String name_log = getResources().getString(R.string.name_btn_vorod);

                rg.setText(rgs_log);

                Ramz.setHint("رمز یکبار مصرف");
                name_app.setText(mystring);

                nik_name.setHint(name_user);

                check_masol.setText("مسئول هستم");

                pass.setHint(name_pass);

                lg.setText(name_log);
            }
        }

        check_masol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = 2;
                    cbox4.setVisibility(View.VISIBLE);
                } else {
                    state = 1;
                    cbox4.setVisibility(View.INVISIBLE);
                }
            }
        });


        connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    public void arab(View view) {
        SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        prefs.edit().remove(sharedprefrencce.MyToken).commit();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(sharedprefrencce.MyToken, "arab");
        editor.apply();

        finish();
        startActivity(getIntent());
    }

    public void kingdom(View view) {

        SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        prefs.edit().remove(sharedprefrencce.MyToken).commit();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(sharedprefrencce.MyToken, "kingdom");
        editor.apply();
        finish();
        startActivity(getIntent());
    }

    public void iran(View view) {

        SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        prefs.edit().remove(sharedprefrencce.MyToken).commit();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(sharedprefrencce.MyToken, "iran");
        editor.apply();
        finish();
        startActivity(getIntent());
    }


    public void login() {


        if (state == 2) {
            if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {

                if (nik_name.getText().toString().trim().equalsIgnoreCase("") ||
                        pass.getText().toString().trim().equalsIgnoreCase("") || Ramz.getText().toString().trim().equalsIgnoreCase("")) {


                    prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    if ((MyToken.isEmpty())) {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                        dialogBuilder
                                .withTitle("اطلاعات را وارد کنید...")
                                .withMessage("در غیر این صورت اجازه ورود ندارید.")
                                .show();

                    } else {

                        SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                        lan = prefs.getString(sharedprefrencce.MyToken, "");

                        if (lan.equalsIgnoreCase("arab")) {

                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                            dialogBuilder
                                    .withTitle("أدخل المعلومات...")
                                    .withMessage(" ")
                                    .show();

                        } else if (lan.equalsIgnoreCase("kingdom")) {
                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                            dialogBuilder
                                    .withTitle("Enter information...")
                                    .withMessage(" ")
                                    .show();

                        } else if (lan.equalsIgnoreCase("iran")) {

                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                            dialogBuilder
                                    .withTitle("اطلاعات را وارد کنید...")
                                    .withMessage("در غیر این صورت اجازه ورود ندارید.")
                                    .show();

                        }
                    }






                } else


                {
                    loginModel rgster = new loginModel();

                    rgster.userName = nik_name.getText().toString().trim();

                    rgster.password = pass.getText().toString().trim();

                    rgster.state = state;

                    rgster.FirstCode = Ramz.getText().toString().trim();

                    final ProgressDialog mDialog1 = new ProgressDialog(a_login.this);

                    mDialog1.setMessage("");

                    mDialog1.setCancelable(false);

                    mDialog1.show();


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

                    service.login(rgster, new Callback<stateReg>() {
                        @Override
                        public void success(stateReg object, Response response) {
                            boolean status = object.state;

                            mDialog1.dismiss();

                            if (object != null)
                            {
                                if (status == true) {
                                    if (object.IsMasol == false) {
                                        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
                                        prefs.edit().remove(ConfigApi.MyToken).commit();
                                        prefs.edit().remove(ConfigApi.MyMasol).commit();
                                        prefs.edit().remove(ConfigApi.MyUser).commit();
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString(ConfigApi.MyToken, object.MessageText);
                                        editor.putString(ConfigApi.MyMasol, "false");
                                        editor.putString(ConfigApi.MyUser, String.valueOf(object.userID));
                                        editor.apply();
                                        mDialog1.dismiss();
                                        startActivity(new Intent(a_login.this, a_Circle_list_menu.class));
                                        finish();
                                    } else {

                                        userName_txt = nik_name.getText().toString().trim();

                                        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

                                        prefs.edit().remove(ConfigApi.MyToken).commit();

                                        prefs.edit().remove(ConfigApi.MyMasol).commit();

                                        SharedPreferences.Editor editor = prefs.edit();

                                        prefs.edit().remove(ConfigApi.MyUser).commit();

                                        editor.putString(ConfigApi.MyToken, object.MessageText);

                                        editor.putString(ConfigApi.MyMasol, "true");

                                        editor.putString(ConfigApi.MyUser, String.valueOf(object.userID));

                                        editor.apply();
                                        mDialog1.dismiss();

                                        startActivity(new Intent(a_login.this, a_Circle_list_menu.class));
                                        finish();
                                    }


                                } else {
                                   // Toast.makeText(a_login.this, "ورود شما موفقیت آمیز نبوده است.", Toast.LENGTH_SHORT).show();
                                    mDialog1.dismiss();
                                }

                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {


                            prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                            if ((MyToken.isEmpty())) {
                                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                                Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();

                            } else {

                                SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                                lan = prefs.getString(sharedprefrencce.MyToken, "");

                                if (lan.equalsIgnoreCase("arab")) {

                                    String errorMsg = "حاول مرة أخرى في وقت لاحق";
                                    Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();


                                } else if (lan.equalsIgnoreCase("kingdom")) {
                                    String errorMsg = "Try Again Later...";
                                    Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();

                                } else if (lan.equalsIgnoreCase("iran")) {

                                    String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                                    Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();


                                }
                            }




                            mDialog1.dismiss();
                        }
                    });
                }

            } else if (connec.getNetworkInfo(0).getState() != NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(1).getState() != NetworkInfo.State.CONNECTED) {

                prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                if ((MyToken.isEmpty())) {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                    dialogBuilder
                            .withTitle("اینترنت گوشی تان را روشن کنید...")
                            .withMessage("در غیر این صورت اجازه ورود ندارید.")
                            .show();
                } else {

                    SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    lan = prefs.getString(sharedprefrencce.MyToken, "");

                    if (lan.equalsIgnoreCase("arab")) {

                        String errorMsg = "بدوره على واي فاي الخاص بك";
                        Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();


                    } else if (lan.equalsIgnoreCase("kingdom")) {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                        dialogBuilder
                                .withTitle("Turn On your Wifi")
                                .withMessage("")
                                .show();
                    } else if (lan.equalsIgnoreCase("iran")) {

                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                        dialogBuilder
                                .withTitle("اینترنت گوشی تان را روشن کنید...")
                                .withMessage("در غیر این صورت اجازه ورود ندارید.")
                                .show();

                    }
                }
            }
        } else if (state == 1) {
            if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {

                if (nik_name.getText().toString().trim().equalsIgnoreCase("") ||
                        pass.getText().toString().trim().equalsIgnoreCase("")) {
                    prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    if ((MyToken.isEmpty())) {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                        dialogBuilder
                                .withTitle("اطلاعات را وارد کنید...")
                                .withMessage("در غیر این صورت اجازه ورود ندارید.")
                                .show();

                    } else {

                        SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                        lan = prefs.getString(sharedprefrencce.MyToken, "");

                        if (lan.equalsIgnoreCase("arab")) {

                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                            dialogBuilder
                                    .withTitle("أدخل المعلومات...")
                                    .withMessage(" ")
                                    .show();

                        } else if (lan.equalsIgnoreCase("kingdom")) {
                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                            dialogBuilder
                                    .withTitle("Enter information...")
                                    .withMessage(" ")
                                    .show();

                        } else if (lan.equalsIgnoreCase("iran")) {

                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                            dialogBuilder
                                    .withTitle("اطلاعات را وارد کنید...")
                                    .withMessage("در غیر این صورت اجازه ورود ندارید.")
                                    .show();

                        }
                    }
                } else

                {
                    loginModel rgster = new loginModel();

                    rgster.userName = nik_name.getText().toString().trim();

                    rgster.password = pass.getText().toString().trim();

                    rgster.state = state;

                    rgster.FirstCode = Ramz.getText().toString().trim();

                    final ProgressDialog mDialog1 = new ProgressDialog(a_login.this);

                    mDialog1.setMessage(" ");

                    mDialog1.setCancelable(false);

                    mDialog1.show();


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

                    service.login(rgster, new Callback<stateReg>() {
                        @Override
                        public void success(stateReg object, Response response) {
                            boolean status = object.state;

                            mDialog1.dismiss();

                            if (object != null)
                            {
                                if (status == true) {

                                    if (object.IsMasol == false) {
                                        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
                                        prefs.edit().remove(ConfigApi.MyToken).commit();
                                        prefs.edit().remove(ConfigApi.MyMasol).commit();
                                        prefs.edit().remove(ConfigApi.MyUser).commit();
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString(ConfigApi.MyToken, object.MessageText);
                                        editor.putString(ConfigApi.MyMasol, "false");
                                        editor.putString(ConfigApi.MyUser, String.valueOf(object.userID));
                                        editor.apply();
                                        mDialog1.dismiss();
                                        startActivity(new Intent(a_login.this, a_Circle_list_menu.class));
                                        finish();
                                    } else {

                                        userName_txt = nik_name.getText().toString().trim();

                                        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

                                        prefs.edit().remove(ConfigApi.MyToken).commit();

                                        prefs.edit().remove(ConfigApi.MyMasol).commit();

                                        SharedPreferences.Editor editor = prefs.edit();

                                        prefs.edit().remove(ConfigApi.MyUser).commit();

                                        editor.putString(ConfigApi.MyToken, object.MessageText);

                                        editor.putString(ConfigApi.MyMasol, "true");

                                        editor.putString(ConfigApi.MyUser, String.valueOf(object.userID));

                                        editor.apply();
                                        mDialog1.dismiss();

                                        startActivity(new Intent(a_login.this, a_Circle_list_menu.class));
                                        finish();
                                    }


                                } else {
                                 //   Toast.makeText(a_login.this, "ورود شما موفقیت آمیز نبوده است.", Toast.LENGTH_SHORT).show();
                                    mDialog1.dismiss();
                                }


                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                            if ((MyToken.isEmpty())) {
                                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                                Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();

                            } else {

                                SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                                lan = prefs.getString(sharedprefrencce.MyToken, "");

                                if (lan.equalsIgnoreCase("arab")) {

                                    String errorMsg = "حاول مرة أخرى في وقت لاحق";
                                    Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();


                                } else if (lan.equalsIgnoreCase("kingdom")) {
                                    String errorMsg = "Try Again Later...";
                                    Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();

                                } else if (lan.equalsIgnoreCase("iran")) {

                                    String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                                    Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();


                                }
                            }
                            mDialog1.dismiss();
                        }
                    });
                }
            } else if (connec.getNetworkInfo(0).getState() != NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(1).getState() != NetworkInfo.State.CONNECTED) {

                prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                if ((MyToken.isEmpty())) {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                    dialogBuilder
                            .withTitle("اینترنت گوشی تان را روشن کنید...")
                            .withMessage("در غیر این صورت اجازه ورود ندارید.")
                            .show();
                } else {

                    SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    lan = prefs.getString(sharedprefrencce.MyToken, "");

                    if (lan.equalsIgnoreCase("arab")) {

                        String errorMsg = "بدوره على واي فاي الخاص بك";
                        Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();


                    } else if (lan.equalsIgnoreCase("kingdom")) {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                        dialogBuilder
                                .withTitle("Turn On your Wifi")
                                .withMessage("")
                                .show();
                    } else if (lan.equalsIgnoreCase("iran")) {

                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_login.this);
                        dialogBuilder
                                .withTitle("اینترنت گوشی تان را روشن کنید...")
                                .withMessage("در غیر این صورت اجازه ورود ندارید.")
                                .show();

                    }
                }

            }
        }


    }

    public void vorod(View view) {
        login();


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void register(View view) {

        startActivity(new Intent(a_login.this, a_register.class));
        finish();
    }

    public void get_city() {


        Array_class_get.array_m_Ostan.clear();
        final ProgressDialog mDialog = new ProgressDialog(a_login.this);

        mDialog.setMessage(" ");
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

        service.statecity(new Callback<Object>() {
            @Override
            public void success(Object object, Response response) {

                int m = 0;
                Gson gson2 = new Gson();
                String json = gson2.toJson(object);
                try {

                    JSONObject jsonObj = new JSONObject(json);

                    JSONArray ostans = jsonObj.getJSONArray("ostans");

                    for (int i = 0; i < ostans.length(); i++) {

                        JSONObject resultsobject = ostans.getJSONObject(i);

                        m_Ostan m_ostan = new m_Ostan();

                        if (resultsobject.has("id")) {
                            m_ostan.id = resultsobject.getInt("id");

                        } else {
                            m_ostan.id = 0;

                        }
                        if (resultsobject.has("name")) {
                            m_ostan.Name = resultsobject.getString("name");

                        } else {
                            m_ostan.Name = "";

                        }
                        Array_class_get.array_m_Ostan.add(m_ostan);
                    }
                    JSONArray shars = jsonObj.getJSONArray("shars");
                    for (int i = 0; i < shars.length(); i++) {

                        JSONObject resultsobject = shars.getJSONObject(i);

                        m_Shahr m_shahr = new m_Shahr();

                        if (resultsobject.has("id")) {
                            m_shahr.id = resultsobject.getInt("id");

                        } else {
                            m_shahr.id = 0;

                        }
                        if (resultsobject.has("name")) {
                            m_shahr.name = resultsobject.getString("name");

                        } else {
                            m_shahr.name = "";

                        }

                        if (resultsobject.has("idOstan")) {
                            m_shahr.idOstan = resultsobject.getInt("idOstan");

                        } else {
                            m_shahr.idOstan = 0;

                        }
                        Array_class_get.array_m_Shahr.add(m_shahr);
                    }
                } catch (Exception e) {
                }

                //   boolean status = object.status;
//
//
//                if (status == true) {
//                    Toast.makeText(register.this, "ثبت نام با موفقیت انجام شد.", Toast.LENGTH_SHORT).show();
//
//                    mDialog.dismiss();
//                    startActivity(new Intent(register.this, a_Menu.class));
//                    finish();
//
//                } else {
//                    Toast.makeText(register.this, object.msg, Toast.LENGTH_SHORT).show();
//                    mDialog.dismiss();
//                }
                mDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
//                Toast.makeText(a_login.this, errorMsg, Toast.LENGTH_SHORT).show();
//                Dialog.dismiss();
                mDialog.dismiss();
            }
        });

    }
}
