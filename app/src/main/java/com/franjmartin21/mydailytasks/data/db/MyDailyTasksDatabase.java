package com.franjmartin21.mydailytasks.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.franjmartin21.mydailytasks.data.dao.TaskOcurrenceDao;
import com.franjmartin21.mydailytasks.data.entity.DateConverters;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.dao.TaskDao;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrence;

@Database(entities = {Task.class, TaskOcurrence.class}, version = 1)
@TypeConverters(DateConverters.class)
public abstract class MyDailyTasksDatabase extends RoomDatabase{

    public abstract TaskDao taskDao();

    public abstract TaskOcurrenceDao taskOcurrenceDao();

    private static MyDailyTasksDatabase INSTANCE;

    public static MyDailyTasksDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDailyTasksDatabase.class, "database-name").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
