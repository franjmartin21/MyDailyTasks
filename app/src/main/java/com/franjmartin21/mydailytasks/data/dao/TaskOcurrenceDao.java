package com.franjmartin21.mydailytasks.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrence;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrenceItem;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskOcurrenceDao {

    @Query("SELECT t.title title, o.completedDate completedDate, o.goalDate goalDate, o.position position FROM Task t, TaskOcurrence o WHERE t.id = o.taskId and goalDate between :startDate and :endDate")
    public List<TaskOcurrenceItem> loadTaskOcurrencesForDateRange(Date startDate, Date endDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(TaskOcurrence taskOcurrence);

}
