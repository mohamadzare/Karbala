package com.zare.karbala;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

//import br.com.goncalves.pugnotification.notification.PugNotification;
import Adapter.CalendarTool;
import Model.Array_class_get;
import Model.ConfigApi;
import Model.GetConnectParams;
import Model.MasolInfo;
import Model.Message;
import Model.PreferenceHelper;
import Model.SetConnection;
import Model.m_UserInfo2;
import Model.m_chat;
import Model.m_message;
import Model.privateMessageParams;
import Model.recParams;
import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.Request;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

public class SignalRService extends Service {

    private microsoft.aspnet.signalr.client.hubs.HubConnection mHubConnection;
    private microsoft.aspnet.signalr.client.hubs.HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients

    public static final String BROADCAST_ACTION = "com.android.com.simplechatwithsignalr";
    public static final String BROADCAST_CHAT = "com.android.com.a_chat";

    String conid = "";

    public void getconnectionid(String connectionid) {
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyUser = prefs.getString(ConfigApi.MyUser, "");

        String SERVER_METHOD_SEND = "hubconnect";
        GetConnectParams obj = new GetConnectParams();
        obj.connectionid = connectionid;
        obj.userid = MyUser; //in bayad taghr kon
        mHubProxy.invoke(SERVER_METHOD_SEND, obj);

    }

    public SignalRService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        startSignalR();
        return result;
    }

    @Override
    public void onDestroy() {
        mHubConnection.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startSignalR();
        return mBinder;
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public SignalRService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRService.this;
        }
    }

    /**
     * method for clients (activities)
     */
    public void sendMessage(String message, String conid1, String status) {
        privateMessageParams params = new privateMessageParams();
        SharedPreferences prefs = getSharedPreferences(ConfigApi.My_PREFERENCE, Context.MODE_PRIVATE);
        final String MyUser = prefs.getString(ConfigApi.MyUser, "");

        params.msg = message;
        params.FromUserID = MyUser;
        params.ToUserID = conid1;
        params.messageType = status;
        String SERVER_METHOD_SEND = "privatemessage";
        mHubProxy.invoke(SERVER_METHOD_SEND, params);
    }

    private void startSignalR() {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());

        String serverUrl = "http://chat.mazandatabat.org:3000/";
        mHubConnection = new microsoft.aspnet.signalr.client.hubs.HubConnection(serverUrl);
        String SERVER_HUB_CHAT = "SignalRChatHub";
        mHubProxy = mHubConnection.createHubProxy(SERVER_HUB_CHAT);
        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
        SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);

        try {
            signalRFuture.get();

            conid = mHubConnection.getConnectionId();
            Log.e("SimpleSignalR", conid);
        } catch (InterruptedException | ExecutionException e) {
            Log.e("SimpleSignalR", e.toString());
            return;
        }


        mHubProxy.on("receiveMessage", new SubscriptionHandler1<recParams>() {
            @Override
            public void run(recParams msg) {
                int m = 0;

                Array_class_get.m_UserInfo2.clear();


                if (msg.status.equalsIgnoreCase("ListUsers")) {

                    for (int z = 0; z < msg.userLists.size(); z++) {
                        m_UserInfo2 info2 = new m_UserInfo2();

                         info2.Id = msg.userLists.get(z).Id;

                        info2.connectionID = msg.userLists.get(z).connectionID;

                        info2.nameFamily = msg.userLists.get(z).nameFamily;

                        info2.profilePic = msg.userLists.get(z).profilePic;

                        info2.IsOnline = msg.userLists.get(z).IsOnline;

                        info2.city = msg.userLists.get(z).city;

                        info2.titlePost = msg.userLists.get(z).titlePost;

                        info2.UnReadMessage = msg.userLists.get(z).UnReadMessage;


                        Array_class_get.m_UserInfo2.add(info2);

                    }
                    Intent intent = new Intent(BROADCAST_ACTION);
                    sendBroadcast(intent);


                } else if (msg.status.equalsIgnoreCase("chatMessage")) {
                    m_chat pm = new m_chat();
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

                    pm.s_chat = msg.param1;

                    pm.date = formattedDate;

                    pm.msg_type = msg.MessageType;

                    Array_class_get.m_chat.add(pm);

                    Intent intent = new Intent(BROADCAST_CHAT);
                    sendBroadcast(intent);

                } else if (msg.status.equalsIgnoreCase("OnConnect")) {

                    getconnectionid(conid);

                }


            }
        }, recParams.class);


    }
}