package com.franjmartin21.mydailytasks.di;


import android.app.Application;

import com.franjmartin21.mydailytasks.activity.DailyTaskListFragment;
import com.franjmartin21.mydailytasks.activity.EditTaskFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {

    void inject(DailyTaskListFragment dailyTaskListFragment);
    void inject(EditTaskFragment editTaskFragment);

    Application application();
}
