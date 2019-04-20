package com.zare.karbala;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import DataBase.db_class;
import DataBase.field_db_city;
import DataBase.field_db_ostan;
import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.M_mobes;
import Model._3langMokebs;
import Model.m_Ostan;
import Model.m_Shahr;
import Model.paramAllMokeb;
import Model.sharedprefrencce;
import Model.versionModel;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_splash extends AppCompatActivity {

    ConnectivityManager connec;

    VideoView videoview;

    private static int SPLASH_TIME_OUT = 3000;

    db_class dbClass;

    String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_splash);

        dbClass = new db_class(a_splash.this);

        String languageToLoad = "fa"; // your language

        Locale locale = new Locale(languageToLoad);

        Locale.setDefault(locale);

        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        versionName = BuildConfig.VERSION_NAME;

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
                    SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
                    final String MyToken = prefs.getString(ConfigApi.MyToken, "");

//                    if (MyToken.isEmpty()) {
//                        new Handler().postDelayed(new Runnable() {
//
//
//                            @Override
//                            public void run() {
//
//                                startActivity(new Intent(a_splash.this, a_login.class));
//                                finish();
//
//                            }
//                        }, SPLASH_TIME_OUT);
//                    } else {
                        Get_version_app();
                   // }


                } else {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_splash.this);
                    dialogBuilder
                            .withTitle("اینترنت گوشی تان را روشن کنید...")
                            .withMessage("در غیر این صورت اجازه ورود ندارید.")
                            .show();
                }


            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//
//
//            int hasWriteContactsPermission1 = 0;
//            hasWriteContactsPermission1 = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//
//            if (hasWriteContactsPermission1 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(a_splash.this, new String[]{
//                        Manifest.permission.ACCESS_FINE_LOCATION}, 12);
//
//            }
//
//
//            int hasWriteContactsPermission3 = 0;
//            hasWriteContactsPermission3 = checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
//
//            if (hasWriteContactsPermission3 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(a_splash.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 12);
//
//            }
//
//            int hasWriteContactsPermission4 = 0;
//            hasWriteContactsPermission4 = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
//
//            if (hasWriteContactsPermission4 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(a_splash.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 12);
//
//            }
//
//
//            int hasWriteContactsPermission5 = 0;
//            hasWriteContactsPermission5 = checkSelfPermission(Manifest.permission.INTERNET);
//
//            if (hasWriteContactsPermission5 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(a_splash.this, new String[]{Manifest.permission.INTERNET}, 12);
//
//            }
//
//            int hasWriteContactsPermission6 = 0;
//            hasWriteContactsPermission6 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//            if (hasWriteContactsPermission6 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(a_splash.this, new String[]{
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12);
//
//            }
//        }


        //   ImageView imageView = (ImageView) findViewById(R.id.imageView);

