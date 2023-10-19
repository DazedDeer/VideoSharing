package com.example.videosharing.channelModel;

// A submodel of ChannelVideoModel that will be used to access channel detail information from the
// get channel info query's JSON response
public class Thumbnails {
    private Deffault deffault;

    private High high;

    private Medium medium;

    public Deffault getDefault ()
    {
        return deffault;
    }

    public void setDefault (Deffault deffault)
    {
        this.deffault = deffault;
    }

    public High getHigh ()
    {
        return high;
    }

    public void setHigh (High high)
    {
        this.high = high;
    }

    public Medium getMedium ()
    {
        return medium;
    }

    public void setMedium (Medium medium)
    {
        this.medium = medium;
    }
}
