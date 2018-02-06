package com.xfc.mymvp;

import android.app.Application;
import android.content.Context;

/**
 * Created by xfc on 2018/2/2.
 */

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