//        Glide.with(this)
//                .load(R.raw.splash)
//                .into(new GlideDrawableImageViewTarget(
//                        (ImageView) findViewById(R.id.imageView)));
//

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void Get_version_app() {

        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        versionModel mokeb = new versionModel();

        mokeb.token = MyToken;

        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            mokeb.Ismasol = true;
        } else {
            mokeb.Ismasol = false;
        }


        mokeb.versionNumber = versionName;

        final ProgressDialog mDialog = new ProgressDialog(a_splash.this);

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
        service.checkVersion(mokeb, new Callback<versionModel>() {
            @Override
            public void success(final versionModel object, Response response) {


                try {

                    if (object != null) {
                        if (object.NewExist == false) {
                            Toast.makeText(a_splash.this, object.versionDescryption, Toast.LENGTH_SHORT).show();
                            load_ostan();
                        } else {

                            Toast.makeText(a_splash.this, object.versionDescryption, Toast.LENGTH_SHORT).show();

                            final Dialog dialog = new Dialog(a_splash.this);

                            dialog.setCancelable(false);

                            dialog.setContentView(R.layout.versian_app);

                            dialog.show();

                            Button btn = (Button) dialog.findViewById(R.id.btn_dialog);

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                    dialog.dismiss();
//                            String url = "http://topgil.com/FileUploadFolder/topgil.apk";
//                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.setPackage("com.android.chrome");
//                            try {
//                                startActivity(i);
//                            } catch (ActivityNotFoundException e) {
//                                // Chrome is probably not installed
//                                // Try with the default browser
//                                i.setPackage(null);
//                                startActivity(i);
//                            }

                                    get_fil(object.versionLink);
                                }
                            });

                        }
                    } else {

                    }


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

    public void load_ostan() {
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            if (MyToken.isEmpty()) {

                dbClass.delete_ostan();
                dbClass.delete_shahr();
                Array_class_get.array_m_Shahr.clear();
                Array_class_get.array_m_Ostan.clear();

                final ProgressDialog mDialog = new ProgressDialog(a_splash.this);

                mDialog.setMessage("");
                mDialog.setCancelable(false);
                mDialog.show();


                final OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setReadTimeout(900, TimeUnit.SECONDS);
                okHttpClient.setConnectTimeout(900, TimeUnit.SECONDS);

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

                service.statecity(new Callback<Object>() {
                    @Override
                    public void success(Object object, Response response) {

                        int m = 0;
                        Gson gson2 = new Gson();

                        String json = gson2.toJson(object);
                        try {

                            JSONObject jsonObj = new JSONObject(json);

                            JSONArray ostans = jsonObj.getJSONArray("ostans");

                            for (int i = 0; i < ostans.length(); i++) {

                                JSONObject resultsobject = ostans.getJSONObject(i);

                                m_Ostan m_ostan = new m_Ostan();


                                if (resultsobject.has("id")) {
                                    m_ostan.id = resultsobject.getInt("id");

                                } else {
                                    m_ostan.id = 0;

                                }
                                if (resultsobject.has("name")) {
                                    m_ostan.Name = resultsobject.getString("name");

                                } else {
                                    m_ostan.Name = "";

                                }
                                Array_class_get.array_m_Ostan.add(m_ostan);

                            }
                            field_db_city dbCity;
                            JSONArray shars = jsonObj.getJSONArray("shars");
                            for (int i = 0; i < shars.length(); i++) {

                                dbCity = new field_db_city();

                                JSONObject resultsobject = shars.getJSONObject(i);

                                m_Shahr m_shahr = new m_Shahr();

                                if (resultsobject.has("id")) {
                                    m_shahr.id = resultsobject.getInt("id");
                                 } else {
                                    m_shahr.id = 0;

                                }
                                if (resultsobject.has("name")) {
                                    m_shahr.name = resultsobject.getString("name");

                                } else {
                                    m_shahr.name = "";

                                }

                                if (resultsobject.has("idOstan")) {
                                    m_shahr.idOstan = resultsobject.getInt("idOstan");

                                } else {
                                    m_shahr.idOstan = 0;

                                }

                                Array_class_get.array_m_Shahr.add(m_shahr);
                            }
                        } catch (Exception e) {
                        }


                        mDialog.dismiss();

                        startActivity(new Intent(a_splash.this, a_login.class));
                        finish();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                        Toast.makeText(a_splash.this, errorMsg, Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();
                    }
                });



            } else {
                dbClass.delete_ostan();
                dbClass.delete_shahr();
                Array_class_get.array_m_Shahr.clear();
                Array_class_get.array_m_Ostan.clear();

                final ProgressDialog mDialog = new ProgressDialog(a_splash.this);

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

                service.statecity(new Callback<Object>() {
                    @Override
                    public void success(Object object, Response response) {

                        int m = 0;
                        Gson gson2 = new Gson();
                        field_db_ostan db_ostan;
                        String json = gson2.toJson(object);
                        try {

                            JSONObject jsonObj = new JSONObject(json);

                            JSONArray ostans = jsonObj.getJSONArray("ostans");

                            for (int i = 0; i < ostans.length(); i++) {

                                JSONObject resultsobject = ostans.getJSONObject(i);

                                m_Ostan m_ostan = new m_Ostan();


                                if (resultsobject.has("id")) {
                                    m_ostan.id = resultsobject.getInt("id");

                                } else {
                                    m_ostan.id = 0;

                                }
                                if (resultsobject.has("name")) {
                                    m_ostan.Name = resultsobject.getString("name");

                                } else {
                                    m_ostan.Name = "";

                                }
                                Array_class_get.array_m_Ostan.add(m_ostan);

                            }
                            field_db_city dbCity;
                            JSONArray shars = jsonObj.getJSONArray("shars");
                            for (int i = 0; i < shars.length(); i++) {


                                JSONObject resultsobject = shars.getJSONObject(i);

                                m_Shahr m_shahr = new m_Shahr();

                                if (resultsobject.has("id")) {
                                    m_shahr.id = resultsobject.getInt("id");

                                } else {
                                    m_shahr.id = 0;

                                }
                                if (resultsobject.has("name")) {
                                    m_shahr.name = resultsobject.getString("name");

                                } else {
                                    m_shahr.name = "";

                                }

                                if (resultsobject.has("idOstan")) {
                                    m_shahr.idOstan = resultsobject.getInt("idOstan");

                                } else {
                                    m_shahr.idOstan = 0;

                                }

                                Array_class_get.array_m_Shahr.add(m_shahr);
                            }
                        } catch (Exception e) {
                        }


                        mDialog.dismiss();

                        startActivity(new Intent(a_splash.this, a_Circle_list_menu.class));
                        finish();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                        Toast.makeText(a_splash.this, errorMsg, Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();
                    }
                });
            }


        } else if (connec.getNetworkInfo(0).getState() != NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(1).getState() != NetworkInfo.State.CONNECTED) {
            int cont = dbClass.getCount_Shahr();

            if (cont > 0) {
                startActivity(new Intent(a_splash.this, a_Circle_list_menu.class));
                finish();
            } else {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_splash.this);
                dialogBuilder
                        .withTitle("اینترنت گوشی تان را روشن کنید...")
                        .withMessage("در غیر این صورت اجازه ورود ندارید.")
                        .show();
            }

        }


    }

    public void get_fil(String url) {
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));

        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/app_meraj.apk");


        r.allowScanningByMediaScanner();


        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        r.setTitle("MERAJ");

        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // to restart the video after coming from other activity like Sing up
//        videoview.start();
//
//
//    }

//    public void vorod(View view) {
//        startActivity(new Intent(a_splash.this, a_login.class));
//        finish();
//    }
}
