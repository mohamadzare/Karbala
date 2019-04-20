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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Historypay;
import Adapter.VerticalRecyclerViewAdapter;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.changeRegisterParam;
import Model.imputPardakht;
import Model.inputhistory;
import Model.m_imputPardakht;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_historypay extends AppCompatActivity {

    Adapter_Historypay historypay;

    RecyclerView last_ecycle;

    GridLayoutManager  mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_historypay);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        register();

        last_ecycle = (RecyclerView) findViewById(R.id.rc_history);

        mLayoutManager = new GridLayoutManager(a_historypay.this, 1);

        last_ecycle.setLayoutManager(mLayoutManager);



    }

    public void register() {

        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        inputhistory rgster = new inputhistory();


        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            rgster.IsMasol = true;
        } else {
            rgster.IsMasol = false;
        }
        //rgster.IsMasol = true;


        rgster.token = MyToken;

        Array_class_get.array_m_imputPardakht.clear();

        final ProgressDialog mDialog = new ProgressDialog(a_historypay.this);
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
        service.historypardakht(rgster, new Callback<List<imputPardakht>>() {

            @Override
            public void success(List<imputPardakht> imputPardakhts, Response response) {
                mDialog.dismiss();
                if (imputPardakhts != null) {

                    for (imputPardakht tag : imputPardakhts) {

                        if(tag.isSubmit())
                        {
                            m_imputPardakht pardakht = new m_imputPardakht();
                            pardakht.Age = tag.getAge();
                            pardakht.AmmountRials = tag.getAmmountRials();
                            pardakht.city = tag.getCity();
                            pardakht.GoalUsage = tag.getGoalUsage();
                            pardakht.neeyat = tag.getNeeyat();
                            pardakht.nameFamily = tag.getNameFamily();
                            pardakht.datetime = tag.getDatetime();
                            pardakht.OrderIdApp = tag.getOrderIdApp();
                            pardakht.orgName = tag.getOrgName();
                            pardakht.OrderIdBank = tag.getOrderIdBank();
                            pardakht.IsSubmit = tag.isSubmit();
                            Array_class_get.array_m_imputPardakht.add(pardakht);
                        }

                    }

                    historypay = new Adapter_Historypay(a_historypay.this,  Array_class_get.array_m_imputPardakht);

                    int resId = R.anim.layout_animation_slide_from_right;

                    LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_historypay.this, resId);

                    last_ecycle.setLayoutAnimation(animation);

                    last_ecycle.setAdapter(historypay);


                } else {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_historypay.this);
                    dialogBuilder
                            .withTitle("پیغام")
                            .withMessage("اطلاعاتی در دسترس نیست...")
                            .show();
                }

            }

            @Override
            public void failure(RetrofitError error) {

                mDialog.dismiss();
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_historypay.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
