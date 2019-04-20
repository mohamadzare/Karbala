package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Masol_Spinner;
import Adapter.Adapter_detialz_qoran;
import Adapter.Adapter_get_ticket;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.GetAllMessages;
import Model.GetTiksInfo;
import Model.M_masolNameID;
import Model.ParamGetTickList;
import Model.TickHistoryParam;
import Model.masolNameID;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_get_ticket extends AppCompatActivity {

    RecyclerView _Recycle;

    String MyToken,lan;

    Adapter_get_ticket adapter_get;

    SharedPreferences prefs;

    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_get_ticket);

        String languageToLoad = "fa"; // your language

        Locale locale = new Locale(languageToLoad);

        Locale.setDefault(locale);

        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
      
      
        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
       
        MyToken = prefs.getString(sharedprefrencce.MyToken, "");

       
 
        ParamGetTickList getTickList = new ParamGetTickList();
       
        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_get_ticket.this);
            dialogBuilder
                    .withTitle("شما به عنوان مسئول تعیین  شده اید.")
                    .withMessage("اجازه دسترسی ندارید.")
                    .show();
        } else {

            get_tcket();
        }


        _Recycle = (RecyclerView) findViewById(R.id.ticket_khan_Recycle);

        _Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_get_ticket.this, 1);

        _Recycle.setLayoutManager(mLayoutManager);


        int resId = R.anim.layout_animation_scroll_from_bottom;

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_get_ticket.this, resId);

        _Recycle.setLayoutAnimation(animation);


    }


    public void get_tcket() {
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        Array_class_get.array_GetTiksInfo.clear();

        ParamGetTickList getTickList = new ParamGetTickList();
        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            getTickList.IsMasol = true;
        } else {
            getTickList.IsMasol = false;
        }

        getTickList.token = MyToken;

        final ProgressDialog mDialog = new ProgressDialog(a_get_ticket.this);

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
        service.GetTikIDList(getTickList, new Callback<List<GetTiksInfo>>() {


            @Override
            public void success(List<GetTiksInfo> masolNameIDS, Response response) {

                mDialog.dismiss();

                for (GetTiksInfo tag : masolNameIDS) {

                    m_GetTiksInfo m_masolNameID = new m_GetTiksInfo();
                    m_masolNameID.ClientName = tag.getClientName();
                    m_masolNameID.dateTime = tag.getDateTime();
                    m_masolNameID.IsOpen = tag.getIsOpen();
                    m_masolNameID.MasolName = tag.getMasolName();
                    m_masolNameID.Rank = tag.getRank();
                    m_masolNameID.tickID = tag.getTickID();
                    Array_class_get.array_GetTiksInfo.add(m_masolNameID);
                }
                adapter_get = new Adapter_get_ticket(a_get_ticket.this, Array_class_get.array_GetTiksInfo, "1");

                _Recycle.setAdapter(adapter_get);
            }

            @Override
            public void failure(RetrofitError error) {
               
                if ((MyToken.isEmpty())) {
                    String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                    Toast.makeText(a_get_ticket.this, errorMsg, Toast.LENGTH_SHORT).show();

                } else {

                    SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    lan = prefs.getString(sharedprefrencce.MyToken, "");

                    if (lan.equalsIgnoreCase("arab")) {

                        String errorMsg = "حاول مرة أخرى في وقت لاحق";
                        Toast.makeText(a_get_ticket.this, errorMsg, Toast.LENGTH_SHORT).show();


                    } else if (lan.equalsIgnoreCase("kingdom")) {
                        String errorMsg = "Try Again Later...";
                        Toast.makeText(a_get_ticket.this, errorMsg, Toast.LENGTH_SHORT).show();

                    } else if (lan.equalsIgnoreCase("iran")) {

                        String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                        Toast.makeText(a_get_ticket.this, errorMsg, Toast.LENGTH_SHORT).show();


                    }
                }
                mDialog.dismiss();
            }
        });

    }

    public void get_TickHistory() {

        Array_class_get.array_GetTiksInfo.clear();

        TickHistoryParam getTickList = new TickHistoryParam();

        getTickList.IsMasol = false;

        getTickList.token = "9ZIO7UL6";

        getTickList.tickID = 1;

        final ProgressDialog mDialog = new ProgressDialog(a_get_ticket.this);

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
        service.TickHistory(getTickList, new Callback<GetAllMessages>() {


            @Override
            public void success(GetAllMessages masolNameIDS, Response response) {

                mDialog.dismiss();

//                for (GetTiksInfo tag : masolNameIDS) {
//
//                    m_GetTiksInfo m_masolNameID = new m_GetTiksInfo();
//                    m_masolNameID.ClientName = tag.getClientName();
//                    m_masolNameID.dateTime = tag.getDateTime();
//                    m_masolNameID.IsOpen = tag.getIsOpen();
//                    m_masolNameID.MasolName = tag.getMasolName();
//                    m_masolNameID.Rank = tag.getRank();
//                    m_masolNameID.tickID = tag.getTickID();
//                    Array_class_get.array_GetTiksInfo.add(m_masolNameID);
//                }
            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_get_ticket.this, errorMsg, Toast.LENGTH_SHORT).show();

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
        startActivity(new Intent(a_get_ticket.this, a_Circle_list_menu.class));
        finish();
    }
}
