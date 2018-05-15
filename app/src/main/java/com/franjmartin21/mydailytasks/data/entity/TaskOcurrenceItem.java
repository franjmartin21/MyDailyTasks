package com.franjmartin21.mydailytasks.data.entity;

import android.arch.persistence.room.TypeConverters;

import java.util.Date;

public class TaskOcurrenceItem {

    private String title;

    private Date goalDate;

    private Date completedDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
