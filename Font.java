package com.zare.karbala;

import android.app.Application;
import android.support.multidex.MultiDex;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Font extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("IRANSans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("IRANSans.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
    }
}