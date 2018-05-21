package com.franjmartin21.mydailytasks.service;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.franjmartin21.mydailytasks.data.db.MyDailyTasksDatabase;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrence;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;
import com.franjmartin21.mydailytasks.util.UtilDate;

import java.util.Date;
import java.util.List;

public class MyDailyTaskService {

    private static MyDailyTaskService INSTANCE;

    private MyDailyTasksDatabase myDb;

    private MyDailyTaskService(Context context){
        myDb = MyDailyTasksDatabase.getAppDatabase(context);
    }

    public synchronized static MyDailyTaskService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MyDailyTaskService(context);
        }
        return INSTANCE;
    }

    public LiveData<List<TaskOccurrenceItem>> getTaskOccurrenceItemList(Date startDate, Date endDate){
        return myDb.taskOccurrenceDao().loadTaskOccurrencesForDateRange(startDate, endDate);
    }

    public LiveData<List<TaskOccurrenceItem>> getTaskOccurrenceItemList(Date date){
        return getTaskOccurrenceItemList(UtilDate.atStartOfDay(date), UtilDate.atEndOfDay(date));
    }

    /*
    public List<TaskOccurrenceItem> getAllTaskOccurrences(){
        return myDb.taskOccurrenceDao().loadTaskOccurrences();
    }
     */

    public List<Task> getAllTasks(){
        return myDb.taskDao().loadTasks();
    }

    public void insertQuickTask(final String title, final Date date) {
        myDb.runInTransaction(new Runnable() {
            @Override
            public void run() {
                Task task = new Task(title);
                long id = myDb.taskDao().insert(task);
                TaskOccurrence taskOccurrence = new TaskOccurrence(date, null, 1, (int) id);
                myDb.taskOccurrenceDao().insert(taskOccurrence);
            }
        });
    }

    public void completeTaskOccurrence(int taskOcurrenceId, boolean isChecked){
        TaskOccurrence taskOccurrence = myDb.taskOccurrenceDao().loadTaskOccurrenceById(taskOcurrenceId);
        if(isChecked)
            taskOccurrence.setCompletedDate(new Date());
        else
            taskOccurrence.setCompletedDate(null);
        myDb.taskOccurrenceDao().insert(taskOccurrence);
    }

    public void saveTaskOccurrence(final TaskOccurrenceItem taskOccurrenceItem){
        myDb.runInTransaction(new Runnable() {
            @Override
            public void run() {
                TaskOccurrence taskOccurrence = myDb.taskOccurrenceDao().loadTaskOccurrenceById(taskOccurrenceItem.getOccurrenceId());
                Task task = myDb.taskDao().loadTask(taskOccurrence.getTaskId());
                taskOccurrence.setCompletedDate(taskOccurrenceItem.getCompletedDate());
                taskOccurrence.setGoalDate(taskOccurrenceItem.getGoalDate());
                task.setTitle(taskOccurrenceItem.getTitle());
                myDb.taskDao().updateTask(task);
                myDb.taskOccurrenceDao().insert(taskOccurrence);
            }
        });

    }

    public void deleteTaskOccurrence(final int taskOccurrenceId){
        myDb.runInTransaction(new Runnable() {
            @Override
            public void run() {
                TaskOccurrence taskOccurrence = myDb.taskOccurrenceDao().loadTaskOccurrenceById(taskOccurrenceId);
                myDb.taskOccurrenceDao().deleteById(taskOccurrence.getId());
                myDb.taskDao().deleteById(taskOccurrence.getTaskId());
            }
        });
    }
}
