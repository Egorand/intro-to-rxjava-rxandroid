package me.egorand.introtorxjava;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * @author Egor
 */
public class IntroToRxJavaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
