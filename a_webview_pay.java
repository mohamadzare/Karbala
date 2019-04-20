package com.zare.karbala;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Locale;

public class a_webview_pay extends AppCompatActivity {

    android.webkit.WebView webview;

    ProgressDialog Pdialog;

    String money;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_webview_pay);

        progress = (ProgressBar) findViewById(R.id.progressBar);

        progress.setMax(100);

      money = getIntent().getStringExtra("money").toString().trim();
//
//        webview = (WebView) findViewById(R.id.webview);
//
//        webview.setWebViewClient(new WebViewController());
//        webview
//                .getSettings().setJavaScriptEnabled(true);
//        webview.loadUrl("https://pec.shaparak.ir/NewIPG/?Token=" + money);
//
//
//        webview.requestFocus();



        String urlString = "https://pec.shaparak.ir/NewIPG/?Token=" + money;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
             startActivity(intent);
        }
    }

    public class WebViewController extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);

            Pdialog = new ProgressDialog(a_webview_pay.this);
            Pdialog.setTitle("LOADING");
            Pdialog.setMessage("صبور باشید...");
            Pdialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.equalsIgnoreCase("http://194.36.174.21:3003/ParsianPGWSalePayment")) {

                startActivity(new Intent(a_webview_pay.this, a_payment.class));
                finish();
            } else {
                view.loadUrl(url);
            }
            return true;


        }

        @Override
        public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
            if (url.equalsIgnoreCase("http://194.36.174.21:3003/ParsianPGWSalePayment/index")) {

                startActivity(new Intent(a_webview_pay.this, a_payment.class));
                finish();
            } else if (url.equalsIgnoreCase("http://194.36.174.21:3003/ParsianPGWSalePayment")) {
                startActivity(new Intent(a_webview_pay.this, a_payment.class));
                finish();
            } else {

            }


            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {

            // TODO Auto-generated method stub

//            if (url.equalsIgnoreCase("http://194.36.174.21:3003/ParsianPGWSalePayment/index")) {
//
//                startActivity(new Intent(a_webview_pay.this, a_payment.class));
//                finish();
//            } else if (url.equalsIgnoreCase("http://194.36.174.21:3003/ParsianPGWSalePayment")) {
//                startActivity(new Intent(a_webview_pay.this, a_payment.class));
//                finish();
//            } else {
//
//            }
            super.onPageFinished(view, url);
            Pdialog.dismiss();

        }
    }

    @Override   // Detect when the back button is pressed
    public void onBackPressed() {
//        if (webview.canGoBack()) {
//            webview.goBack();
//        } else {
//            // Let the system handle the back button
//            startActivity(new Intent(a_webview_pay.this, a_payment.class));
//            finish();
//        }

        startActivity(new Intent(a_webview_pay.this, a_payment.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
