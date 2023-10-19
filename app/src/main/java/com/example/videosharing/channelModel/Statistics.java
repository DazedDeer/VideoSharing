package com.example.videosharing.channelModel;

// A submodel of ChannelVideoModel that will be used to access channel detail information from the
// get channel info query's JSON response
public class Statistics {

    private String videoCount;

    private String subscriberCount;

    private String viewCount;

    private String hiddenSubscriberCount;

    public String getVideoCount ()
    {
        return videoCount;
    }

    public void setVideoCount (String videoCount)
    {
        this.videoCount = videoCount;
    }

    public String getSubscriberCount ()
    {
        return subscriberCount;
    }

    public void setSubscriberCount (String subscriberCount)
    {
        this.subscriberCount = subscriberCount;
    }

    public String getViewCount ()
    {
        return viewCount;
    }

    public void setViewCount (String viewCount)
    {
        this.viewCount = viewCount;
    }

    public String getHiddenSubscriberCount ()
    {
        return hiddenSubscriberCount;
    }

    public void setHiddenSubscriberCount (String hiddenSubscriberCount)
    {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
    }
}
