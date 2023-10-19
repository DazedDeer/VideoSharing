package com.example.videosharing.API;

import com.example.videosharing.channelModel.ChannelVideoModel;
import com.example.videosharing.model.VideoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// Uses Retrofit with the youtube API to get channels and channel video info
public interface API {

    // Gets information on a channel's videos
    @GET("search")
    Call<VideoModel> getVideosDetails(@Query("key") String key,
                                      @Query("channelId") String channelId,
                                      @Query("part") String part,
                                      @Query("order") String order,
                                      @Query("maxResults") String maxResult,
                                      @Query("type") String type);

    // Gets information on a channel
    @GET("channels")
    Call<ChannelVideoModel> getChannelDetails(@Query("part") String part,
                                              @Query("id") String channelId,
                                              @Query("key") String key);
}

// The query to get the Rick Astley channel's videos
//https://www.googleapis.com/youtube/v3/search?
//key=AIzaSyAzzimVmu9nBvifyIdwaY-el371vrczEhk
//&channelId=UCuAXFkgsw1L7xaCfnd5JJOw
//&part=snippet
//&order=date
//&maxResults=50
//&type=video

// The query to get the Rick Astley channel's details
//https://youtube.googleapis.com/youtube/v3/channels?
//part=snippet%2Cstatistics
//&id=UCuAXFkgsw1L7xaCfnd5JJOw
//&key=AIzaSyAzzimVmu9nBvifyIdwaY-el371vrczEhk