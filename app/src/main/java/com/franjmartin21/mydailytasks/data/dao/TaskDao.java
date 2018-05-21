package com.franjmartin21.mydailytasks.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.franjmartin21.mydailytasks.data.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task")
    public List<Task> loadTasks();

    @Query("SELECT * FROM Task where id = :id")
    public Task loadTask(int id);

    /**
    @Query("SELECT * FROM Task where id in (:ids)")
    public List<Task> loadTasks(int... ids);
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Task task);

    @Query("DELETE FROM Task where id = :id")
    public void deleteById(int id);

    @Update
    public void updateTask(Task task);

}
