package com.franjmartin21.mydailytasks.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;
import android.content.RestrictionEntry;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(
            entity = Task.class,
            parentColumns = "id",
            childColumns = "taskId",
            onDelete = ForeignKey.RESTRICT
        ),
        indices = {
            @Index("taskId"),
            @Index("goalDate")
        }
)
public class TaskOcurrence extends BaseEntity{

    private Date goalDate;

    private Date completedDate;

    private int taskId;

    public TaskOcurrence(){
        super();
    }

    @Ignore
    public TaskOcurrence(Date goalDate, Date completedDate, int taskId){
        super();
        this.goalDate = goalDate;
        this.completedDate = completedDate;
        this.taskId = taskId;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
