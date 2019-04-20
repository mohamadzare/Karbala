package com.zare.karbala;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import Model.Array_class_get;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_moarefi_setad extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    TextView Setad_name, Address, Phone, Mobile, Descryption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_moarefi_setad);


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


        Setad_name = (TextView)findViewById(R.id.Setad_name);
        Address = (TextView)findViewById(R.id.Address);
        Phone = (TextView)findViewById(R.id.Phone);
        Mobile = (TextView)findViewById(R.id.Mobile);
        Descryption = (TextView)findViewById(R.id.Descryption);

        Setad_name.setText( Array_class_get.m_setadList.get(0).setadName);

        Address.setText( Array_class_get.m_setadList.get(0).address);

        Phone.setText( Array_class_get.m_setadList.get(0).phone1);

        Mobile.setText( Array_class_get.m_setadList.get(0).phone2);

        Descryption.setText( Array_class_get.m_setadList.get(0).descryption);
    }

    @Override
    public void onMapReady(GoogleMap google_Map) {
        googleMap = google_Map;

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {



//                    LatLng customMarkerLocationzero = new LatLng(Double.valueOf(Array_class_get.m_setadList.get(0).lat), Double.valueOf(Array_class_get.m_setadList.get(0).lang));
//                    googleMap.addMarker(new MarkerOptions().position(customMarkerLocationzero).title(Array_class_get.m_setadList.get(0).masolName + " " + Array_class_get.m_setadList.get(0).address + "  " + Array_class_get.m_setadList.get(0).biography + "  " + Array_class_get.m_setadList.get(0).phone1).
//                            icon(BitmapDescriptorFactory.fromBitmap(
//                                    createCustomMarker(a_moarefi_setad.this, R.drawable.marker_icon, Array_class_get.m_setadList.get(0).setadName))));
//                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                    //  builder.include(customMarkerLocationOne); //Taking Point A (First LatLng)
//                    builder.include(customMarkerLocationzero); //Taking Point B (Second LatLng)
//                    LatLngBounds bounds = builder.build();
//
//                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
//                    googleMap.moveCamera(cu);
//                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(5), 100, null);
//


                LatLng customMarkerLocationzero = new LatLng(Double.valueOf( Array_class_get.m_setadList.get(0).lat),Double.valueOf(Array_class_get.m_setadList.get(0).lang) );
//                LatLng customMarkerLocationone = new LatLng(36.340019,59.592919);
//                LatLng customMarkerLocationTwo = new LatLng(34.604389,50.963986);
//                LatLng customMarkerLocationThree = new LatLng(36.869184,49.514425);
//                LatLng customMarkerLocationfour = new LatLng(35.807233,51.554443);

                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationzero).title(Array_class_get.m_setadList.get(0).biography).
                        icon(BitmapDescriptorFactory.fromBitmap(
                                createCustomMarker(a_moarefi_setad.this, R.drawable.marker_icon, "مازندران"))));

//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationfour). title("پیاده روی اربعین حسینی نمایش اقتدار جبهه حق در برابر ظلم جهانی است").
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_moarefi_setad.this, R.drawable.marker_icon, "تهران"))));
//
//
//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationone).title("پیاده روی اربعین حسینی نمایش اقتدار جبهه حق در برابر ظلم جهانی است").
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_moarefi_setad.this, R.drawable.marker_icon, "مشهد"))));
//
//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationTwo).title("پیاده روی اربعین حسینی نمایش اقتدار جبهه حق در برابر ظلم جهانی است").
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_moarefi_setad.this, R.drawable.marker_icon, "قم"))));
//
//                googleMap.addMarker(new MarkerOptions().position(customMarkerLocationThree).title("پیاده روی اربعین حسینی نمایش اقتدار جبهه حق در برابر ظلم جهانی است").
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(a_moarefi_setad.this, R.drawable.marker_icon, "رشت"))));
//
                 //LatLngBound will cover all your marker on Google Maps
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                 builder.include(customMarkerLocationzero); //Taking Point A (First LatLng)
                builder.include(customMarkerLocationzero); //Taking Point B (Second LatLng)
                LatLngBounds bounds = builder.build();

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                googleMap.moveCamera(cu);
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(5), 100, null);
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
        startActivity(new Intent(a_moarefi_setad.this, a_intro_setad.class));
        finish();
    }
}
