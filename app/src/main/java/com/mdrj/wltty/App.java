package com.mdrj.wltty;

import android.app.Application;

import ceneax.lib.thirdsdk.ThirdSDK;
import ceneax.lib.thirdsdk.module.QQShareModule;
import ceneax.lib.thirdsdk.module.WechatShareModule;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new ThirdSDK.Builder(this)
                .addModule(new QQShareModule("1105101921"))
                .addModule(new WechatShareModule("wx6fff32b737311a62"))
                .build()
                .init();
    }

}
