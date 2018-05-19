package com.franjmartin21.mydailytasks.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.franjmartin21.mydailytasks.data.entity.TaskOccurrence;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskOccurrenceDao {

    @Query("SELECT o.id occurrenceId, title title, o.completedDate completedDate, o.goalDate goalDate, o.position FROM Task t, TaskOccurrence o WHERE t.id = o.taskId and goalDate between :startDate and :endDate")
    public LiveData<List<TaskOccurrenceItem>> loadTaskOccurrencesForDateRange(Date startDate, Date endDate);

    @Query("SELECT o.id occurrenceId, t.title title, o.completedDate completedDate, o.goalDate goalDate, o.position FROM Task t, TaskOccurrence o WHERE t.id = o.taskId")
    public LiveData<List<TaskOccurrenceItem>> loadTaskOccurrences();

    @Query("SELECT * FROM TaskOccurrence where id = :id")
    public TaskOccurrence loadTaskOccurrenceById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(TaskOccurrence taskOccurrence);

}
