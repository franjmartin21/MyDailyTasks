package com.franjmartin21.mydailytasks.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.franjmartin21.mydailytasks.data.db.MyDailyTasksDatabase;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrence;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskOccurrenceDaoTest {
    MyDailyTasksDatabase myDB;

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void init(){
        myDB = Room.inMemoryDatabaseBuilder(mContext, MyDailyTasksDatabase.class).build();
    }

    @Test
    public void testInsertOccurrence_success(){
        String TITLE_TASK = "Esta es mi primera Tarea";
        Task task = new Task(TITLE_TASK);
        myDB.taskDao().insert(task);

        Date GOALDATE_TASKOCCURRENCE = new Date();
        Date COMPLETEDDATE_TASKOCCURRENCE = new Date();
        int TASK_ID_TASKOCCURRENCE = 1;
        int POSITION_TASKOCCURRENCE =1;

        TaskOccurrence taskOccurrence = new TaskOccurrence(GOALDATE_TASKOCCURRENCE, COMPLETEDDATE_TASKOCCURRENCE, POSITION_TASKOCCURRENCE, TASK_ID_TASKOCCURRENCE);
        myDB.taskOccurrenceDao().insert(taskOccurrence);
        Date startDate = new Date();
        LiveData<List<TaskOccurrenceItem>> taskOccurrenceItemList = myDB.taskOccurrenceDao().loadTaskOccurrencesForDateRange(GOALDATE_TASKOCCURRENCE, new Date());
        Assert.assertNotNull(taskOccurrenceItemList.getValue());
        Assert.assertEquals(1, taskOccurrenceItemList.getValue().size());
        Assert.assertEquals(TITLE_TASK, taskOccurrenceItemList.getValue().get(0).getTitle());
        Assert.assertEquals(GOALDATE_TASKOCCURRENCE, taskOccurrenceItemList.getValue().get(0).getGoalDate());
        Assert.assertEquals(COMPLETEDDATE_TASKOCCURRENCE, taskOccurrenceItemList.getValue().get(0).getCompletedDate());
    }

    @After
    public void close(){
        myDB.close();
    }
}
