package com.franjmartin21.mydailytasks.data.entity;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverters {

    @TypeConverter
    public static Long dateToLong(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date longToDate(Long time) {
        return time == null ? null : new Date(time);
    }
}
