package com.zare.karbala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Adapter.CalendarTool;
import Adapter.GPSTracker;
import Model.ConfigApi;
import Model.sharedprefrencce;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_settings extends AppCompatActivity {

    TextView change, text2, time2;

    SharedPreferences prefs;

    String lan;

    double latitude, longitude;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_settings);


        Calendar c = Calendar.getInstance();

        int yeard = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        CalendarTool ct = new CalendarTool(yeard, month, day);

        String Day_Month_Year = ct.getIranianDate();


        change = (TextView) findViewById(R.id.change);

        time2 = (TextView) findViewById(R.id.time2);

        exit = (Button) findViewById(R.id.exit);

        text2 = (TextView) findViewById(R.id.text2);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");

        if ((MyToken.isEmpty())) {

        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");


            if (lan.equalsIgnoreCase("arab")) {

                change.setText("تغيير اللغة");
                exit.setText("الخروج من البرنامج");
            } else if (lan.equalsIgnoreCase("kingdom")) {

                change.setText("Change language");
                exit.setText("Exit");

            } else if (lan.equalsIgnoreCase("iran")) {

                change.setText("تغییر زبان");
                exit.setText("خروج از نرم افزار");

            }
        }
        GPSTracker mGPS = new GPSTracker(a_settings.this);


        if (mGPS.canGetLocation) {
            mGPS.getLocation();

            latitude = mGPS.getLatitude();
            longitude = mGPS.getLongitude();
        } else {

            System.out.println("Unable");
        }

        if (latitude != 0.0)
        {
            Geocoder geoCoder = new Geocoder(this, Locale.getDefault()); //it is Geocoder
            StringBuilder builder = new StringBuilder();
            try {
                List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
                int maxLines = address.get(0).getMaxAddressLineIndex();
                for (int i = 0; i < maxLines; i++) {
                    String addressStr = address.get(0).getAddressLine(i);
                    builder.append(addressStr);
                    builder.append(" ");
                }

                String fnialAddress = builder.toString(); //This is the complete address.
                text2.setText(fnialAddress);

            } catch (IOException e) {
            } catch (NullPointerException e) {
            }

        }


        time2.setText(Day_Month_Year);
    }

    public void exite(View view) {
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        prefs.edit().remove(ConfigApi.MyToken).commit();
        prefs.edit().remove(ConfigApi.MyMasol).commit();
        prefs.edit().remove(ConfigApi.MyUser).commit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_settings.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
