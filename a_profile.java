package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Model.ApiService;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.SubmiVol;
import Model.TickHistoryParam;
import Model.changeRegisterParam;
import Model.sharedprefrencce;
import Model.stateReg;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_profile extends AppCompatActivity {

    TextView add_txt, txtostan, txt_jensiat;

    EditText Phone, username, name, addrs, jensiat, city, old_pass, new_pass, testss, naemss;

    String state, citys;

    ImageView profile_image;

    java.io.ByteArrayOutputStream baos2, baos1;

    SharedPreferences prefs_s, prefs;

    String lan;

    String im11;

    Button update;
    private final int REQUEST_CODE_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_profile);

        String languageToLoad = "fa"; // your language

        Locale locale = new Locale(languageToLoad);

        Locale.setDefault(locale);

        Configuration config = new Configuration();

        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        naemss = (EditText) findViewById(R.id.naemss);

        old_pass = (EditText) findViewById(R.id.old_pass);

        city = (EditText) findViewById(R.id.city);

        addrs = (EditText) findViewById(R.id.addrs);

        // txt_jensiat = (TextView)findViewById(R.id.txt_jensiat);

        txtostan
                = (TextView) findViewById(R.id.txtostan);

        add_txt = (TextView) findViewById(R.id.add_txt);

        jensiat = (EditText) findViewById(R.id.jensiat);

        new_pass = (EditText) findViewById(R.id.new_pass);

        baos1 = new java.io.ByteArrayOutputStream();

        testss = (EditText) findViewById(R.id.testss);

        update = (Button) findViewById(R.id.update);

        register();


        prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
        String MyToken = prefs.getString(sharedprefrencce.MyToken, "");
        if ((MyToken.isEmpty())) {


        } else {

            SharedPreferences prefs = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

            lan = prefs.getString(sharedprefrencce.MyToken, "");

            if (lan.equalsIgnoreCase("arab")) {

                update.setText("تحديث");
                new_pass.setHint("كلمة السر الجديدة");
                add_txt.setText("عنوان");
                txtostan.setText("المدينة");
                //  txt_jensiat.setText("جنس");
            } else if (lan.equalsIgnoreCase("kingdom")) {
                update.setText("update");
                new_pass.setHint("new password");
                add_txt.setText("Address");
                txtostan.setText("City");
                // txt_jensiat.setText("Sex");
            } else if (lan.equalsIgnoreCase("iran")) {
                //  txt_jensiat.setText("جنسیت");
                txtostan.setText("استان");
                add_txt.setText("آدرس");
                new_pass.setHint("پسورد جدید");
                update.setText("ویرایش");
            }

        }


        profile_image = (ImageView) findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, REQUEST_CODE_FROM_GALLERY);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FROM_GALLERY:


                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos1);

                        byte[] img11 = baos1.toByteArray();

                        im11 = Base64.encodeToString(img11, Base64.DEFAULT);

                        profile_image.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    Bitmap photo1 = (Bitmap) data.getExtras().get("data");
