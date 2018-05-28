package com.franjmartin21.mydailytasks;

import android.app.Application;

import com.franjmartin21.mydailytasks.di.ApplicationComponent;
import com.franjmartin21.mydailytasks.di.ApplicationModule;
import com.franjmartin21.mydailytasks.di.DaggerApplicationComponent;
import com.franjmartin21.mydailytasks.di.RoomModule;


public class MyDailyTasksApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
