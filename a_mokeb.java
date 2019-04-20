package com.zare.karbala;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Trace;
import android.support.annotation.DrawableRes;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_Answer_Spinner;
import Adapter.Adapter_Last_News;
import Adapter.GPSTracker;
import Adapter.List_Spinner_City;
import Model.Adapter_Spinner_City;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.Khabar_persian;
import Model.ListObject_Spinner_Ostan;
import Model.M_mobes;

import Model._3langMokebs;
import Model.mokebs;
import Model.paramAllMokeb;
import Model.sharedprefrencce;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_mokeb extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    Spinner spinner_Ostan;

    Dialog dialog;

    int Ostan_ID;

    String lan, id;

    SharedPreferences prefs_s;

    ArrayList<ListObject_Spinner_Ostan> objects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_mokeb);

        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();


        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(final GoogleMap google_Map) {
        googleMap = google_Map;


        //When Map Loads Successfully
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                GPSTracker mGPS = new GPSTracker(a_mokeb.this);


                if (mGPS.canGetLocation) {
                    mGPS.getLocation();
                    Toast.makeText(a_mokeb.this, ("Lat" + mGPS.getLatitude() + "Lon" + mGPS.getLongitude()), Toast.LENGTH_SHORT).show();
                    LatLng self = new LatLng(mGPS.getLatitude(), mGPS.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(self).
                            icon(BitmapDescriptorFactory.fromBitmap(
                                    createCustomMarker(a_mokeb.this, R.drawable.ic_maps, "موقعیت کاربر"))));


                } else {

                    System.out.println("Unable");
                }


                for (int j = 0; j < Array_class_get.array_mokebs.size(); j++) {


                    LatLng customMarkerLocationzero = new LatLng(Double.valueOf(Array_class_get.array_mokebs.get(j).lat), Double.valueOf(Array_class_get.array_mokebs.get(j).longt));
                    googleMap.addMarker(new MarkerOptions().position(customMarkerLocationzero) .title(Array_class_get.array_mokebs.get(j).mokebdesc + "" + Array_class_get.array_mokebs.get(j).phoneIran).
                            icon(BitmapDescriptorFactory.fromBitmap(
                                    createCustomMarker(a_mokeb.this, R.drawable.marker_icon, Array_class_get.array_mokebs.get(j).mokebName))));
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();

                    builder.include(customMarkerLocationzero); //Taking Point B (Second LatLng)
                    LatLngBounds bounds = builder.build();

                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                    googleMap.moveCamera(cu);
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 100, null);


                }

//                LatLng customMarkerLocationzero = new LatLng(36.210994, 53.165858);
//                LatLng customMarkerLocationone = new LatLng(36.170994, 53.085858);
//                LatLng customMarkerLocationTwo = new LatLng(36.270994, 53.185858);
//                LatLng customMarkerLocationThree = new LatLng(36.223227, 53.098006);
//                LatLng customMarkerLocationfour = new LatLng(37.223227, 54.098006);
//
////                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationOne).
////                        icon(BitmapDescriptorFactory.fromBitmap(
//////                                createCustomMarker(a_mokeb.this, R.drawable.pointer, "09118947291"))));
////                googleMap.addMarker(new MarkerOptions().position(customMarker_feli).
////                        icon(BitmapDescriptorFactory.fromBitmap(
////                                createCustomMarker(a_mokeb.this, R.drawable.marker_icon, "09118947291"))));
//
//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationzero).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_mokeb.this, R.drawable.marker_icon, "09118947291"))));
//
//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationfour).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_mokeb.this, R.drawable.marker_icon, "09118947291"))));
//
//
//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationone).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_mokeb.this, R.drawable.marker_icon, "09118947291"))));
//
//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationTwo).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_mokeb.this, R.drawable.marker_icon, "09118947291"))));
//
//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationThree).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_mokeb.this, R.drawable.marker_icon, "09118947291"))));
//
//                //LatLngBound will cover all your marker on Google Maps
//                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                //  builder.include(customMarkerLocationOne); //Taking Point A (First LatLng)
//                builder.include(customMarkerLocationThree); //Taking Point B (Second LatLng)
//                LatLngBounds bounds = builder.build();
//
//                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
//                googleMap.moveCamera(cu);
//                googleMap.animateCamera(CameraUpdateFactory.zoomTo(8), 100, null);
            }
        });


    }

    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageResource(resource);
        TextView txt_name = (TextView) marker.findViewById(R.id.name);
        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_mokeb.this, a_choosen_mokeb.class));
        finish();
    }
}
