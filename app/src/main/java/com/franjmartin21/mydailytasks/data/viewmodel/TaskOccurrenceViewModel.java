package com.franjmartin21.mydailytasks.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.franjmartin21.mydailytasks.data.dao.TaskDao;
import com.franjmartin21.mydailytasks.data.dao.TaskOccurrenceDao;
import com.franjmartin21.mydailytasks.data.db.MyDailyTasksDatabase;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrence;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;
import com.franjmartin21.mydailytasks.util.UtilDate;

import java.util.Date;
import java.util.List;

public class TaskOccurrenceViewModel extends AndroidViewModel {

    private Date showedDate;

    private TaskOccurrenceDao taskOccurrenceDao;

    private TaskOccurrenceItem taskOcurrenceItem;

    public TaskOccurrenceViewModel(@NonNull Application application) {
        super(application);
        MyDailyTasksDatabase myDB = MyDailyTasksDatabase.getAppDatabase(application);
        taskOccurrenceDao = myDB.taskOccurrenceDao();
        showedDate = new Date();
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

    public LiveData<List<TaskOccurrenceItem>> getTaskOccurrenceItemList(){
        return getTaskOccurrenceItemList(UtilDate.atStartOfDay(showedDate), UtilDate.atEndOfDay(showedDate));
    }


    public TaskOccurrenceItem getTaskOcurrenceItem() {
        return taskOcurrenceItem;
    }

    public Date getShowedDate() {
        return showedDate;
    }

    public void setShowedDate(Date showedDate) {
        this.showedDate = showedDate;
    }

}
