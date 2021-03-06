package com.franjmartin21.mydailytasks.data.entity;

import android.arch.persistence.room.TypeConverters;

import java.util.Date;

public class TaskOccurrenceItem {

    private int occurrenceId;

    private String title;

    private Date goalDate;

    private Date completedDate;

    private int position;

    public int getOccurrenceId() {
        return occurrenceId;
    }

    public void setOccurrenceId(int occurrenceId) {
        this.occurrenceId = occurrenceId;
    }

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
