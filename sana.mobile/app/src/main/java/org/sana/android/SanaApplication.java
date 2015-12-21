package org.sana.android;

import android.app.Application;

/**
 * Created by ankush on 18/10/15.
 */
public class SanaApplication extends Application {

    public static boolean isPatient;
    public static boolean isDoctor;

    @Override
    public void onCreate() {
        super.onCreate();

        isDoctor = false;
        isPatient =false;
    }
}
