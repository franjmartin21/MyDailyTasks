package com.franjmartin21.mydailytasks.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Transaction;
import android.util.Log;

import com.franjmartin21.mydailytasks.data.dao.TaskDao;
import com.franjmartin21.mydailytasks.data.dao.TaskOccurrenceDao;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrence;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;
import com.franjmartin21.mydailytasks.util.UtilDate;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class TaskRepository {

    private final TaskDao taskDao;

    private final TaskOccurrenceDao taskOccurrenceDao;

    @Inject
    public TaskRepository(TaskDao taskDao, TaskOccurrenceDao taskOccurrenceDao) {
        this.taskDao = taskDao;
        this.taskOccurrenceDao = taskOccurrenceDao;
    }

    public TaskOccurrenceItem getTaskOcurrence(int taskOccurrenceId){
        return taskOccurrenceDao.loadTaskOccurrenceItem(taskOccurrenceId);
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

    @Transaction
    public void insertTaskOcurrenceItem(TaskOccurrenceItem taskOccurrenceItem){
        Task task = new Task();
        task.setId(taskOccurrenceItem.getTaskId());
        task.setTitle(taskOccurrenceItem.getTitle());
        int taskId = (int)taskDao.insert(task);
        TaskOccurrence taskOccurrence = new TaskOccurrence();
        taskOccurrence.setGoalDate(taskOccurrenceItem.getGoalDate());
        taskOccurrence.setCompletedDate(taskOccurrenceItem.getCompletedDate());
        taskOccurrence.setTaskId(taskId);
        taskOccurrenceDao.insert(taskOccurrence);
    }

    @Transaction
    public void updateTaskOccurrence(final TaskOccurrenceItem taskOccurrenceItem){
        TaskOccurrence taskOccurrence = taskOccurrenceDao.loadTaskOccurrenceById(taskOccurrenceItem.getOccurrenceId());
        Task task = taskDao.loadTask(taskOccurrence.getTaskId());
        taskOccurrence.setCompletedDate(taskOccurrenceItem.getCompletedDate());
        taskOccurrence.setGoalDate(taskOccurrenceItem.getGoalDate());
        task.setTitle(taskOccurrenceItem.getTitle());
        taskDao.updateTask(task);
        taskOccurrenceDao.insert(taskOccurrence);
    }

    @Transaction
    public void deleteTaskOccurrence(final int taskOccurrenceId){
        TaskOccurrence taskOccurrence = taskOccurrenceDao.loadTaskOccurrenceById(taskOccurrenceId);
        taskOccurrenceDao.deleteById(taskOccurrence.getId());
        taskDao.deleteById(taskOccurrence.getTaskId());
    }
}
