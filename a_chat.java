package com.zare.karbala;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Adapter.Adapter_chat_list;
import Adapter.CalendarTool;
import Adapter.adp_chat;
import Model.ApiService;
import Model.Array_class_get;
import Model.ChatSeenInput;
import Model.ConfigApi;
import Model.chatDetails;
import Model.inpck;
import Model.m_chat;
import Model.sharedprefrencce;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class a_chat extends AppCompatActivity {

    private final Context mContext = this;

    private SignalRService mService;

    private boolean mBound = false;

    public TextView txtMessage;

    private Uri realUri;

    private String message = "";

    String conid1, conid2;

    RecyclerView chat;

    GridLayoutManager mLayoutManager;

    adp_chat adap;

    String MyUser;

    ImageView pick_gallery;

    BottomSheetDialog d;

    Dialog dialog;

    String recordedVideoPath;

    SharedPreferences prefs_Lan;

    private static final String VIDEO_DIRECTORY = "/demonuts";

    private int GALLERY = 3, CAMERA = 4;

    String im11, lan, MyLan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_chat);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {


            } else {
                conid1 = getIntent().getStringExtra("conid1").toString();
                conid2 = getIntent().getStringExtra("conid2").toString();

            }
        } else {
            // StateName = extras.getString("StateName");
        }
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

        registerReceiver(broadcastReceiver, new IntentFilter(SignalRService.BROADCAST_CHAT));


        chat = (RecyclerView) findViewById(R.id.chat);

        chat.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(a_chat.this, 1);

        chat.setLayoutManager(mLayoutManager);

        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

        MyUser = prefs.getString(ConfigApi.MyUser, "");


        if (Array_class_get.m_chatDetails.size() > 0) {
            for (int m = 0; m < Array_class_get.m_chatDetails.size(); m++) {


                m_chat pm = new m_chat();

                if (Array_class_get.m_chatDetails.get(m).userFrom.equalsIgnoreCase(MyUser)) {
                    pm.s_chat = "send_chat_zare" + Array_class_get.m_chatDetails.get(m).Message;
                    pm.date = Array_class_get.m_chatDetails.get(m).dateTime;
                    pm.msg_type = Array_class_get.m_chatDetails.get(m).LastMessageType;
                } else {
                    pm.s_chat = Array_class_get.m_chatDetails.get(m).Message;
                    pm.date = Array_class_get.m_chatDetails.get(m).dateTime;
                    pm.msg_type = Array_class_get.m_chatDetails.get(m).LastMessageType;

                }

                Array_class_get.m_chat.add(pm);
            }


            adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");

            chat.setAdapter(adap);

            adap.notifyDataSetChanged();

            chat.scrollToPosition(Array_class_get.m_chat.size() - 1);
        }

        dialog = new Dialog(this);

        dialog.setContentView(R.layout.camera_gallery);

        final Button camera = (Button) dialog.findViewById(R.id.camera);

        final Button gallery = (Button) dialog.findViewById(R.id.gallery);


        d = new BottomSheetDialog(this);

        d.setContentView(R.layout.dialog_image_video);

        d.setCancelable(true);

        Button video = (Button) d.findViewById(R.id.video);

        Button image_gallry = (Button) d.findViewById(R.id.image_gallry);

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                d.dismiss();

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(intent, 4);
                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(galleryIntent, 3);
                    }
                });
            }
        });
        image_gallry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                d.dismiss();

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);
                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, 1);
                    }
                });
            }
        });


        final Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs = mContext.getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);

                final String MyToken = prefs.getString(ConfigApi.MyToken, "");


                inpck inp = new inpck();

                inp.token = MyToken;
                inp.userID = MyUser;


                final OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setReadTimeout(900, TimeUnit.SECONDS);
                okHttpClient.setConnectTimeout(900, TimeUnit.SECONDS);

