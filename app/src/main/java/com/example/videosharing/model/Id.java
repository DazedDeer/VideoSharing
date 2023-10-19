package com.example.videosharing.model;

// A submodel of VideoModel that will be used to access channel video list information from the
// get channel video list query's JSON response
public class Id {

    private String kind;

    private String videoId;

    public String getKind ()
    {
        return kind;
    }

    public void setKind (String kind)
    {
        this.kind = kind;
    }

    public String getVideoId ()
    {
        return videoId;
    }

    public void setVideoId (String videoId)
    {
        this.videoId = videoId;
    }
}
