package com.example.videosharing.channelModel;

// A submodel of ChannelVideoModel that will be used to access channel detail information from the
// get channel info query's JSON response
public class Localized {
    private String description;

    private String title;

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }
}
