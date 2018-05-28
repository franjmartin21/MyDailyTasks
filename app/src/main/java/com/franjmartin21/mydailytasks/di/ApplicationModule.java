package com.franjmartin21.mydailytasks.di;

import android.app.Application;

import com.franjmartin21.mydailytasks.MyDailyTasksApplication;
import com.franjmartin21.mydailytasks.activity.util.UIUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final MyDailyTasksApplication application;

    public ApplicationModule(MyDailyTasksApplication application) {
        this.application = application;
    }

    @Provides
    MyDailyTasksApplication provideMyDailyTaskApplication(){
        return application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }

}
