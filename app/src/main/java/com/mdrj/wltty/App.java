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
                .addModule(new WechatModule("wx6fff32b737311a62", "b45b7b6679bfbeef92274adf42c78d0b"))
                .build()
                .init();
    }

}
