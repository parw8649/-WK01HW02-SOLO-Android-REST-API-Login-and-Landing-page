package com.example.wk01hw02solo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.wk01hw02solo.db.typeConverters.DateTypeConverter;
import com.example.wk01hw02solo.model.User;


@Database(entities = {User.class}, version = 1)
@TypeConverters(DateTypeConverter.class)

public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "WK01HW02SOLO_DATABASE";
    public static final String USER_TABLE_NAME = "WK01HW02SOLO_USER_TABLE";

    public abstract Wk01hw02soloDao getWk01hw02soloDao();
}
