package com.example.videosharing.model;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {ChannelInfo.class},
        version = 1,
        exportSchema = false
)
public abstract class YoutubeDatabase extends RoomDatabase {
    private static YoutubeDatabase database;
    public abstract  YoutubeDao getDatabaseDao();

    public static synchronized YoutubeDatabase getDBInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(
                            context.getApplicationContext(),
                            YoutubeDatabase.class,
                            "YTDatabase"
                    )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

}
