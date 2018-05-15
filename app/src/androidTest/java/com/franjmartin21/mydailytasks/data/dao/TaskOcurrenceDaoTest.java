package com.franjmartin21.mydailytasks.data.dao;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.franjmartin21.mydailytasks.data.db.MyDailyTasksDatabase;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrence;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrenceItem;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskOcurrenceDaoTest {
    MyDailyTasksDatabase myDB;

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void init(){
        myDB = Room.inMemoryDatabaseBuilder(mContext, MyDailyTasksDatabase.class).build();
    }

    @Test
    public void testInsertOcurrence_success(){
        String TITLE_TASK = "Esta es mi primera Tarea";
        Task task = new Task(TITLE_TASK);
        myDB.taskDao().insert(task);

        Date GOALDATE_TASKOCURRENCE = new Date();
        Date COMPLETEDDATE_TASKOCURRENCE = new Date();
        int TASK_ID_TASKOCURRENCE = 1;

        TaskOcurrence taskOcurrence = new TaskOcurrence(GOALDATE_TASKOCURRENCE, COMPLETEDDATE_TASKOCURRENCE, TASK_ID_TASKOCURRENCE);
        myDB.taskOcurrenceDao().insert(taskOcurrence);
        Date startDate = new Date();
        List<TaskOcurrenceItem> taskOcurrenceItemList = myDB.taskOcurrenceDao().loadTaskOcurrencesForDateRange(GOALDATE_TASKOCURRENCE, new Date());
        Assert.assertNotNull(taskOcurrenceItemList);
        Assert.assertEquals(1, taskOcurrenceItemList.size());
        Assert.assertEquals(TITLE_TASK, taskOcurrenceItemList.get(0).getTitle());
        Assert.assertEquals(GOALDATE_TASKOCURRENCE, taskOcurrenceItemList.get(0).getGoalDate());
        Assert.assertEquals(COMPLETEDDATE_TASKOCURRENCE, taskOcurrenceItemList.get(0).getCompletedDate());
    }

    @After
    public void close(){
        myDB.close();
    }
}
