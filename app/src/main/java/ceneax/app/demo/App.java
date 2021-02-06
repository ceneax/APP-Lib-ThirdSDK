package ceneax.app.demo;

import android.app.Application;

import ceneax.lib.thirdsdk.ThirdSDK;
import ceneax.lib.thirdsdk.module.QQShareModule;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new ThirdSDK.Builder(this)
                .addModule(new QQShareModule("1105101921"))
                .build()
                .init();
    }

}
