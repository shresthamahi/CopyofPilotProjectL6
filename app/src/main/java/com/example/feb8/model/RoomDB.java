package com.example.feb8.model;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.feb8.data.HabitsDao;

@Database(entities = {Habits.class},version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //creating database instance

    private static RoomDB habits_database;
    //define databse name
    private static String database_name = "habits_database.db";

    public synchronized static RoomDB getInstance(Context context) {
        //check condition
        if (habits_database == null) {
            habits_database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, database_name)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(sCallback)
                    .build();
        }
        return habits_database;
    }

    //Create DAO
    public abstract HabitsDao mainDao();

    private static RoomDatabase.Callback sCallback=new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(habits_database).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Habits,Void,Void> {
        private  HabitsDao mHabitsDAO;
        private PopulateDbAsync(RoomDB db)
        {
            mHabitsDAO=db.mainDao();
        }

        @Override
        protected Void doInBackground(Habits... maindatas) {

            Habits md= new Habits();
            md.setID(1);
            md.setDrivingInfo(3);
            md.setHandsFreeCallInfo(9);
            md.setLightInfo(5);
            md.setPostureInfo(66);
            mHabitsDAO.Insert(md);
            return null;
        }
    }

}
