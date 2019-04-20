package com.zare.karbala;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.jh.circularlist.CircularAdapter;
import com.jh.circularlist.CircularListView;
import com.jh.circularlist.CircularTouchListener;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipOutputStream;

import DataBase.db_class;
import DataBase.field_db_city;
import DataBase.field_db_ostan;
import Model.ConfigApi;
import Model.ParamGetTickList;
import Model.sharedprefrencce;
import spencerstudios.com.bungeelib.Bungee;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_Circle_list_menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CircularItemAdapter adapter;

    String lan;

    SharedPreferences prefs;

    List<field_db_city> obj_city;

    List<field_db_ostan> obj_ostan;

    db_class aClass;

    TextView ckrb;

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    private int selectedItem;

    BottomSheetDialog d;


    ImageView arab, iran, kingdom;

    Menu menu;

    NavigationView navigationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a__circle_list_menu);


        String languageToLoad = "en"; // your language

        Locale locale = new Locale(languageToLoad);

        Locale.setDefault(locale);

        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());



        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);

        ImageView menuRight = (ImageView) findViewById(R.id.menuRight);

        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

         navigationView1 = (NavigationView) findViewById(R.id.menu_drawer);

         menu = navigationView1.getMenu();

        navigationView1.setNavigationItemSelectedListener(this);



        aClass = new db_class(a_Circle_list_menu.this);


        ckrb = (TextView) findViewById(R.id.ckrb);


        ArrayList<String> itemTitles = new ArrayList<>();
        int[] textureArrayWin = {
                R.drawable.news_main,
                R.drawable.location_main,
                R.drawable.nozorat,
                R.drawable.note,
                R.drawable.chat_main,
                R.drawable.gallery,
                R.drawable.ziarat
                , R.drawable.qoran,
                R.drawable.hadis,
                R.drawable.sabte,
                R.drawable.setad,
                //  R.drawable.tanz,
                R.drawable.news_main,
                R.drawable.contact
        };

        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");

        if ((MyToken.isEmpty())) {

            itemTitles.add("اخبار");
            itemTitles.add("موکب یاب");
            itemTitles.add("پرداخت نذورات");
            itemTitles.add("یادداشت روزانه");
            itemTitles.add("ارتباط با مسئولین");
            itemTitles.add("گالری");
            itemTitles.add("زیارت مجازی");
            itemTitles.add("قرآن خوان");
            itemTitles.add("دعا و حدیث");
            itemTitles.add("ثبت نام نیروهای متبرع");
            itemTitles.add("معرفی ستادها");
            // itemTitles.add("خدمات");
            itemTitles.add("توضیحات");

            MenuItem nav_camara = menu.findItem(R.id.nav_item_1);

            // set new title to the MenuItem
            nav_camara.setTitle("مدیریت پروفایل");

            // do the same for other MenuItems
            MenuItem nav_gallery = menu.findItem(R.id.nav_item_2);


            nav_gallery.setTitle(" صندوق پیام");

            MenuItem nav_item_3 = menu.findItem(R.id.nav_item_3);

            // set new title to the MenuItem
            nav_item_3.setTitle("تغییر زبان");

            // do the same for other MenuItems
            MenuItem nav_item_4 = menu.findItem(R.id.nav_item_4);


            nav_item_4.setTitle("گزارش تراکنش ");


            MenuItem nav_item_5 = menu.findItem(R.id.nav_item_5);

            // set new title to the MenuItem
            nav_item_5.setTitle("انتقاد و پیشنهادات ");

            // do the same for other MenuItems
            MenuItem nav_item_6 = menu.findItem(R.id.nav_item_6);


            nav_item_6.setTitle("ارتباط با ما");

            MenuItem nav_item_7 = menu.findItem(R.id.nav_item_7);


            nav_item_7.setTitle("خروج از نرم افزار");



        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");


            if (lan.equalsIgnoreCase("arab")) {


                itemTitles.add(getResources().getString(R.string.name_khabar_arab));
                itemTitles.add(getResources().getString(R.string.name_mokeb_arab));
                itemTitles.add(getResources().getString(R.string.name_pay_arab));
                itemTitles.add(getResources().getString(R.string.name_Note_arab));
                itemTitles.add(getResources().getString(R.string.name_con_arb));
                itemTitles.add(getResources().getString(R.string.name_pic_arab));
                itemTitles.add(getResources().getString(R.string.name_ziarat_arab));
                itemTitles.add(getResources().getString(R.string.name_qari_arab));
                itemTitles.add(getResources().getString(R.string.name_hadis_arab));
                itemTitles.add(getResources().getString(R.string.name_sabtenam_arab));
                itemTitles.add(getResources().getString(R.string.name_setad_arab));
                // itemTitles.add(getResources().getString(R.string.name_khadamat_arab));
                itemTitles.add(getResources().getString(R.string.name_tozi_arab));
                //  itemTitles.add(getResources().getString(R.string.name_contac_arab));
                ckrb.setText("تعال لجعل كربلاء معا");


                MenuItem nav_camara = menu.findItem(R.id.nav_item_1);

                // set new title to the MenuItem
                nav_camara.setTitle("الملف الشخصي");

                // do the same for other MenuItems
                MenuItem nav_gallery = menu.findItem(R.id.nav_item_2);


                nav_gallery.setTitle("مربع الرسالة");




                MenuItem nav_item_3 = menu.findItem(R.id.nav_item_3);

                // set new title to the MenuItem
                nav_item_3.setTitle("تغيير اللغة");

                // do the same for other MenuItems
                MenuItem nav_item_4 = menu.findItem(R.id.nav_item_4);


                nav_item_4.setTitle("تقرير المعاملات");


                MenuItem nav_item_5 = menu.findItem(R.id.nav_item_5);

                // set new title to the MenuItem
                nav_item_5.setTitle("ردود الفعل والاقتراحات");

                // do the same for other MenuItems
                MenuItem nav_item_6 = menu.findItem(R.id.nav_item_6);


                nav_item_6.setTitle("اتصل بنا");

                MenuItem nav_item_7 = menu.findItem(R.id.nav_item_7);


                nav_item_7.setTitle("قم بإنهاء البرنامج");

            } else if (lan.equalsIgnoreCase("kingdom")) {


                itemTitles.add(getResources().getString(R.string.name_khabar_pe_en));
                itemTitles.add(getResources().getString(R.string.name_mokeb_en));
                itemTitles.add(getResources().getString(R.string.name_pay_en));
                itemTitles.add(getResources().getString(R.string.name_Note_en));
                itemTitles.add(getResources().getString(R.string.name_con_en));
                itemTitles.add(getResources().getString(R.string.name_pic_en));
                itemTitles.add(getResources().getString(R.string.name_ziarat_en));
                itemTitles.add(getResources().getString(R.string.name_qari_en));
                itemTitles.add(getResources().getString(R.string.name_hadis_en));
                itemTitles.add(getResources().getString(R.string.name_sabtenam_en));
                itemTitles.add(getResources().getString(R.string.name_setad_en));
                //  itemTitles.add(getResources().getString(R.string.name_khadamat_en));
                itemTitles.add(getResources().getString(R.string.name_tozi_en));
                // itemTitles.add(getResources().getString(R.string.name_contac_en));

                ckrb.setText("    Come to make Karbala together");



                MenuItem nav_camara = menu.findItem(R.id.nav_item_1);

                // set new title to the MenuItem
                nav_camara.setTitle("Profile");

                // do the same for other MenuItems
                MenuItem nav_gallery = menu.findItem(R.id.nav_item_2);


                nav_gallery.setTitle("Inbox");

                MenuItem nav_item_3 = menu.findItem(R.id.nav_item_3);

                // set new title to the MenuItem
                nav_item_3.setTitle("Language");

                // do the same for other MenuItems
                MenuItem nav_item_4 = menu.findItem(R.id.nav_item_4);


                nav_item_4.setTitle("Transaction report");


                MenuItem nav_item_5 = menu.findItem(R.id.nav_item_5);

                // set new title to the MenuItem
                nav_item_5.setTitle("suggestions");

                // do the same for other MenuItems
                MenuItem nav_item_6 = menu.findItem(R.id.nav_item_6);


                nav_item_6.setTitle("Contact");

                MenuItem nav_item_7 = menu.findItem(R.id.nav_item_7);


                nav_item_7.setTitle("Exit");
            } else if (lan.equalsIgnoreCase("iran")) {


                itemTitles.add("اخبار");
                itemTitles.add("موکب یاب");
                itemTitles.add("پرداخت نذورات");
                itemTitles.add("یادداشت روزانه");
                itemTitles.add("ارتباط با مسئولین");
                itemTitles.add("گالری");
                itemTitles.add("زیارت مجازی");
                itemTitles.add("قرآن خوان");
                itemTitles.add("دعا و حدیث");
                itemTitles.add("ثبت نام نیروهای متبرع");
                itemTitles.add("معرفی ستادها");
                //itemTitles.add("خدمات");
                itemTitles.add("توضیحات");
                //  itemTitles.add("ارتباط با ما");

                ckrb.setText("بیاید باهم کربلا را بسازیم");



                MenuItem nav_camara = menu.findItem(R.id.nav_item_1);

                // set new title to the MenuItem
                nav_camara.setTitle("مدیریت پروفایل");

                // do the same for other MenuItems
                MenuItem nav_gallery = menu.findItem(R.id.nav_item_2);


                nav_gallery.setTitle(" صندوق پیام");




                MenuItem nav_item_3 = menu.findItem(R.id.nav_item_3);

                // set new title to the MenuItem
                nav_item_3.setTitle("تغییر زبان");

                // do the same for other MenuItems
                MenuItem nav_item_4 = menu.findItem(R.id.nav_item_4);


                nav_item_4.setTitle("گزارش تراکنش ");


                MenuItem nav_item_5 = menu.findItem(R.id.nav_item_5);

                // set new title to the MenuItem
                nav_item_5.setTitle("انتقاد و پیشنهادات ");

                // do the same for other MenuItems
                MenuItem nav_item_6 = menu.findItem(R.id.nav_item_6);


                nav_item_6.setTitle("ارتباط با ما");

                MenuItem nav_item_7 = menu.findItem(R.id.nav_item_7);


                nav_item_7.setTitle("خروج از نرم افزار");


            }
        }


        final CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);
        adapter = new CircularItemAdapter(getLayoutInflater(), itemTitles, textureArrayWin);
        circularListView.setAdapter(adapter);
        circularListView.setRadius(50);
        circularListView.setOnItemClickListener(new CircularTouchListener.CircularItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {

                TextView itemView = (TextView) view.findViewById(R.id.bt_item);


                if (itemView.getText().toString().trim().equalsIgnoreCase("اخبار") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_khabar_pe_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_khabar_arab))) {


                    startActivity(new Intent(a_Circle_list_menu.this, a_news.class));
                    finish();


                } else if (itemView.getText().toString().trim().equalsIgnoreCase("موکب یاب") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_mokeb_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_mokeb_arab))) {

                    startActivity(new Intent(a_Circle_list_menu.this, a_choosen_mokeb.class));
                    finish();
                } else if (itemView.getText().toString().trim().equalsIgnoreCase("پرداخت نذورات") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_pay_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_pay_arab))) {

                            startActivity(new Intent(a_Circle_list_menu.this,a_payment.class));
                            finish();
                } else if (itemView.getText().toString().trim().equalsIgnoreCase("یادداشت روزانه") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_Note_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_Note_arab))) {
                    startActivity(new Intent(a_Circle_list_menu.this, AlarmMe.class));
                    finish();
                } else if (itemView.getText().toString().trim().equalsIgnoreCase("ارتباط با مسئولین") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_con_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_con_arb))) {

                    SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
                    final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

                    if (MyMasol.equalsIgnoreCase("true")) {
                        startActivity(new Intent(a_Circle_list_menu.this, List_a_chat.class));
                        finish();
                    } else {

                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_Circle_list_menu.this);
                        dialogBuilder
                                .withTitle("شما به عنوان مسئول تعیین  نشده اید.")
                                .withMessage("اجازه دسترسی ندارید.")
                                .show();
                    }


                } else if (itemView.getText().toString().trim().equalsIgnoreCase("گالری") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_pic_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_pic_arab))) {
                    startActivity(new Intent(a_Circle_list_menu.this, a_gallery.class));
                    finish();
                } else if (itemView.getText().toString().trim().equalsIgnoreCase("زیارت مجازی") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_ziarat_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_ziarat_arab))) {

                    startActivity(new Intent(a_Circle_list_menu.this, a_ziarate_majazi.class));
                    finish();
                    Bungee.slideRight(a_Circle_list_menu.this);
                } else if (itemView.getText().toString().trim().equalsIgnoreCase("قرآن خوان") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_qari_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_qari_arab))) {

                    startActivity(new Intent(a_Circle_list_menu.this, a_qoran.class));
                    finish();
                } else if (itemView.getText().toString().trim().equalsIgnoreCase("دعا و حدیث") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_hadis_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_hadis_arab))) {
                    startActivity(new Intent(a_Circle_list_menu.this, a_hadis.class));
                    finish();
                    Bungee.slideRight(a_Circle_list_menu.this);
                } else if (itemView.getText().toString().trim().equalsIgnoreCase("ثبت نام نیروهای متبرع") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_sabtenam_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_sabtenam_arab))) {

                    startActivity(new Intent(a_Circle_list_menu.this, a_register_guys_motabare.class));
                    finish();
                    Bungee.slideRight(a_Circle_list_menu.this);

                } else if (itemView.getText().toString().trim().equalsIgnoreCase("معرفی ستادها") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_setad_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_setad_arab))) {

                    startActivity(new Intent(a_Circle_list_menu.this, a_intro_setad.class));
                    finish();
                    Bungee.slideRight(a_Circle_list_menu.this);
//                } else if (itemView.getText().toString().trim().equalsIgnoreCase("خدمات") ||
//                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_khadamat_en)) ||
//                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_khadamat_arab))) {
//
//                    startActivity(new Intent(a_Circle_list_menu.this, a_settings.class));
//                    finish();
                } else if (itemView.getText().toString().trim().equalsIgnoreCase("توضیحات") ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_tozi_en)) ||
                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_tozi_arab))) {

                    startActivity(new Intent(a_Circle_list_menu.this, a_tozihat.class));
                    finish();
                    Bungee.slideRight(a_Circle_list_menu.this);
                }