//                RequestInterceptor requestInterceptor = new RequestInterceptor() {
//                    @Override
//                    public void intercept(RequestFacade request) {
//                        request.addHeader("IdFrom", MyUser);
//                        request.addHeader("IdTo", conid1);
//                        request.addHeader("Content-encoding", "gzip");
//                        request.addHeader("Accept", "application/json");
//                    }
//                };
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("http://api.mazandatabat.org:3001")
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();

                ApiService service = restAdapter.create(ApiService.class);


                service.checkLastFile(inp, new Callback<List<chatDetails>>() {
                    @Override
                    public void success(List<chatDetails> s, Response response) {
                        int m = 0;

                        if (s == null) {

//                            SharedPreferences prefs_Lan = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);
//
//
//                            MyLan = prefs_Lan.getString(sharedprefrencce.MyToken, "");
//
//                            if ((MyLan.isEmpty())) {
//
//                                Toast.makeText(a_chat.this, "مشکلی در زمان ارسال رخ داده است مجدد تلاش نمایید.", Toast.LENGTH_SHORT).show();
//
//
//                            } else {
//
//
//                                lan = prefs_Lan.getString(sharedprefrencce.MyToken, "");
//
//                                if (lan.equalsIgnoreCase("arab")) {
//                                    String languageToLoad1 = "ar"; // your language
//
//                                    Locale locale1 = new Locale(languageToLoad1);
//                                    Locale.setDefault(locale1);
//                                    Configuration config1 = new Configuration();
//
//                                    config1.locale = locale1;
//                                    getBaseContext().getResources().updateConfiguration(config1,
//                                            getBaseContext().getResources().getDisplayMetrics());
//
//                                    Toast.makeText(a_chat.this, "حاول مرة أخري", Toast.LENGTH_SHORT).show();
//
//
//                                } else if (lan.equalsIgnoreCase("kingdom")) {
//
//                                    String languageToLoad1 = "en"; // your language
//
//                                    Locale locale1 = new Locale(languageToLoad1);
//                                    Locale.setDefault(locale1);
//                                    Configuration config1 = new Configuration();
//
//                                    config1.locale = locale1;
//                                    getBaseContext().getResources().updateConfiguration(config1,
//                                            getBaseContext().getResources().getDisplayMetrics());
//
//                                    Toast.makeText(a_chat.this, "try again", Toast.LENGTH_SHORT).show();
//
//
//                                } else if (lan.equalsIgnoreCase("iran")) {
//
//                                    String languageToLoad1 = "fa"; // your language
//
//                                    Locale locale1 = new Locale(languageToLoad1);
//                                    Locale.setDefault(locale1);
//                                    Configuration config1 = new Configuration();
//
//                                    config1.locale = locale1;
//                                    getBaseContext().getResources().updateConfiguration(config1,
//                                            getBaseContext().getResources().getDisplayMetrics());
//
//                                    Toast.makeText(a_chat.this, "مشکلی در زمان ارسال رخ داده است مجدد تلاش نمایید.", Toast.LENGTH_SHORT).show();
//
//
//                                }
//                            }


                        } else {

                            Calendar c = Calendar.getInstance();

                            int yeard = c.get(Calendar.YEAR);

                            int month = c.get(Calendar.MONTH) + 1;


                            int day = c.get(Calendar.DAY_OF_MONTH);

                            CalendarTool ct = new CalendarTool(yeard, month, day);

                            String Day_Month_Year = ct.getIranianDate();

                            Date date = new Date();

                            String strDateFormat = "hh:mm:ss a";

                            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

                            String formattedDate = dateFormat.format(date);

                            m_chat pm = new m_chat();

                            for (int i = 0; i < s.size(); i++) {
                                pm.s_chat = s.get(i).Message;

                                pm.date = s.get(i).dateTime;

                                pm.msg_type = s.get(i).MessageType;

                                Array_class_get.m_chat.add(pm);
                            }

                             adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");

                            chat.setAdapter(adap);

                            adap.notifyDataSetChanged();

                            chat.scrollToPosition(Array_class_get.m_chat.size() - 1);

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        int m = 0;
                    }
                });


                handler.postDelayed(this, 120000);

            }
        }, 0);


    }

    public void pick_gallery(View view) {
        d.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        dialog.dismiss();
        switch (requestCode) {

            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

                    try {

                        java.io.ByteArrayOutputStream baos1;

                        baos1 = new java.io.ByteArrayOutputStream();

                        final InputStream imageStream = getContentResolver().openInputStream(selectedImage);

                        final Bitmap selected = BitmapFactory.decodeStream(imageStream);

                        selected.compress(Bitmap.CompressFormat.JPEG, 100, baos1);

                        byte[] img11 = baos1.toByteArray();

                        im11 = Base64.encodeToString(img11, Base64.DEFAULT);

                        if (mBound) {
                            Calendar c = Calendar.getInstance();

                            int yeard = c.get(Calendar.YEAR);
                            int month = c.get(Calendar.MONTH) + 1;
                            int day = c.get(Calendar.DAY_OF_MONTH);

                            CalendarTool ct = new CalendarTool(yeard, month, day);

                            String Day_Month_Year = ct.getIranianDate();

                            Date date = new Date();

                            String strDateFormat = "hh:mm:ss a";

                            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

                            String formattedDate = dateFormat.format(date);

                            m_chat pm = new m_chat();

                            pm.s_chat = "send_chat_zare" + im11;

                            pm.date = formattedDate;

                            pm.msg_type = "image";

                            Array_class_get.m_chat.add(pm);

                            adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");

                            chat.setAdapter(adap);

                            adap.notifyDataSetChanged();

                            chat.scrollToPosition(Array_class_get.m_chat.size() - 1);


                            mService.sendMessage(im11, conid1, "image-png");

                            baos1.reset();

                        }

                        //  profile_image.setImageBitmap(selected);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // imageview.setImageURI(selectedImage);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {

                    Uri contentURI = imageReturnedIntent.getData();

                    recordedVideoPath = getPath(contentURI);


                    realUri = Uri.parse(getPath(imageReturnedIntent.getData()));

                    Calendar c = Calendar.getInstance();

                    int yeard = c.get(Calendar.YEAR);

                    int month = c.get(Calendar.MONTH) + 1;

                    int day = c.get(Calendar.DAY_OF_MONTH);

                    CalendarTool ct = new CalendarTool(yeard, month, day);

                    String Day_Month_Year = ct.getIranianDate();

                    Date date = new Date();

                    String strDateFormat = "hh:mm:ss a";

                    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

                    String formattedDate = dateFormat.format(date);

                    m_chat pm = new m_chat();

                    pm.s_chat = "send_chat_zare" + realUri;

                    pm.date = formattedDate;

                    pm.msg_type = "image";

                    Array_class_get.m_chat.add(pm);

                    adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");

                    chat.setAdapter(adap);

                    adap.notifyDataSetChanged();

                    sssaa("image");



//                    try {
//                        java.io.ByteArrayOutputStream baos1;
//
//                        baos1 = new java.io.ByteArrayOutputStream();
//
//                        final InputStream imageStream = getContentResolver().openInputStream(selectedImage);
//
//                        final Bitmap selected = BitmapFactory.decodeStream(imageStream);
//
//                        selected.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
//
//                        byte[] img11 = baos1.toByteArray();
//
//                        im11 = Base64.encodeToString(img11, Base64.DEFAULT);
//
//                        if (mBound) {
//                            Calendar c = Calendar.getInstance();
//
//                            int yeard = c.get(Calendar.YEAR);
//                            int month = c.get(Calendar.MONTH) + 1;
//                            int day = c.get(Calendar.DAY_OF_MONTH);
//
//                            CalendarTool ct = new CalendarTool(yeard, month, day);
//
//                            String Day_Month_Year = ct.getIranianDate();
//
//                            Date date = new Date();
//
//                            String strDateFormat = "hh:mm:ss a";
//
//                            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
//
//                            String formattedDate = dateFormat.format(date);
//
//                            m_chat pm = new m_chat();
//
//                            pm.s_chat = "send_chat_zare" + im11;
//
//                            pm.date = formattedDate;
//
//                            pm.msg_type = "image";
//
//                            Array_class_get.m_chat.add(pm);
//
//                            adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");
//
//                            chat.setAdapter(adap);
//
//                            adap.notifyDataSetChanged();
//
//                            chat.scrollToPosition(Array_class_get.m_chat.size() - 1);
//
//
//                            mService.sendMessage(im11, conid1, "image-png");
//
//                            baos1.reset();

                      //  }

                        //  profile_image.setImageBitmap(selected);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    //  imageview.setImageURI(selectedImage);
                }
                break;

            case 3:

                Uri contentURI = imageReturnedIntent.getData();
                recordedVideoPath = getPath(contentURI);


                realUri = Uri.parse(getPath(imageReturnedIntent.getData()));
                Calendar c = Calendar.getInstance();

                int yeard = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);

                CalendarTool ct = new CalendarTool(yeard, month, day);

                String Day_Month_Year = ct.getIranianDate();

                Date date = new Date();

                String strDateFormat = "hh:mm:ss a";

                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

                String formattedDate = dateFormat.format(date);

                m_chat pm = new m_chat();

                pm.s_chat = "send_chat_zare" + realUri;

                pm.date = formattedDate;

                pm.msg_type = "video";

                Array_class_get.m_chat.add(pm);

                adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");

                chat.setAdapter(adap);

                adap.notifyDataSetChanged();

                sssaa("video");


                break;
            case 4:
//                Uri contentURI = imageReturnedIntent.getData();
//                String recordedVideoPath = getPath(contentURI);


//                Log.d("frrr",recordedVideoPath);
//                saveVideoToInternalStorage(recordedVideoPath);
//                videoView.setVideoURI(contentURI);
//                videoView.requestFocus();
//                videoView.start();
                break;
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");

            chat.setAdapter(adap);

            chat.scrollToPosition(Array_class_get.m_chat.size() - 1);

            adap.notifyDataSetChanged();


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


    @Override
    protected void onStart() {

        if (!mBound)
        {
            Intent intent = new Intent();
            intent.setClass(mContext, SignalRService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
        super.onStart();
    }

    @Override
    protected void onResume() {

        if(!mBound)
        {
            Intent intent = new Intent();
            intent.setClass(mContext, SignalRService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        if(!mBound)
        {
            Intent intent = new Intent();
            intent.setClass(mContext, SignalRService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }

        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setClass(mContext, SignalRService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        super.onDestroy();
    }

    public void sssaa(String type) {


        chat.scrollToPosition(Array_class_get.m_chat.size() - 1);

        final ProgressDialog mDialog = new ProgressDialog(a_chat.this);

        mDialog.setMessage("");

        mDialog.setCancelable(false);

        mDialog.show();

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(900, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(900, TimeUnit.SECONDS);

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("IdFrom", MyUser);
                request.addHeader("IdTo", conid1);
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

        TypedFile typedFile = new TypedFile(type, new File(recordedVideoPath));

        service.upload(typedFile, new Callback<chatDetails>() {
            @Override
            public void success(chatDetails s, Response response) {
                int m = 0;
                mDialog.dismiss();
                if (s.id == 0) {

                    prefs_Lan = getSharedPreferences(sharedprefrencce.My_PREFERENCE, Context.MODE_PRIVATE);

                    MyLan = prefs_Lan.getString(sharedprefrencce.MyToken, "");

                    if ((MyLan.isEmpty())) {

                        Toast.makeText(a_chat.this, "مشکلی در زمان ارسال رخ داده است مجدد تلاش نمایید.", Toast.LENGTH_SHORT).show();


                    } else {


                        lan = prefs_Lan.getString(sharedprefrencce.MyToken, "");

                        if (lan.equalsIgnoreCase("arab")) {

                            String languageToLoad1 = "ar"; // your language

                            Locale locale1 = new Locale(languageToLoad1);

                            Locale.setDefault(locale1);

                            Configuration config1 = new Configuration();

                            config1.locale = locale1;

                            getBaseContext().getResources().updateConfiguration(config1,
                                    getBaseContext().getResources().getDisplayMetrics());

                            Toast.makeText(a_chat.this, "حاول مرة أخري", Toast.LENGTH_SHORT).show();


                        } else if (lan.equalsIgnoreCase("kingdom")) {

                            String languageToLoad1 = "en"; // your language

                            Locale locale1 = new Locale(languageToLoad1);

                            Locale.setDefault(locale1);

                            Configuration config1 = new Configuration();

                            config1.locale = locale1;

                            getBaseContext().getResources().updateConfiguration(config1,
                                    getBaseContext().getResources().getDisplayMetrics());


                            Toast.makeText(a_chat.this, "try again", Toast.LENGTH_SHORT).show();


                        } else if (lan.equalsIgnoreCase("iran")) {

                            String languageToLoad1 = "fa"; // your language

                            Locale locale1 = new Locale(languageToLoad1);
                            Locale.setDefault(locale1);
                            Configuration config1 = new Configuration();

                            config1.locale = locale1;
                            getBaseContext().getResources().updateConfiguration(config1,
                                    getBaseContext().getResources().getDisplayMetrics());

                            Toast.makeText(a_chat.this, "مشکلی در زمان ارسال رخ داده است مجدد تلاش نمایید.", Toast.LENGTH_SHORT).show();


                        }
                    }


                } else {




//                    Calendar c = Calendar.getInstance();
//
//                    int yeard = c.get(Calendar.YEAR);
//
//                    int month = c.get(Calendar.MONTH) + 1;
//
//
//                    int day = c.get(Calendar.DAY_OF_MONTH);
//
//                    CalendarTool ct = new CalendarTool(yeard, month, day);
//
//                    String Day_Month_Year = ct.getIranianDate();
//
//                    Date date = new Date();
//
//                    String strDateFormat = "hh:mm:ss a";
//
//                    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
//
//                    String formattedDate = dateFormat.format(date);
//
//                    m_chat pm = new m_chat();
//
//                    pm.s_chat = s.Message;
//
//                    pm.date = formattedDate;
//
//                    pm.msg_type = "video";
//
//                    Array_class_get.m_chat.add(pm);
//
//                    adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");
//
//                    chat.setAdapter(adap);
//
//                    adap.notifyDataSetChanged();
//
//                    chat.scrollToPosition(Array_class_get.m_chat.size() - 1);

                }
            }

            @Override
            public void failure(RetrofitError error) {
                int m = 0;
                mDialog.dismiss();
            }
        });


    }

    public void send(View view) {
        if (mBound) {
            // Call a method from the SignalRService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            EditText editText = (EditText) findViewById(R.id.txt_chat);
            if (editText != null && editText.getText().length() > 0) {
                String message = editText.getText().toString();

                Calendar c = Calendar.getInstance();

                int yeard = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;

                int day = c.get(Calendar.DAY_OF_MONTH);

                CalendarTool ct = new CalendarTool(yeard, month, day);

                String Day_Month_Year = ct.getIranianDate();

                Date date = new Date();

                String strDateFormat = "hh:mm:ss a";

                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

                String formattedDate = dateFormat.format(date);

                m_chat pm = new m_chat();

                pm.s_chat = "send_chat_zare" + message;

                pm.date = formattedDate;

                pm.msg_type = "text";

                Array_class_get.m_chat.add(pm);

                adap = new adp_chat(a_chat.this, Array_class_get.m_chat, "1");

                chat.setAdapter(adap);

                adap.notifyDataSetChanged();

                chat.scrollToPosition(Array_class_get.m_chat.size() - 1);

                editText.setText("");

                mService.sendMessage(message, conid1, "text");

            }

        }
    }

    private MultipartTypedOutput attachFiles() {
        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();

        // this will add data to body to send via retrofit.
//        multipartTypedOutput.addPart("email",new TypedString("pawneshwergupta@gmail.com"));
//        multipartTypedOutput.addPart("website", new TypedString("https://www.learnpainless.com"));

        // this will make Retrofit file from gallery image
        multipartTypedOutput.addPart("vidoe", makeFile(realUri.toString()));
        return multipartTypedOutput;
    }

    private TypedFile makeFile(String uri) {
        // this will make file which is required by Retrofit.
        File file = new File(uri);
        TypedFile typedFile = new TypedFile("vidoe/*", file);
        return typedFile;
    }

    @Override
    public void onBackPressed() {
        Array_class_get.m_chat.clear();
        startActivity(new Intent(a_chat.this, chat_msg.class));
        finish();
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
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
}