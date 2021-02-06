package ceneax.app.demo;

import android.app.Application;

import ceneax.lib.thirdsdk.ThirdSDK;
import ceneax.lib.thirdsdk.module.QQShareModule;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new ThirdSDK.Builder()
                .addModule(new QQShareModule(this, "222222"))
                .build()
                .init();
    }

}
