package com.example.feb8.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.feb8.model.Habits;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface HabitsDao {

    @Insert(onConflict = REPLACE)
    void Insert (Habits habits);


    @Query("UPDATE Habits SET lightInfo = :newVal where ID =1")
    void updateLightInfo(int newVal);

    @Query("UPDATE Habits SET postureInfo = :newVal where ID =1")
    void updatePostureInfo(int newVal);

    @Query("UPDATE Habits SET drivingInfo = :newVal where ID =1")
    void updateDriveCallInfo(int newVal);


    @Query("SELECT * FROM Habits")
    List<Habits> getAll();
}
