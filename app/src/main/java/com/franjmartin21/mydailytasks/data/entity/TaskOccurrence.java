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
public class TaskOccurrence extends BaseEntity{

    private Date goalDate;

    private Date completedDate;

    private int position;

    private int taskId;

    public TaskOccurrence(){
        super();
    }

    @Ignore
    public TaskOccurrence(Date goalDate, Date completedDate, int position, int taskId){
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
