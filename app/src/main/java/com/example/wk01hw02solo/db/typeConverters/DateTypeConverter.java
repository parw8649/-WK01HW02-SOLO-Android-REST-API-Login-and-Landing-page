package com.example.wk01hw02solo.db.typeConverters;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {

    @TypeConverter
    public long convertDateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date convertLongToDate(Long time) {
        return new Date(time);
    }
}
