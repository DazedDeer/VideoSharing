package com.example.videosharing.model;

// A submodel of VideoModel that will be used to access channel video list information from the
// get channel video list query's JSON response
public class Thumbnails {

    private Deffault deffault;

    private High high;

    private Medium medium;

    public Deffault getDeffault ()
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
