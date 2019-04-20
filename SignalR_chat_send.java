package com.zare.karbala;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import Model.GetConnectParams;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;



public class SignalR_chat_send  extends Service {

    private microsoft.aspnet.signalr.client.hubs.HubConnection mHubConnection;
    private microsoft.aspnet.signalr.client.hubs.HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients

    public static final String BROADCAST_ACTION = "com.android.com.simplechatwithsignalr";

    public SignalR_chat_send() {
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
        public SignalR_chat_send getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalR_chat_send.this;
        }
    }

    /**
     * method for clients (activities)
     */
    public void sendMessage(String message) {
        String SERVER_METHOD_SEND = "Send";
        mHubProxy.invoke(SERVER_METHOD_SEND, message);
    }

    public void getconnectionid(String connectionid) {
        String SERVER_METHOD_SEND = "hubconnect";
        GetConnectParams obj = new GetConnectParams();
        obj.connectionid = connectionid;
        obj.userid = "1";
        mHubProxy.invoke(SERVER_METHOD_SEND, obj);

    }

    private void startSignalR() {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        String conid = "";
        String serverUrl = "http://88.198.102.229:4545/";
        mHubConnection = new microsoft.aspnet.signalr.client.hubs.HubConnection(serverUrl);
        String SERVER_HUB_CHAT = "SignalRChatHub";
        mHubProxy = mHubConnection.createHubProxy(SERVER_HUB_CHAT);
        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
        SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);

        try {
            signalRFuture.get();

            conid = mHubConnection.getConnectionId();
            Log.e("SimpleSignalR", conid);
            getconnectionid(conid);
        } catch (InterruptedException | ExecutionException e) {
            Log.e("SimpleSignalR", e.toString());
            return;
        }


        mHubProxy.on("SendChatMessage", new SubscriptionHandler1<String>() {
            @Override
            public void run(String msg) {
                Intent intent = new Intent(BROADCAST_ACTION);
                intent.putExtra("message", msg);
                sendBroadcast(intent);
            }
        }, String.class);
    }
}
