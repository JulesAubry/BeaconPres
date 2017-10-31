package com.estimote.blank;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /** TODO: Replace with your App ID and App Token.
         You can get them by adding a new app at https://cloud.estimote.com/#/apps
         */
        EstimoteSDK.initialize(getApplicationContext(), "beaconpres-7m6", "a915a8566f753dd9193dfe320df6a606");

        // uncomment to enable debug-level logging
        // it's usually only a good idea when troubleshooting issues with the Estimote SDK
//        EstimoteSDK.enableDebugLogging(true);
    }
}
