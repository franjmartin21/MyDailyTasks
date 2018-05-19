package com.franjmartin21.mydailytasks.data.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.franjmartin21.mydailytasks.data.dao.TaskDao;
import com.franjmartin21.mydailytasks.data.dao.TaskOccurrenceDao;
import com.franjmartin21.mydailytasks.data.entity.DateConverters;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Database(entities = {Task.class, TaskOccurrence.class}, version = 1)
@TypeConverters(DateConverters.class)
public abstract class MyDailyTasksDatabase extends RoomDatabase{

    public abstract TaskDao taskDao();

    public abstract TaskOccurrenceDao taskOccurrenceDao();

    private static MyDailyTasksDatabase INSTANCE;

    public synchronized static MyDailyTasksDatabase getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static MyDailyTasksDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), MyDailyTasksDatabase.class, "mydailytask-db")
                .allowMainThreadQueries()
                //.addCallback(rdc)
                .build();
    }


    static RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
        public void onCreate (SupportSQLiteDatabase db) {
            Log.d(MyDailyTasksDatabase.class.getSimpleName(), "onCreate");
            // do something after database has been created
            db.beginTransaction();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //db.execSQL("INSERT INTO Task (id, title) VALUES(1, 'Esta es mi primera tarea')");
            //db.execSQL("INSERT INTO TaskOccurrence (id, goalDate, completedDate, position, taskId) " +
            //        "VALUES(1,'" + df.format(new Date())+ "','" + df.format(new Date()) + "',1,1)");
            db.endTransaction();
        }
        public void onOpen (SupportSQLiteDatabase db) {
            Log.d(MyDailyTasksDatabase.class.getSimpleName(), "onOpen");
            // do something every time database is open
        }
    };

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
