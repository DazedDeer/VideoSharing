package com.example.videosharing.model;

// A submodel of VideoModel that will be used to access channel video list information from the
// get channel video list query's JSON response
public class High {
    private String width;

    private String url;

    private String height;

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }
}
