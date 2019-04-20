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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
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
import Model.SubmiVol;
import Model.sharedprefrencce;
import Model.stateReg;
import Model.userRegister;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_register_guys_motabare extends AppCompatActivity {


    EditText editnik, editTextuser, editFamily, editcodemeli, date_brthday, edit_takhasos, editphone, editphone2, edit_address;

    Spinner spinner_Ostan, spinner_Shahr;

    int state = 1;

    ArrayList<ListObject_Spinner_Ostan> objects;

    ArrayList<List_Spinner_City> objects_city;

    int City_id, Ostan_ID;

    CheckBox check_masol;

    SharedPreferences prefs;

    String lan;

    Button sign;

    TextView shahr, ostan, jensiat;


    RadioButton mard, zan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_register_guys_motabare);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();


        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        sign = (Button) findViewById(R.id.sign);
        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");


        mard = (RadioButton) findViewById(R.id.mard);

        zan = (RadioButton) findViewById(R.id.zan);

        jensiat = (TextView) findViewById(R.id.jensiat);

        shahr = (TextView) findViewById(R.id.shahr);

        ostan = (TextView) findViewById(R.id.ostan);

        editTextuser = (EditText) findViewById(R.id.editTextuser);

        editFamily = (EditText) findViewById(R.id.editFamily);

        editcodemeli = (EditText) findViewById(R.id.editcodemeli);

        edit_takhasos = (EditText) findViewById(R.id.edit_takhasos);

        editphone = (EditText) findViewById(R.id.editphone);

        editphone2 = (EditText) findViewById(R.id.editphone2);

        edit_address = (EditText) findViewById(R.id.edit_address);

        date_brthday = (EditText) findViewById(R.id.date_brthday);

        spinner_Ostan = (Spinner) findViewById(R.id.spinner_Ostan);

        spinner_Shahr = (Spinner)
                findViewById(R.id.spinner_Shahr);


        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {

                date_brthday.setHint("  الميلاد");
                editphone2.setHint("رقم الجوال");
                editphone.setHint("قضية ثابتة");
                edit_takhasos.setHint("نوع التخصص");
                editcodemeli.setHint("رمز وطني");
                editFamily.setHint("عائلة");
                editTextuser.setHint("اسم ");
                shahr.setText("المدينة");
                ostan.setText("مقاطعة");
                sign.setText("اشترك");
                edit_address.setHint("عنوان");
                jensiat.setText("جنس");
                mard.setText("رجل");
                zan.setText("النساء");
            } else if (lan.equalsIgnoreCase("kingdom")) {

                date_brthday.setHint("Age");
                editphone2.setHint("Mobile number");
                editphone.setHint("Phone number");
                edit_takhasos.setHint("Carrier");
                editcodemeli.setHint("International Code");
                editFamily.setHint("Family");
                editTextuser.setHint("Name");
                shahr.setText("City");
                ostan.setText("Province");
                sign.setText("SignUp");
                edit_address.setHint("Address");
                jensiat.setText("sex");
                mard.setText("Man");
                zan.setText("Woman");
            } else if (lan.equalsIgnoreCase("iran")) {

                date_brthday.setHint("سن");
                editphone2.setHint("شماره همراه");
                editphone.setHint("شماره ثابت");
                edit_takhasos.setHint("نوع تخصص");
                editcodemeli.setHint("کد ملی");
                editFamily.setHint("نام خانوادگی");
                editTextuser.setHint("نام ");
                shahr.setText("شهر");
                ostan.setText("استان");
                sign.setText("ثبت نام");
                edit_address.setHint("آدرس");
                jensiat.setText("جنسیت");
                mard.setText("مرد");
                zan.setText("زن");
            }
        }


        objects = new ArrayList<ListObject_Spinner_Ostan>();

        objects_city = new ArrayList<List_Spinner_City>();

        ListObject_Spinner_Ostan obj1 = new ListObject_Spinner_Ostan();


        objects.add(obj1);

        for (int i = 0; i < Array_class_get.array_m_Ostan.size(); i++) {

            ListObject_Spinner_Ostan obj = new ListObject_Spinner_Ostan();

            obj.setAll(Array_class_get.array_m_Ostan.get(i).Name, Array_class_get.array_m_Ostan.get(i).id);

            objects.add(obj);

            spinner_Ostan.setAdapter(new Adapter_Answer_Spinner(a_register_guys_motabare.this, objects));

        }
        spinner_Ostan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Ostan_ID = objects.get(position).id;

                objects_city.clear();

                List_Spinner_City obj1 = new List_Spinner_City();

                objects_city.add(obj1);

                for (int i = 0; i < Array_class_get.array_m_Shahr.size(); i++) {

                    if ((Array_class_get.array_m_Shahr.get(i).idOstan) == Ostan_ID) {

                        List_Spinner_City obj = new List_Spinner_City();

                        obj.setAll(Array_class_get.array_m_Shahr.get(i).name, Array_class_get.array_m_Shahr.get(i).id);

                        objects_city.add(obj);

                        spinner_Shahr.setAdapter(new Adapter_Spinner_City(a_register_guys_motabare.this, objects_city));
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

    }

    public void register() {


        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        SubmiVol rgster = new SubmiVol();

        rgster.Token = MyToken;

        SharedPreferences prefs_ = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);


        String MyMasol = prefs_.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            rgster.Ismasol = true;
        } else {
            rgster.Ismasol = false;
        }
        if (
                editFamily.getText().toString().trim().equalsIgnoreCase("") ||
                        editphone.getText().toString().trim().equalsIgnoreCase("") ||
                        editphone2.getText().toString().trim().equalsIgnoreCase("") ||
                        edit_address.getText().toString().trim().equalsIgnoreCase("") ||
                        edit_takhasos.getText().toString().trim().equalsIgnoreCase("") ||
                        date_brthday.getText().toString().trim().equalsIgnoreCase("") ||
                        editcodemeli.getText().toString().trim().equalsIgnoreCase("")) {
            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_register_guys_motabare.this);
            dialogBuilder
                    .withTitle("توجه بفرمایید")
                    .withMessage("پر کردن تمامی موارد اجباری هست.")
                    .show();
        } else {
            //   rgster.userName = editnik.getText().toString().trim();


            if (editphone.getText().length() >= 9 && editphone2.getText().length() >= 9 && editcodemeli.getText().length() >= 10 && (Integer.parseInt(date_brthday.getText().toString().trim()) > 20 || Integer.parseInt(date_brthday.getText().toString().trim()) < 55)) {

                rgster.phone = editphone.getText().toString().trim();

                rgster.mobile = editphone2.getText().toString().trim();

                rgster.nameFamily = editFamily.getText().toString().trim();

                rgster.state = String.valueOf(Ostan_ID);

                rgster.city = String.valueOf(City_id);

                rgster.Address = edit_address.getText().toString().trim();

                rgster.Jobs = edit_takhasos.getText().toString().trim();

                rgster.birthDate = date_brthday.getText().toString().trim();

                rgster.NationalID = editcodemeli.getText().toString().trim();


                final ProgressDialog mDialog = new ProgressDialog(a_register_guys_motabare.this);
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
                service.subvol(rgster, new Callback<stateReg>() {
                    @Override
                    public void success(stateReg object, Response response) {

                        mDialog.dismiss();
                        if (object != null) {
                            if (object.state == true) {
                                Toast.makeText(a_register_guys_motabare.this, "ثبت نام با موفقیت انجام شد.", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(a_register_guys_motabare.this, "خطایی رخ داده است ، مجدد سعی کنید", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }
                        } else {
                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_register_guys_motabare.this);
                            dialogBuilder
                                    .withTitle("پیغام")
                                    .withMessage("ثبت نام با خطا مواجه شده است")
                                    .show();
                        }


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                        Toast.makeText(a_register_guys_motabare.this, errorMsg, Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();
                    }
                });
            } else {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_register_guys_motabare.this);
                dialogBuilder
                        .withTitle("توجه بفرمایید")
                        .withMessage(" شماره تلفن ها یا کد ملی را کامل وارد نمایید و سن مجاز بین سنین 20 و 55 می باشد ")
                        .show();
            }


        }


    }

    public void register(View view) {

        register();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_register_guys_motabare.this, a_Circle_list_menu.class));
        finish();
    }
}
