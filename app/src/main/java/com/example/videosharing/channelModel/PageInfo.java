package com.example.videosharing.channelModel;

// A submodel of ChannelVideoModel that will be used to access channel detail information from the
// get channel info query's JSON response
public class PageInfo {

    private String totalResults;

    private String resultsPerPage;

    public String getTotalResults ()
    {
        return totalResults;
    }

    public void setTotalResults (String totalResults)
    {
        this.totalResults = totalResults;
    }

    public String getResultsPerPage ()
    {
        return resultsPerPage;
    }

    public void setResultsPerPage (String resultsPerPage)
    {
        this.resultsPerPage = resultsPerPage;
    }
}
