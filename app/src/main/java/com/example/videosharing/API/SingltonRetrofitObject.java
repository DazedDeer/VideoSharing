package com.example.videosharing.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingltonRetrofitObject {

    // Get a singlton instance
    private static SingltonRetrofitObject mInstance;

    // The base URL that will be the base for the get channel details and get channel videos queries
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    // Get an instance of retrofit
    private static Retrofit retrofit;

    // Build the retrofit object
    private SingltonRetrofitObject(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // instantiate the singltonRetrofitObject
    public static synchronized SingltonRetrofitObject getmInstance() {
        if(mInstance == null) {
            mInstance = new SingltonRetrofitObject();
        }
        return mInstance;
    }

    // get the API
    public API getAPI() {
        return  retrofit.create(API.class);
    }
}
