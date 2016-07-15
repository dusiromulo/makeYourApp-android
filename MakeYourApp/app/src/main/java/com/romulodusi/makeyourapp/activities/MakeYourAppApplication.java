package com.romulodusi.makeyourapp.activities;

import android.app.Application;

import com.romulodusi.makeyourapp.database.ProjectDbHelper;
import com.romulodusi.makeyourapp.database.ScreenDbHelper;


public class MakeYourAppApplication extends Application {

    public static ProjectDbHelper projectDb;
    public static ScreenDbHelper screenDb;

    @Override
    public void onCreate() {
        super.onCreate();

        projectDb = new ProjectDbHelper(this);
        screenDb = new ScreenDbHelper(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
