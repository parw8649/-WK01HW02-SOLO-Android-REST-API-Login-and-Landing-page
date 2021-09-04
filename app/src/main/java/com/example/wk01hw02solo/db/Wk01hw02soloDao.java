package com.example.wk01hw02solo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.wk01hw02solo.model.User;

import java.util.List;

@Dao
public interface Wk01hw02soloDao {

    @Insert
    Long insert(User user);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE_NAME + " ORDER BY uid DESC")
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE_NAME + " WHERE uid = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE_NAME + " WHERE username = :username")
    User getUserByUsername(String username);

}
