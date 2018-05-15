package com.franjmartin21.mydailytasks.data.entity;

import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.sql.Timestamp;
import java.util.Date;

public class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date timestamp;

    public BaseEntity(){
        timestamp = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
