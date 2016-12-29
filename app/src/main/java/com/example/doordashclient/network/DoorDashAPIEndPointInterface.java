package com.example.doordashclient.network;

import com.example.doordashclient.model.SearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vvenkatramen on 12/28/16.
 */

public interface DoorDashAPIEndPointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @POST("/v2/auth/token/")
    Observable<String> getUser(@Field("username") String username, @Field("password") String password);

    @GET("/v2/restaurant/")
    Observable<List<SearchResult>> getRestaurantList(@Query("lat") double lat, @Query("lng") double lng);
}