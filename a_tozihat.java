package com.zare.karbala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import Model.sharedprefrencce;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_tozihat extends AppCompatActivity {

    TextView id_1, id_2, id_3, id_4, id_5, id_6, id_7, id_8, id_9, id_10, id_11, id_12, id_13, id_14, id_15, id_16, id_17;
    SharedPreferences prefs;

    String lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_tozihat);


        id_1 = (TextView) findViewById(R.id.id_1);

        id_2 = (TextView) findViewById(R.id.id_2);


        id_3 = (TextView) findViewById(R.id.id_3);

        id_4 = (TextView) findViewById(R.id.id_4);

        id_5 = (TextView) findViewById(R.id.id_5);

        id_6 = (TextView) findViewById(R.id.id_6);

        id_7 = (TextView) findViewById(R.id.id_7);

        id_8 = (TextView) findViewById(R.id.id_8);

        id_9 = (TextView) findViewById(R.id.id_9);

        id_10 = (TextView) findViewById(R.id.id_10);

        id_11 = (TextView) findViewById(R.id.id_11);

        id_12 = (TextView) findViewById(R.id.id_12);

        id_13 = (TextView) findViewById(R.id.id_13);

        id_14 = (TextView) findViewById(R.id.id_14);

        id_15 = (TextView) findViewById(R.id.id_15);

        id_16 = (TextView) findViewById(R.id.id_16);

        id_17 = (TextView) findViewById(R.id.id_17);


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
                id_1.setText("اسم الله");

                id_2.setText("شامل برامج المعجزة");

                id_3.setText("تحية تحياتي. أعزائي المستخدمين. تم إعداد برنامج آياق للتميز من قبل خبراء من فريق إعادة بناء أتابات بمقاطعة مازانداران وينطبق بالتعاون مع المقر الرئيسي للمركز في البلاد ولديه 3 أقسام مهمة");

                id_4.setText("الجزء الأول: إعادة بناء الائتمان المقدس");

                id_5.setText("1. زيادة جاذبية المساهمات الشعبية في ترميم الأضرحة المقدسة لأئمة الأئمة (ع).");

                id_6.setText("2. الشفافية على حساب الأكراد");

                id_7.setText("2. الشفافية على حساب الأكراد");

                id_8.setText("3-مشاعر كردان أعضاء في الحقوق والحقوق الخاصة بمشاركة كندندرا دير إماز بازديزي عبادة مقدسة بتفكك آشتان وشهرستان");

                id_9.setText("4 - إرسال هدية ومثل شركة مدرنزر خرين وعايدة كاندندجان نجف أشرف - كربلاء - قازمين وسامراء");

                id_10.setText("الجزء الثاني: خدمات للحجاج أربعين حسيني");

                id_11.setText("1- (صانع الاقفال) للعثور على ملاحين GPS في العراق");

                id_12.setText("2. العثور على كائنات أو أشخاص مفقودين بواسطة التطبيق");

                id_13.setText("3. الإعلان عن خدمة حجاج الأربعين الحسيني في العراق (عدد المساكن - الحمامات وأحواض الاستحمام - الخدمات الصحية)");

                id_14.setText("القسم 3: الخدمات الخاصة للمستخدمين");

                id_15.setText("1. إمكانية شراء ودفع الفواتير");

                id_16.setText("2. أداء العمل الترويجي");

                id_17.setText("3. عقد المباريات الحية");

            } else if (lan.equalsIgnoreCase("kingdom")) {

                String languageToLoad1 = "en"; // your language

                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();

                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());


                id_1.setText("Name of God");

                id_2.setText("Comprehensive app for Ascension");

                id_3.setText("Welcoming greetings Dear users. The Ayiaq Excellence Program has been prepared by experts of the Atabat Reconstruction Team of Mazandaran Province and it is applicable in cooperation with the Headquarters of the Center in the country and has 3 important sections.");

                id_4.setText("Part One: Reconstruction of the sacred credit");

                id_5.setText("1. Increasing the attraction of popular contributions to the restoration of the holy shrines of Imams' Imams (AS).");

                id_6.setText("2. Transparency at the expense of the Kurds");

                id_7.setText("2. Transparency at the expense of the Kurds");

                id_8.setText("3-Mashaed Kardan Members of the rights and rights of the participation of Kendendra Der Imaz Bazazizi holy worship by disintegration of Astan and Shahristan");

                id_9.setText("4 - Send a gift and like a company Mdnzr Khairin and Ahda Kandendgan Najaf Ashraf - Karbala - Kazemin and Samarra");

                id_10.setText("Part II: Services to pilgrims Arbaeen Hosseini");

                id_11.setText("1- (Locksmiths) to find GPS navigators in Iraq");

                id_12.setText("2. Finding objects or missing people by application");

                id_13.setText("3. Announcement of service to the pilgrims of Arbaeen Hosseini in Iraq (number of dwellings - baths and baths - health services)");

                id_14.setText("Section 3: Special Services to Users");

                id_15.setText("1. Possibility to buy and pay bills");

                id_16.setText("2. Performing promotional work");

                id_17.setText("3. Holding live matches");






            } else if (lan.equalsIgnoreCase("iran")) {

                String languageToLoad1 = "fa"; // your language

                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();

                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());


                id_1.setText("بسمه تعالی");

                id_2.setText("اپلیکیشن جامع معراج");

                id_3.setText("با عرض سلام و احترام خدمت کاربران عزیز. طرح اپلیکیشن جامع معراج توسط کارشناسان ستاد بازسازی عتبات استان مازندران تهیه شده است و با همکاری ستاد مرکز در سطح کشور قابل اجرا می باشد و دارای 3 بخش مهم می باشد");

                id_4.setText("بخش اول : بازسازی اعتاب مقدسه");

                id_5.setText("1-افزایش جذب کمک های مردمی در امر بازسازی حرم های مطهر ائمه معصومین (ع).");

                id_6.setText("2-شفاف سازی در هزینه کردها");

                id_7.setText("2-شفاف سازی در هزینه کردها");

                id_8.setText("3-مشخص کردن افراد حقیقی و حقوقی مشارکت کننده در امر بازسازی اعتاب مقدسه به تفکیک استان و شهرستان");

                id_9.setText("4-ارسال هدایا و کمکها به پروژه موردنظر خیرین و اهدا کنندگان در نجف اشرف – کربلا – کاظمین و سامرا");

                id_10.setText("بخش دوم : خدمات دهی به زائرین اربعین حسینی");

                id_11.setText("1-( موکب یاب ) جهت پیدا کردن موکب های ایرانی در عراق توسط GPS");

                id_12.setText("2-پیدا کردن اشیاء یا افراد گم شده توسط اپلیکیشن");

                id_13.setText("3-اعلام خدمات دهی موکب ها به زائران اربعین حسینی در عراق ( تعداد اسکان – سرویس بهداشتی و حمام و – خدمات درمانی )");

                id_14.setText("بخش سوم : خدمات ویژه به کاربران");

                id_15.setText("1-امکان خرید شارژ و پرداخت قبوض");

                id_16.setText("2-انجام کارهای تبلیغاتی");

                id_17.setText(" 3-برگزاری مسابقات زنده");
            }
        }


        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_tozihat.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
