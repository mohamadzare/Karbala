package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Answer_Spinner;
import Adapter.List_Spinner_City;
import Model.Adapter_Spinner_City;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.ListObject_Spinner_Ostan;
import Model.sharedprefrencce;
import Model.stateReg;
import Model.userRegister;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_register extends AppCompatActivity {

    EditText phone, name, nik_name, pass, family;

    Spinner spinner_Ostan, spinner_Shahr;

    ArrayList<ListObject_Spinner_Ostan> objects;

    ArrayList<List_Spinner_City> objects_city;

    int City_id, Ostan_ID;

    CheckBox check_masol;

    RadioButton zan, mard;

    String sex, lan;

    TextView jensiat;

    SharedPreferences prefs;

    Button rgs;

    TextView ostan,shahr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_register);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());



        jensiat = (TextView) findViewById(R.id.jensiat);

        rgs = (Button) findViewById(R.id.rgs);
        zan = (RadioButton) findViewById(R.id.zan);

        mard = (RadioButton) findViewById(R.id.mard);

        shahr = (TextView) findViewById(R.id.shahr);
        ostan = (TextView) findViewById(R.id.ostan);



//        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_);
//
//        rotatingTextWrapper.setSize(35);
//
//        Rotatable rotatable = new Rotatable(Color.parseColor("#b6ffffff"), 1000, "ثبت نام", "Register", "تسجيل");
//
//        rotatable.setSize(35);
//
//        rotatable.setAnimationDuration(500);
//
//        rotatingTextWrapper.setContent("", rotatable);

        phone = (EditText) findViewById(R.id.editphone);

        name = (EditText) findViewById(R.id.editTextuser);

        nik_name = (EditText) findViewById(R.id.editnik);

        pass = (EditText) findViewById(R.id.editpass);

        family = (EditText) findViewById(R.id.editFamily);

        spinner_Ostan = (Spinner) findViewById(R.id.spinner_Ostan);

        spinner_Shahr = (Spinner)
                findViewById(R.id.spinner_Shahr);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.zan) {
                    sex = "female";
                } else if (checkedId == R.id.mard) {
                    sex = "male";
                }

            }
        });


        objects = new ArrayList<ListObject_Spinner_Ostan>();

        objects_city = new ArrayList<List_Spinner_City>();

        for (int i = 0; i < Array_class_get.array_m_Ostan.size(); i++) {

            ListObject_Spinner_Ostan obj = new ListObject_Spinner_Ostan();

            obj.setAll(Array_class_get.array_m_Ostan.get(i).Name, Array_class_get.array_m_Ostan.get(i).id);

            objects.add(obj);

            spinner_Ostan.setAdapter(new Adapter_Answer_Spinner(a_register.this, objects));

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

                        spinner_Shahr.setAdapter(new Adapter_Spinner_City(a_register.this, objects_city));
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
                phone.setHint("رقم الجوال");
                name.setHint("اسم");
                nik_name.setHint("اسم المستخدم");
                pass.setHint("سر");
                family.setHint("عائلة");
                jensiat.setText("جنس");
                zan.setText("نساء");
                mard.setText("رجل");
                rgs.setText("سجل");
                shahr.setText("المدينة");
                ostan.setText("مقاطعة");
            } else if (lan.equalsIgnoreCase("kingdom")) {
                String languageToLoad1 = "en"; // your language

                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();

                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());

                phone.setHint("Phone");
                name.setHint("Name");
                nik_name.setHint("Nick name");
                pass.setHint("Password");
                family.setHint("Family");
                jensiat.setText("Sex");
                zan.setText("Women");
                mard.setText("Man");
                rgs.setText("Sign up");
                shahr.setText("City");
                ostan.setText("Province");
            } else if (lan.equalsIgnoreCase("iran")) {

                String languageToLoad1 = "fa"; // your language

                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();

                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());

                phone.setHint("تلفن");
                name.setHint("نام");
                nik_name.setHint("نام کاربری");
                pass.setHint("رمز");
                family.setHint("خانوادگی");
                jensiat.setText("جنسیت:");
                zan.setText("زن");
                mard.setText("مرد");
                rgs.setText("ثبت نام");
                shahr.setText("شهر");
                ostan.setText("استان");
            }
        }

    }


    public void register() {


        if (nik_name.getText().toString().trim().equalsIgnoreCase("") ||
                name.getText().toString().trim().equalsIgnoreCase("") ||
                family.getText().toString().trim().equalsIgnoreCase("") ||
                phone.getText().toString().trim().equalsIgnoreCase("") ||
                pass.getText().toString().trim().equalsIgnoreCase("")) {
            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_register.this);

            dialogBuilder
                    .withTitle("توجه بفرمایید")
                    .withMessage("پر کردن تمامی موارد اجباری هست.")
                    .show();
        } else {

            if (phone.getText().length() >= 11 && pass.getText().length() > 7) {
                userRegister rgster = new userRegister();

                rgster.userName = nik_name.getText().toString().trim();

                rgster.name = name.getText().toString().trim();

                rgster.family = family.getText().toString().trim();

                rgster.address = phone.getText().toString().trim();

                rgster.password = pass.getText().toString().trim();

                rgster.sex = sex;

                rgster.state = String.valueOf(Ostan_ID);

                rgster.city = String.valueOf(City_id);
                final ProgressDialog mDialog = new ProgressDialog(a_register.this);
                mDialog.setMessage("درحال ثبت نام");
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
                service.userRegister(rgster, new Callback<stateReg>() {
                    @Override
                    public void success(stateReg object, Response response) {
                        boolean status = object.state;

                        if (status == true) {
                            Toast.makeText(a_register.this, "ثبت نام با موفقیت انجام شد.", Toast.LENGTH_SHORT).show();
                            SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
                            prefs.edit().remove(ConfigApi.MyToken).commit();
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString(ConfigApi.MyToken, object.MessageText);
                            editor.apply();
                            mDialog.dismiss();
                            startActivity(new Intent(a_register.this, a_Circle_list_menu.class));
                            finish();

                        } else {
                            Toast.makeText(a_register.this, "خطایی رخ داده است ، مجدد سعی کنید", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                        Toast.makeText(a_register.this, errorMsg, Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();
                    }
                });
            } else {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_register.this);

                dialogBuilder
                        .withTitle("توجه بفرمایید")
                        .withMessage("تعداد کاراکتر تلفن و پسورد به ترتیب 11 وبزرگتر از 8 می باشد")
                        .show();
            }

        }


    }

    public void register(View view) {
        register();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_register.this, a_login.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
