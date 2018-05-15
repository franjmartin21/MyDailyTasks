package com.franjmartin21.mydailytasks.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.franjmartin21.mydailytasks.data.db.MyDailyTasksDatabase;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    MyDailyTasksDatabase myDB;

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void init(){
        myDB = Room.inMemoryDatabaseBuilder(mContext, MyDailyTasksDatabase.class).build();
    }

    @Test
    public void testInsertTask_success(){
        String TITLE_TASK = "Esta es mi primera Tarea";
        Task task = new Task(TITLE_TASK);
        myDB.taskDao().insert(task);
        Task taskReturned = myDB.taskDao().loadTask(1);
        Assert.assertNotNull(taskReturned);
        Assert.assertEquals(1, taskReturned.getId());
        Assert.assertEquals(TITLE_TASK, taskReturned.getTitle());
    }

    @After
    public void close(){
        myDB.close();
    }
}
