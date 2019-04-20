package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class a_webview extends AppCompatActivity {

    WebView webview;
    ProgressDialog Pdialog;
    public String zriat, DateToStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_webview);

        zriat = getIntent().getStringExtra("zriat").toString().trim();

        webview = (WebView) findViewById(R.id.webview);

        webview.setWebViewClient(new WebViewClient());

        webview
                .getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(zriat);
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);

            Pdialog = new ProgressDialog(a_webview.this);
            Pdialog.setTitle("LOADING");
            Pdialog.setMessage("");
            Pdialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub

            super.onPageFinished(view, url);
            Pdialog.dismiss();

        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(a_webview.this, a_ziarate_majazi.class));
        finish();
    }
}
