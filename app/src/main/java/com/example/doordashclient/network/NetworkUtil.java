package com.example.doordashclient.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by vvenkatramen on 12/28/16.
 */

public class NetworkUtil {
    private static final String BASE_URL = "https://api.doordash.com/";
    public static DoorDashAPIEndPointInterface apiService;
    static {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
        apiService = retrofit.create(DoorDashAPIEndPointInterface.class);
    }
    
}
