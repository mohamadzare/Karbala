package com.zare.karbala;

import android.app.Activity;
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
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Intro_setad;
import Adapter.Adapter_choosen_mokeb;
import Adapter.ExpandableListAdapter;
import Adapter.Group_Class;
import Adapter.VerticalRecyclerViewAdapter;
import Adapter.VerticalRecyclerViewAdapter_OSTAN;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.HorizontalModel;
import Model.M_shahr_ex;
import Model.VerticalModel;
import Model.m_Ostan;
import Model.m_Shahr;
import Model.m_setadList;
import Model.multiLangSetadIntro;
import Model.setadIntroGetParam;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_intro_setad extends AppCompatActivity {
    String lan;

    SharedPreferences prefs_s;

    RecyclerView _Recycle;

    Adapter_Intro_setad title;

    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<VerticalModel> mArrayList = new ArrayList<>();
    VerticalRecyclerViewAdapter_OSTAN adapter_au;


    private ExpandableListAdapter ExpAdapter;
    private ArrayList<Group_Class> ExpListItems;
    private ExpandableListView ExpandList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_intro_setad);

        String languageToLoad1 = "fa"; // your language

        Locale locale1 = new Locale(languageToLoad1);
        Locale.setDefault(locale1);
        Configuration config1 = new Configuration();

        config1.locale = locale1;
        getBaseContext().getResources().updateConfiguration(config1,
                getBaseContext().getResources().getDisplayMetrics());

        ExpandList = (ExpandableListView) findViewById(R.id.simple_expandable_listview);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandableListAdapter(a_intro_setad.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);


        ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                ;
                ArrayList<M_shahr_ex> chList = ExpListItems.get(groupPosition)
                        .getItems();

                get_city(ExpListItems.get(groupPosition).name, chList.get(childPosition).name);

                return true;
            }
        });


