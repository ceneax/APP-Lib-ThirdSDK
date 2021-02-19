package com.mdrj.wltty;

import android.app.Application;

import ceneax.lib.thirdsdk.ThirdSDK;
import ceneax.lib.thirdsdk.module.QQModule;
import ceneax.lib.thirdsdk.module.WechatModule;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new ThirdSDK.Builder(this)
                .addModule(new QQModule("1105101921"))
                .addModule(new WechatModule("wx6fff32b737311a62"))
                .build()
                .init();
    }

}