//
//
//
//                    profile_image.setImageBitmap(photo1);


                    break;

            }
        }
    }

    public void register() {

        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        changeRegisterParam rgster = new changeRegisterParam();


        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            rgster.IsMasol = true;
        } else {
            rgster.IsMasol = false;
        }

        rgster.token = MyToken;


        final ProgressDialog mDialog = new ProgressDialog(a_profile.this);
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
        service.changeRegister(rgster, new Callback<changeRegisterParam>() {
            @Override
            public void success(changeRegisterParam object, Response response) {

                mDialog.dismiss();

                if (object != null) {

                    if (object.token != null) {


                        boolean status = object.IsFounded;
                        if (object.sex.equalsIgnoreCase("male")) {
                            jensiat.setHint("مرد");
                        } else if (object.sex.equalsIgnoreCase("female")) {
                            jensiat.setHint("زن");
                        } else {
                            jensiat.setHint("");
                        }

                        for (int z = 0; z < Array_class_get.array_m_Shahr.size(); z++) {
                            if (object.city.equalsIgnoreCase(String.valueOf(Array_class_get.array_m_Shahr.get(z).id))) {

                                citys = String.valueOf(Array_class_get.array_m_Shahr.get(z).name);
                            }
                        }
                        for (int v = 0; v < Array_class_get.array_m_Ostan.size(); v++) {
                            if (object.state.equalsIgnoreCase(String.valueOf(Array_class_get.array_m_Ostan.get(v).id))) {
                                state = (String.valueOf(Array_class_get.array_m_Ostan.get(v).Name));
                            }
                        }

//                        citys = String.valueOf(object.city);
//
//
//                        state = (String.valueOf(object.address));

                        addrs.setHint(object.address);

                        city.setHint(object.address + "-" + object.city);

                        testss.setHint(object.userName);

                        naemss.setHint(String.valueOf(object.name + object.family));

                        old_pass.setHint(object.password);


                    } else {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_profile.this);
                        dialogBuilder
                                .withTitle("پیغام")
                                .withMessage("اطلاعاتی در دسترس نیست...")
                                .show();
                    }
                } else {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_profile.this);
                    dialogBuilder
                            .withTitle("پیغام")
                            .withMessage("اطلاعاتی در دسترس نیست...")
                            .show();
                }


            }

            @Override
            public void failure(RetrofitError error) {
                String errorMsg = "خطایی در هنگام اتصال به سرور رخ داده است.مجددا تلاش نمایید.";
                Toast.makeText(a_profile.this, errorMsg, Toast.LENGTH_SHORT).show();

                mDialog.dismiss();
            }
        });


    }

    public void change_update(View view) {

        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyToken = prefs.getString(ConfigApi.MyToken, "");

        changeRegisterParam param = new changeRegisterParam();

        final String MyMasol = prefs.getString(ConfigApi.MyMasol, "");

        if (MyMasol.equalsIgnoreCase("true")) {
            param.IsMasol = true;
        } else {
            param.IsMasol = false;
        }

        param.token = MyToken;
        param.password = new_pass.getText().toString().trim();
        param.address = addrs.getText().toString().trim();
        param.name = naemss.getText().toString().trim();
        param.picBase64 = im11;
        param.userName = testss.getText().toString().trim();


        final ProgressDialog mDialog = new ProgressDialog(a_profile.this);
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
        service.SetRegister(param, new Callback<changeRegisterParam>() {
            @Override
            public void success(changeRegisterParam object, Response response) {

                mDialog.dismiss();

                if (object != null) {

                    if (object.token != null) {


                        boolean status = object.IsFounded;
                        if (object.sex.equalsIgnoreCase("male")) {
                            jensiat.setHint("مرد");
                        } else {
                            jensiat.setHint("زن");
                        }

                        for (int z = 0; z < Array_class_get.array_m_Shahr.size(); z++) {
                            if (object.city.equalsIgnoreCase(String.valueOf(Array_class_get.array_m_Shahr.get(z).id))) {

                                citys = String.valueOf(Array_class_get.array_m_Shahr.get(z).name);
                            }
                        }
                        for (int v = 0; v < Array_class_get.array_m_Ostan.size(); v++) {
                            if (object.state.equalsIgnoreCase(String.valueOf(Array_class_get.array_m_Ostan.get(v).id))) {
                                state = (String.valueOf(Array_class_get.array_m_Ostan.get(v).Name));
                            }
                        }

                        addrs.setHint(object.city);

                        city.setHint(state);

                        username.setHint(object.userName);

                        name.setHint(object.name + object.family);

                        Phone.setText(object.address);

                        old_pass.setHint(object.password);


                    } else {
                        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_profile.this);
                        dialogBuilder
                                .withTitle("پیغام")
                                .withMessage("اطلاعاتی در دسترس نیست...")
                                .show();
                    }
                } else {
                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(a_profile.this);
                    dialogBuilder
                            .withTitle("پیغام")
                            .withMessage("اطلاعاتی در دسترس نیست...")
                            .show();
                }


            }

            @Override
            public void failure(RetrofitError error) {

                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(a_profile.this, a_Circle_list_menu.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
