package com.zare.karbala;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_chat_list;
import Adapter.Adapter_video_gallery;
import Adapter.Adaptrt_chat_msg_list;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.MasolInfo;
import Model.SendList;
import Model.m_SendList;
import Model.m_UserInfo2;
import Model.param_chat_list;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class chat_msg extends AppCompatActivity {

    RecyclerView chat_rc;

    Adaptrt_chat_msg_list adapter_get;

    RecyclerView.LayoutManager mLayoutManager;

    BottomBar bottomBar;

    String MyToken, MyMasol, lan;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_msg);

        prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

        MyToken = prefs.getString(ConfigApi.MyToken, "");

        MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            get_chat();
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

                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(chat_msg.this);
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

                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(chat_msg.this);
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

                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(chat_msg.this);
                    dialogBuilder
                            .withTitle("شما به عنوان مسئول تعیین نشده اید.")
                            .withMessage("اجازه دسترسی ندارید.")
                            .show();

                }
            }
        }


        chat_rc = (RecyclerView) findViewById(R.id.cha_Recycle);

        chat_rc.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(chat_msg.this, 1);

        chat_rc.setLayoutManager(mLayoutManager);


        String languageToLoad = "fa"; // your language

        Locale locale = new Locale(languageToLoad);

        Locale.setDefault(locale);

        Configuration config = new Configuration();

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setDefaultTab(R.id.last_chat);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.last_chat) {

                } else if (tabId == R.id.List_masolin) {
                    startActivity(new Intent(chat_msg.this, List_a_chat.class));
                    finish();
                }
                //else if (tabId == R.id.tickt) {
//                    Intent inn = new Intent(chat_msg.this, a_message_inbox.class);
//                    inn.putExtra("StateName", "chat_msg");
//                    startActivity(inn);
//                    finish();
//                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(chat_msg.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void get_chat() {
        Array_class_get.m_SendList.clear();
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        param_chat_list list = new param_chat_list();
        list.masolToken = MyToken;

        final ProgressDialog mDialog = new ProgressDialog(chat_msg.this);

        mDialog.setMessage("");

        mDialog.setCancelable(false);

        mDialog.show();


        final OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.setReadTimeout(600, TimeUnit.SECONDS);

        okHttpClient.setConnectTimeout(600, TimeUnit.SECONDS);

//        RequestInterceptor requestInterceptor = new RequestInterceptor() {
//            @Override
//            public void intercept(RequestFacade request) {
////                request.addHeader("Content-encoding", "gzip");
////                request.addHeader("Accept", "application/json");
//            }
//        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.mazandatabat.org:3001")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiService service = restAdapter.create(ApiService.class);
        service.chatlist(list, new Callback<List<SendList>>() {
            @Override
            public void success(List<SendList> masolNameIDS, Response response) {

                int m = 0;

                for (SendList tag : masolNameIDS) {
                    m_SendList info2 = new m_SendList();
                    info2.dateTime = tag.getDateTime();
                    info2.LastMessageType = tag.getLastMessageType();
                    info2.LastMessage = tag.getLastMessage();
                    info2.profilePics = tag.getProfilePics();
                    info2.toName = tag.getToName();
                    info2.toID = tag.getToID();
                    Array_class_get.m_SendList.add(info2);

                }
                int resId = R.anim.layout_animation_slide_from_right;

                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(chat_msg.this, resId);

                chat_rc.setLayoutAnimation(animation);

                adapter_get = new Adaptrt_chat_msg_list(chat_msg.this, Array_class_get.m_SendList, "1");

                chat_rc.setAdapter(adapter_get);

                adapter_get.notifyDataSetChanged();
                mDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {

                mDialog.dismiss();
            }
        });

    }
}
