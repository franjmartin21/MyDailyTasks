package com.franjmartin21.mydailytasks.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.franjmartin21.mydailytasks.data.dao.TaskDao;
import com.franjmartin21.mydailytasks.data.dao.TaskOccurrenceDao;
import com.franjmartin21.mydailytasks.data.db.MyDailyTasksDatabase;
import com.franjmartin21.mydailytasks.data.repository.TaskRepository;
import com.franjmartin21.mydailytasks.data.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private final MyDailyTasksDatabase database;

    public RoomModule(Application application){
        this.database = Room.databaseBuilder(application, MyDailyTasksDatabase.class, "mydailytask-db")
                .allowMainThreadQueries()
                //.addCallback(rdc)
                .build();

    }

    @Provides
    @Singleton
    TaskRepository provideTaskRepository(TaskDao taskDao, TaskOccurrenceDao taskOccurrenceDao){
        return new TaskRepository(taskDao, taskOccurrenceDao);
    }

    @Provides
    @Singleton
    TaskDao provideTaskDao(MyDailyTasksDatabase database){
        return database.taskDao();
    }

    @Provides
    @Singleton
    TaskOccurrenceDao provideTaskOcurrenceDao(MyDailyTasksDatabase database){
        return database.taskOccurrenceDao();
    }

    @Provides
    @Singleton
    MyDailyTasksDatabase provideMyDailyTaskDatabase(){
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(TaskRepository repository){
        return new CustomViewModelFactory(repository);
    }
}
