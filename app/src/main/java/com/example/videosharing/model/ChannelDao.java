package com.example.videosharing.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChannelDao {

    @Query("SELECT * FROM Channel")
    LiveData<List<Channel>> getChannels();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertChannel(Channel channel);
}
