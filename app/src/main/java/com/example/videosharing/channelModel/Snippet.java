package com.example.videosharing.channelModel;

public class Snippet {
    private String customUrl;

    private String country;

    private String publishedAt;

    private Localized localized;

    private String description;

    private String title;

    private Thumbnails thumbnails;

    public String getCustomUrl ()
    {
        return customUrl;
    }

    public void setCustomUrl (String customUrl)
    {
        this.customUrl = customUrl;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getPublishedAt ()
    {
        return publishedAt;
    }

    public void setPublishedAt (String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public Localized getLocalized ()
    {
        return localized;
    }

    public void setLocalized (Localized localized)
    {
        this.localized = localized;
    }

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

    public Thumbnails getThumbnails ()
    {
        return thumbnails;
    }

    public void setThumbnails (Thumbnails thumbnails)
    {
        this.thumbnails = thumbnails;
    }
}
