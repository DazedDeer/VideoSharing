package com.example.videosharing.viewModel;


import androidx.lifecycle.LiveData;

import com.google.api.services.youtube.model.Channel;

import java.util.List;


public interface YoutubeViewModelMethods {
    public void setYouTubeResponse(Channel channel);
}