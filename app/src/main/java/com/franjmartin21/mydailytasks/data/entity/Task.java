package com.franjmartin21.mydailytasks.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Task extends BaseEntity{

    private String title;

    public Task() {
        super();
    }

    @Ignore
    public Task(String title) {
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
