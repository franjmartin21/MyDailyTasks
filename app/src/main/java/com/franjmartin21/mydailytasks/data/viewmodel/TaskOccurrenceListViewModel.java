package com.franjmartin21.mydailytasks.data.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Delete;
import android.os.AsyncTask;

import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrence;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;
import com.franjmartin21.mydailytasks.data.repository.TaskRepository;
import com.franjmartin21.mydailytasks.util.UtilDate;

import java.util.Date;
import java.util.List;

public class TaskOccurrenceListViewModel extends ViewModel {

    public enum InsertType{
        QUICK,
        REGULAR
    }

    private MutableLiveData<InsertType> insertType;

    private TaskRepository repository;

    public TaskOccurrenceListViewModel(TaskRepository repository){
        this.repository = repository;
        this.insertType = new MutableLiveData<>();
        this.insertType.setValue(InsertType.REGULAR);
    }

    public TaskOccurrenceItem getTaskOccurrence(int id){
        return repository.getTaskOcurrence(id);
    }

    public LiveData<List<TaskOccurrenceItem>> getTaskOccurrenceItemList(Date startDate, Date endDate){
        return repository.getTaskOccurrenceItemList(startDate, endDate);
    }

    public LiveData<List<TaskOccurrenceItem>> getTaskOccurrenceItemList(Date date){
        return getTaskOccurrenceItemList(UtilDate.atStartOfDay(date), UtilDate.atEndOfDay(date));
    }

    public void insertTaskOccurrence(TaskOccurrenceItem taskOccurrenceItem) {
        new AddTaskOcurrenceTask().doInBackground(taskOccurrenceItem);
    }

    public void updateTaskOccurrence(TaskOccurrenceItem taskOccurrenceItem){
        new UpdateTaskOccurrenceTask().doInBackground(taskOccurrenceItem);
    }

    public void deleteTaskOccurrence(int taskOccurrenceId){
        new DeleteTaskOccurrenceTask().doInBackground(taskOccurrenceId);
    }

    public LiveData<InsertType> getInsertType(){
        return insertType;
    }

    public void setInsertType(InsertType insertType) {
        this.insertType.setValue(insertType);
    }

    private class AddTaskOcurrenceTask extends AsyncTask<TaskOccurrenceItem, Void, Void> {

        @Override
        protected Void doInBackground(TaskOccurrenceItem... taskOccurrenceItem) {
            repository.insertTaskOcurrenceItem(taskOccurrenceItem[0]);
            return null;
        }
    }

    private class UpdateTaskOccurrenceTask extends AsyncTask<TaskOccurrenceItem, Void, Void> {
        @Override
        protected Void doInBackground(TaskOccurrenceItem... taskOccurrenceItem) {
            repository.updateTaskOccurrence(taskOccurrenceItem[0]);
            return null;
        }
    }

    private class DeleteTaskOccurrenceTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... taskOccurrenceItemId) {
            repository.deleteTaskOccurrence(taskOccurrenceItemId[0]);
            return null;
        }
    }
}
