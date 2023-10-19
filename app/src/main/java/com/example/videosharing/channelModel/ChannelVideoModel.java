package com.example.videosharing.channelModel;

// The model that will be used to access channel detail information from the get channel info query's
// JSON response
public class ChannelVideoModel {

    private String kind;

    private PageInfo pageInfo;

    private String etag;

    private Ittems[] items;

    public String getKind ()
    {
        return kind;
    }

    public void setKind (String kind)
    {
        this.kind = kind;
    }

    public PageInfo getPageInfo ()
    {
        return pageInfo;
    }

    public void setPageInfo (PageInfo pageInfo)
    {
        this.pageInfo = pageInfo;
    }

    public String getEtag ()
    {
        return etag;
    }

    public void setEtag (String etag)
    {
        this.etag = etag;
    }

    public Ittems[] getIttems ()
    {
        return items;
    }

    public void setItems (Ittems[] items)
    {
        this.items = items;
    }
}
