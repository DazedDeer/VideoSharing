package com.example.videosharing.channelModel;

import com.example.videosharing.model.Items;
import com.example.videosharing.model.PageInfo;

public class ChannelVideoModel {

    private String kind;

    private PageInfo pageInfo;

    private String etag;

    private Items[] items;

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

    public Items[] getItems ()
    {
        return items;
    }

    public void setItems (Items[] items)
    {
        this.items = items;
    }
}