//        _Recycle = (RecyclerView) findViewById(R.id.intro_Recycle);
//
//        _Recycle.setHasFixedSize(true);
//
//        mLayoutManager = new GridLayoutManager(a_intro_setad.this, 2);
//
//        _Recycle.setLayoutManager(mLayoutManager);
//
//
//        int resId = R.anim.layout_animation_scroll_from_bottom;
//
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_intro_setad.this, resId);
//
//        _Recycle.setLayoutAnimation(animation);
//
//        title = new Adapter_Intro_setad(a_intro_setad.this, Array_class_get.array_m_Ostan, "1");
//
//        _Recycle.setAdapter(title);
    }

    public ArrayList<Group_Class> SetStandardGroups() {

        ArrayList<Group_Class> group_list = new ArrayList<Group_Class>();

        ArrayList<M_shahr_ex> child_list;

        for (int i = 0; i < Array_class_get.array_m_Ostan.size(); i++) {
            Group_Class gru1 = new Group_Class();

            child_list = new ArrayList<M_shahr_ex>();

            gru1.setName(Array_class_get.array_m_Ostan.get(i).Name);

            group_list.add(gru1);

            for (int t = 0; t < Array_class_get.array_m_Shahr.size(); t++) {

                if (Array_class_get.array_m_Shahr.get(t).idOstan == Array_class_get.array_m_Ostan.get(i).id) {

                    M_shahr_ex ch1_1 = new M_shahr_ex();

                    ch1_1.setName(Array_class_get.array_m_Shahr.get(t).name);

                    child_list.add(ch1_1);

                    gru1.setItems(child_list);
                }

            }

        }


        return group_list;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setDataOnVerticalRecyclerView() {
        for (int i = 0; i < Array_class_get.array_m_Ostan.size(); i++) {

            VerticalModel mVerticalModel = new VerticalModel();

            mVerticalModel.setTitle(Array_class_get.array_m_Ostan.get(i).Name);

            ArrayList<HorizontalModel> arrayList = new ArrayList<>();

            for (int j = 0; j < Array_class_get.array_m_Shahr.size(); j++) {

                if ((Array_class_get.array_m_Ostan.get(i).id) == (Array_class_get.array_m_Shahr.get(j).idOstan)) {

                    HorizontalModel mHorizontalModel = new HorizontalModel();

                    mHorizontalModel.setDescription(Array_class_get.array_m_Shahr.get(j).name);

//                    mHorizontalModel.setTime(Array_class_get.array_hadis_persian.get(j).time);
//
//                    mHorizontalModel.setText(Array_class_get.array_hadis_persian.get(j).text);
//
//                    mHorizontalModel.setAuthors(Array_class_get.array_hadis_persian.get(j).authors);

                    // mHorizontalModel.setId(Array_list_class.Menu_Subtitle.get(j).id_menu);

                    arrayList.add(mHorizontalModel);
                }

            }

            mVerticalModel.setArrayList(arrayList);

            mArrayList.add(mVerticalModel);

        }
        _Recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        adapter_au = new VerticalRecyclerViewAdapter_OSTAN(this, mArrayList);

        _Recycle.setAdapter(adapter_au);


        int resId = R.anim.layout_animation_scroll_from_bottom;

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(a_intro_setad.this, resId);

        _Recycle.setLayoutAnimation(animation);
        adapter_au.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_intro_setad.this, a_Circle_list_menu.class));
        finish();
    }

    public void get_city(String ostan, String shahr) {

        Array_class_get.array_mokebs.clear();
        Array_class_get.m_setadList.clear();
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        prefs_s = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

        String My_La = prefs_s.getString(sharedprefrencce.MyToken, "");


        setadIntroGetParam mokeb = new setadIntroGetParam();

        if (My_La.equalsIgnoreCase("arab")) {

            mokeb.lang = "arabic";
        } else if (My_La.equalsIgnoreCase("kingdom")) {


            mokeb.lang = "english";
        } else if (My_La.equalsIgnoreCase("iran")) {

            mokeb.lang = "persian";
        } else {
            mokeb.lang = "persian";
        }

        mokeb.stateName = ostan;
        mokeb.CityName = shahr;

        final ProgressDialog mDialog = new ProgressDialog(a_intro_setad.this);

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
        service.setadIntro_(mokeb, new Callback<multiLangSetadIntro>() {
            @Override
            public void success(multiLangSetadIntro object, Response response) {

                mDialog.dismiss();

                prefs_s = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                String My_La = prefs_s.getString(sharedprefrencce.MyToken, "");

                if (object == null) {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_intro_setad.this);
                    dialogBuilder
                            .withTitle("پیغام")
                            .withMessage("اطلاعاتی در دسترس نیست...")
                            .show();
                } else {

                    if ((My_La.isEmpty())) {

                        m_setadList m_Up = new m_setadList();
                        m_Up.sID = object.setadlist.sID;
                        m_Up.biography = object.setadlist.biography;
                        m_Up.city =object.setadlist.city;
                        m_Up.setadName = object.setadlist.setadName;
                        m_Up.address = object.setadlist.address;
                        m_Up.masolName = object.setadlist.masolName;
                        m_Up.phone1 = object.setadlist.phone1;
                        m_Up.phone2 = object.setadlist.phone2;
                        m_Up.state = object.setadlist.state;
                        m_Up.lang = object.setadlist.lang;
                        m_Up.lat = object.setadlist.lat;
                        Array_class_get.m_setadList.add(m_Up);


                    } else {

                        SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                        lan = prefs.getString(sharedprefrencce.MyToken, "");


                        if (lan.equalsIgnoreCase("arab")) {
                            m_setadList m_Up = new m_setadList();
                            m_Up.sID = object.setadlistArab.sID;
                            m_Up.biography = object.setadlistArab.biography;
                            m_Up.city =object.setadlistArab.city;
                            m_Up.setadName = object.setadlistArab.setadName;
                            m_Up.address = object.setadlistArab.address;
                            m_Up.masolName = object.setadlistArab.masolName;
                            m_Up.phone1 = object.setadlistArab.phone1;
                            m_Up.phone2 = object.setadlistArab.phone2;
                            m_Up.state = object.setadlistArab.state;
                            m_Up.lang = object.setadlistArab.lang;
                            m_Up.lat = object.setadlistArab.lat;
                            Array_class_get.m_setadList.add(m_Up);

                        } else if (lan.equalsIgnoreCase("kingdom")) {
                            m_setadList m_Up = new m_setadList();
                            m_Up.sID = object.setadlistEng.sID;
                            m_Up.biography = object.setadlistEng.biography;
                            m_Up.city =object.setadlistEng.city;
                            m_Up.setadName = object.setadlistEng.setadName;
                            m_Up.address = object.setadlistEng.address;
                            m_Up.masolName = object.setadlistEng.masolName;
                            m_Up.phone1 = object.setadlistEng.phone1;
                            m_Up.phone2 = object.setadlistEng.phone2;
                            m_Up.state = object.setadlistEng.state;
                            m_Up.lang = object.setadlistEng.lang;
                            m_Up.lat = object.setadlistEng.lat;
                            Array_class_get.m_setadList.add(m_Up);

                        } else if (lan.equalsIgnoreCase("iran")) {


                            m_setadList m_Up = new m_setadList();
                            m_Up.sID = object.setadlist.sID;
                            m_Up.biography = object.setadlist.biography;
                            m_Up.city =object.setadlist.city;
                            m_Up.setadName = object.setadlist.setadName;
                            m_Up.address = object.setadlist.address;
                            m_Up.masolName = object.setadlist.masolName;
                            m_Up.phone1 = object.setadlist.phone1;
                            m_Up.phone2 = object.setadlist.phone2;
                            m_Up.state = object.setadlist.state;
                            m_Up.lang = object.setadlist.lang;
                            m_Up.lat = object.setadlist.lat;
                            Array_class_get.m_setadList.add(m_Up);

                        }
                    }

                    Intent inn = new Intent(a_intro_setad.this, a_moarefi_setad.class);
                    startActivity(inn);
                     finish();
                }
                mDialog.dismiss();
                try {

                } catch (Exception e) {
                }

                mDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {

                mDialog.dismiss();
            }
        });

    }
}
