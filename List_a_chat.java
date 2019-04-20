package com.zare.karbala;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
 import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_List_ziarat;
import Adapter.Adapter_chat_list;
import Adapter.adp_chat;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.MasolInfo;
import Model.PlaceIdName_Arabic;
import Model.PlaceIdName_English;
import Model.m_UserInfo2;
import Model.masolNameID;
import Model.sharedprefrencce;
import Model.ziaratMajazi2Lang;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class List_a_chat extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private final Context mContext = this;

    private SignalRService mService;

    private boolean mBound = false;

    public TextView txtMessage;

    private String message = "";
    SharedPreferences prefs;
    String conid, lan;

    BottomBar bottomBar;

    RecyclerView chat_Recycle;

    GridLayoutManager mLayoutManager;

    Adapter_chat_list adapter_get_name;

    String MyToken, MyMasol;

    SearchView mSearch_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_a_chat);

        mSearch_View = (SearchView) findViewById(R.id.mSearch_View);


        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        Intent intent = new Intent();
        intent.setClass(mContext, SignalRService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        //txtMessage = (TextView) findViewById(R.id.txtMessage);

        registerReceiver(broadcastReceiver, new IntentFilter(SignalRService.BROADCAST_ACTION));


        chat_Recycle = (RecyclerView) findViewById(R.id.chat_Recycle);

        chat_Recycle.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(List_a_chat.this, 1);

        chat_Recycle.setLayoutManager(mLayoutManager);

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);


        bottomBar.setDefaultTab(R.id.List_masolin);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.last_chat) {
                    startActivity(new Intent(List_a_chat.this, chat_msg.class));
                    finish();
                } else if (tabId == R.id.List_masolin) {

                }
            }
        });

        if (Array_class_get.m_UserInfo2.size() != 0) {
            int resId = R.anim.layout_animation_slide_from_right;

            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(List_a_chat.this, resId);

            chat_Recycle.setLayoutAnimation(animation);

            adapter_get_name = new Adapter_chat_list(List_a_chat.this, Array_class_get.m_UserInfo2, "1");

            chat_Recycle.setAdapter(adapter_get_name);

            adapter_get_name.notifyDataSetChanged();
        } else {

        }

        setupSearchView();
    }
    private void setupSearchView() {
        mSearch_View.setIconifiedByDefault(false);
        mSearch_View.setOnQueryTextListener(this);
        mSearch_View.setSubmitButtonEnabled(true);
        mSearch_View.setQueryHint("جستجو براساس نام و استان");
        mSearch_View.setBackgroundColor(getResources().getColor(R.color.white));

    }

    public boolean onQueryTextChange(String newText) {
        adapter_get_name.filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

            MyToken = prefs.getString(ConfigApi.MyToken, "");

            MyMasol = prefs.getString(ConfigApi.MyMasol, "");

            if (MyMasol.equalsIgnoreCase("true")) {
                int resId = R.anim.layout_animation_slide_from_right;

                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(List_a_chat.this, resId);

                chat_Recycle.setLayoutAnimation(animation);

                adapter_get_name = new Adapter_chat_list(List_a_chat.this, Array_class_get.m_UserInfo2, "1");

                chat_Recycle.setAdapter(adapter_get_name);

                adapter_get_name.notifyDataSetChanged();
            } else {


                prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
                MyToken = prefs.getString(sharedprefrencce.MyToken, "");

                if ((MyToken.isEmpty())) {


                } else {


                    lan = prefs.getString(sharedprefrencce.MyToken, "");

                    if (lan.equalsIgnoreCase("arab")) {
                        String languageToLoad1 = "ar"; // your language

                        Locale locale1 = new Locale(languageToLoad1);
                        Locale.setDefault(locale1);
                        Configuration config1 = new Configuration();

                        config1.locale = locale1;
                        getBaseContext().getResources().updateConfiguration(config1,
                                getBaseContext().getResources().getDisplayMetrics());

                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(List_a_chat.this);
                        dialogBuilder
                                .withTitle("لم يتم تحديدك كمسؤول")
                                .withMessage("")
                                .show();

                    } else if (lan.equalsIgnoreCase("kingdom")) {

                        String languageToLoad1 = "en"; // your language

                        Locale locale1 = new Locale(languageToLoad1);
                        Locale.setDefault(locale1);
                        Configuration config1 = new Configuration();

                        config1.locale = locale1;
                        getBaseContext().getResources().updateConfiguration(config1,
                                getBaseContext().getResources().getDisplayMetrics());

                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(List_a_chat.this);
                        dialogBuilder
                                .withTitle("You are not designated as responsible.")
                                .withMessage("")
                                .show();

                    } else if (lan.equalsIgnoreCase("iran")) {

                        String languageToLoad1 = "fa"; // your language

                        Locale locale1 = new Locale(languageToLoad1);
                        Locale.setDefault(locale1);
                        Configuration config1 = new Configuration();

                        config1.locale = locale1;
                        getBaseContext().getResources().updateConfiguration(config1,
                                getBaseContext().getResources().getDisplayMetrics());

                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(List_a_chat.this);
                        dialogBuilder
                                .withTitle("شما به عنوان مسئول تعیین نشده اید.")
                                .withMessage("اجازه دسترسی ندارید.")
                                .show();

                    }
                }

            }


        }
    };

    @Override
    protected void onStop() {
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        super.onStop();
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to SignalRService, cast the IBinder and get SignalRService instance
            SignalRService.LocalBinder binder = (SignalRService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(List_a_chat.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void get_chat() {

        Array_class_get.m_UserInfo2.clear();
        final ProgressDialog mDialog = new ProgressDialog(List_a_chat.this);

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
                .setEndpoint("http://88.198.102.229:4444")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .build();
        ApiService service = restAdapter.create(ApiService.class);
        service.chat(new Callback<List<MasolInfo>>() {
            @Override
            public void success(List<MasolInfo> masolNameIDS, Response response) {

                int m = 0;

                for (MasolInfo tag : masolNameIDS) {
                    m_UserInfo2 info2 = new m_UserInfo2();
                    info2.Id = tag.getUserID();
                    info2.connectionID = tag.getConnectionID();
                    info2.nameFamily = tag.getNameFamily();
                    info2.profilePic = tag.getPicAddress();
                    Array_class_get.m_UserInfo2.add(info2);

                }

            }

            @Override
            public void failure(RetrofitError error) {

                prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
                MyToken = prefs.getString(sharedprefrencce.MyToken, "");

                if ((MyToken.isEmpty())) {


                } else {


                    lan = prefs.getString(sharedprefrencce.MyToken, "");

                    if (lan.equalsIgnoreCase("arab")) {
                        String languageToLoad1 = "ar"; // your language

                        Locale locale1 = new Locale(languageToLoad1);
                        Locale.setDefault(locale1);
                        Configuration config1 = new Configuration();

                        config1.locale = locale1;
                        getBaseContext().getResources().updateConfiguration(config1,
                                getBaseContext().getResources().getDisplayMetrics());

                        String errorMsg = "حاول مرة أخرى";
                        Toast.makeText(List_a_chat.this, errorMsg, Toast.LENGTH_SHORT).show();

                    } else if (lan.equalsIgnoreCase("kingdom")) {

                        String languageToLoad1 = "en"; // your language

                        Locale locale1 = new Locale(languageToLoad1);
                        Locale.setDefault(locale1);
                        Configuration config1 = new Configuration();

                        config1.locale = locale1;
                        getBaseContext().getResources().updateConfiguration(config1,
                                getBaseContext().getResources().getDisplayMetrics());

                        String errorMsg = "try again";
                        Toast.makeText(List_a_chat.this, errorMsg, Toast.LENGTH_SHORT).show();


                    } else if (lan.equalsIgnoreCase("iran")) {

                        String languageToLoad1 = "fa"; // your language

                        Locale locale1 = new Locale(languageToLoad1);
                        Locale.setDefault(locale1);
                        Configuration config1 = new Configuration();

                        config1.locale = locale1;
                        getBaseContext().getResources().updateConfiguration(config1,
                                getBaseContext().getResources().getDisplayMetrics());

                        String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                        Toast.makeText(List_a_chat.this, errorMsg, Toast.LENGTH_SHORT).show();

                    }
                }

                mDialog.dismiss();
            }
        });

    }


}