package com.franjmartin21.mydailytasks.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import com.franjmartin21.mydailytasks.data.entity.DateConverters;
import com.franjmartin21.mydailytasks.data.entity.Task;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrenceItem;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task where id = :id")
    public Task loadTask(int id);

    /**
    @Query("SELECT * FROM Task where id in (:ids)")
    public List<Task> loadTasks(int... ids);
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Task task);

}
