package com.franjmartin21.mydailytasks.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.franjmartin21.mydailytasks.data.dao.TaskOccurrenceDao;
import com.franjmartin21.mydailytasks.data.db.MyDailyTasksDatabase;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;
import com.franjmartin21.mydailytasks.util.UtilDate;

import java.util.Date;
import java.util.List;

public class TaskOccurrenceViewModel extends AndroidViewModel {

    private TaskOccurrenceDao taskOccurrenceDao;

    private TaskOccurrenceItem taskOcurrenceItem;

    public TaskOccurrenceViewModel(@NonNull Application application) {
        super(application);
        MyDailyTasksDatabase myDB = MyDailyTasksDatabase.getAppDatabase(application);
        taskOccurrenceDao = myDB.taskOccurrenceDao();
    }

    public TaskOccurrenceItem getTaskOcurrence(int taskOccurrenceId){
        taskOcurrenceItem = taskOccurrenceDao.loadTaskOccurrenceItem(taskOccurrenceId);
        return taskOcurrenceItem;
    }

    public LiveData<List<TaskOccurrenceItem>> getTaskOccurrenceItemList(Date startDate, Date endDate){
        return taskOccurrenceDao.loadTaskOccurrencesForDateRange(startDate, endDate);
    }

    public LiveData<List<TaskOccurrenceItem>> getAllTaskOccurrences(){
        return taskOccurrenceDao.loadTaskOccurrences();
    }

    public LiveData<List<TaskOccurrenceItem>> getTaskOccurrenceItemList(Date date){
        return getTaskOccurrenceItemList(UtilDate.atStartOfDay(date), UtilDate.atEndOfDay(date));
    }


    public TaskOccurrenceItem getTaskOcurrenceItem() {
        return taskOcurrenceItem;
    }

}
