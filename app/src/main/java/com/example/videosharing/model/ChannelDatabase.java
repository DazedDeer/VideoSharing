package com.example.videosharing.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {Channel.class},
        version = 1,
        exportSchema = false
)
public abstract class ChannelDatabase extends RoomDatabase {

    private static ChannelDatabase database;
    public abstract ChannelDao getChannelDBDao();

    public static synchronized ChannelDatabase getChanDBInstance(Context context){
        if(database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ChannelDatabase.class,
                    "ChannelDatabase"
            )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
