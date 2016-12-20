package com.user.easypay.App;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.user.easypay.Controller.EasyPayController;

import java.lang.ref.WeakReference;


// This is the first Class invoked when application launches.
public class EasyPay extends Application {

    private LifecycleHandler appLifeCycler = new LifecycleHandler();
    private static WeakReference<Activity> activityStarted;
    private static boolean isForeground = false;

    @Override
    public void onCreate() {
        super.onCreate();

        // Register Life cycle Handler..
        registerActivityLifecycleCallbacks(appLifeCycler);

        // Initialize controller.
        EasyPayController cardsController = EasyPayController.getInstance();
        cardsController.init(this);

    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(appLifeCycler);
        super.onTerminate();
    }

    // This class is to track the Activity Life Cycle. Can be used for Analytics to track Events.
    private class LifecycleHandler implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            onAppStateChanged();
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }

    }


    // API to get the app state change whether the application is foreground or background.
    private void onAppStateChanged() {

        boolean isForeground = false;
        Activity startedActivity = null;

        if (activityStarted != null) {
            startedActivity = activityStarted.get();
        }

        if (startedActivity != null) {
            isForeground = true;
        }

        if (this.isForeground != isForeground) {
            onAppVisibilityChanged(isForeground);
        }

        this.isForeground = isForeground;
    }

    // Do operations for Application status change.
    private void onAppVisibilityChanged(boolean isForeground) {

        if(isForeground) {

        }
    }

}
