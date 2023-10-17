package com.example.videosharing.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingltonRetrofitObject {

    private static SingltonRetrofitObject mInstance;
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static Retrofit retrofit;

    private SingltonRetrofitObject(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private synchronized SingltonRetrofitObject getmInstance() {
        if(mInstance == null) {
            mInstance = new SingltonRetrofitObject();
        }
        return mInstance;
    }

    public API getAPI() {
        return  retrofit.create(API.class);
    }
}