//
//                else if (itemView.getText().toString().trim().equalsIgnoreCase("ارتباط با ما") ||
//                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_contac_en)) ||
//                        itemView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.name_contac_arab))) {
//
//                    startActivity(new Intent(a_Circle_list_menu.this, a_about.class));
//                    finish();
//                    Bungee.slideRight(a_Circle_list_menu.this);
//                }


            }
        });


        d = new BottomSheetDialog(this);

        d.setContentView(R.layout.dialog_language);

        d.setCancelable(true);
        arab = (ImageView) d.findViewById(R.id.arab);
        iran = (ImageView) d.findViewById(R.id.iran);
        kingdom = (ImageView) d.findViewById(R.id.kingdom);

        arab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
                prefs.edit().remove(sharedprefrencce.MyToken).commit();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(sharedprefrencce.MyToken, "arab");
                editor.apply();

                finish();
                startActivity(getIntent());
            }
        });
        iran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
                prefs.edit().remove(sharedprefrencce.MyToken).commit();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(sharedprefrencce.MyToken, "iran");
                editor.apply();
                finish();
                startActivity(getIntent());
            }
        });

        kingdom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
                prefs.edit().remove(sharedprefrencce.MyToken).commit();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(sharedprefrencce.MyToken, "kingdom");
                editor.apply();
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String text = "";
        if (id == R.id.nav_item_1) {

            startActivity(new Intent(a_Circle_list_menu.this, a_profile.class));
            finish();
        } else if (id == R.id.nav_item_2) {
            startActivity(new Intent(a_Circle_list_menu.this, a_get_ticket.class));
            finish();
        } else if (id == R.id.nav_item_3) {
            d.show();

        } else if (id == R.id.nav_item_4) {

            startActivity(new Intent(a_Circle_list_menu.this, a_historypay.class));
            finish();
        } else if (id == R.id.nav_item_5) {
            startActivity(new Intent(a_Circle_list_menu.this, a_message_inbox.class));
            finish();
        } else if (id == R.id.nav_item_6) {


            startActivity(new Intent(a_Circle_list_menu.this, a_about.class));
            finish();
        } else if (id == R.id.nav_item_7) {
            SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
            prefs.edit().remove(ConfigApi.MyToken).commit();
            prefs.edit().remove(ConfigApi.MyMasol).commit();
            prefs.edit().remove(ConfigApi.MyUser).commit();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);

        drawer.closeDrawer(GravityCompat.END);
        return true;
    }


    private class CircularItemAdapter extends CircularAdapter {
        private ArrayList<String> mItems;
        private LayoutInflater mInflater;
        private ArrayList<View> mItemViews;

        public CircularItemAdapter(LayoutInflater inflater, ArrayList<String> items, int[] img) {
            this.mItemViews = new ArrayList<>();
            this.mItems = items;
            this.mInflater = inflater;

            int sxx = 0;
            for (final String s : mItems) {
                View view = mInflater.inflate(R.layout.view_circular_item, null);
                TextView itemView = (TextView) view.findViewById(R.id.bt_item);
                Drawable d = getResources().getDrawable(img[sxx]);
                ImageView imageView = (ImageView) view.findViewById(R.id.item_icon);
                imageView.setImageDrawable(d);
                itemView.setText(s);
                mItemViews.add(view);
                sxx++;
            }
        }

        @Override
        public ArrayList<View> getAllViews() {
            return mItemViews;
        }

        @Override
        public int getCount() {
            return mItemViews.size();
        }

        @Override
        public View getItemAt(int i) {
            return mItemViews.get(i);
        }

        @Override
        public void removeItemAt(int i) {
            if (mItemViews.size() > 0) {
                mItemViews.remove(i);
                notifyItemChange();
            }
        }

        @Override
        public void addItem(View view) {
            mItemViews.add(view);
            notifyItemChange();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


//    public void get_shahr() {
//        Array_class_get.array_m_Shahr.clear();
//        obj_city = aClass.getAll_shahr();
//        for (field_db_city tag : obj_city) {
//            m_Shahr m_inspection = new m_Shahr();
//            m_inspection.id = tag.getId();
//            m_inspection.idOstan = tag.getIdOstan();
//            m_inspection.name = tag.getName();
//            Array_class_get.array_m_Shahr.add(m_inspection);
//        }
//        get_ostan();
//    }
//
//    public void get_ostan() {
//        Array_class_get.array_m_Ostan.clear();
//        obj_ostan = aClass.getAll_ostan();
//        for (field_db_ostan tag : obj_ostan) {
//            m_Ostan m_inspection = new m_Ostan();
//            m_inspection.id = tag.getId();
//            m_inspection.Name = tag.getName();
//            Array_class_get.array_m_Ostan.add(m_inspection);
//        }
//    }
}
