package com.example.trainapp.api

import com.example.trainapp.model.TripsApiResponse
import com.example.trainapp.model.StationsApiResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RouteApiService {
    @Headers("Ocp-Apim-Subscription-Key: 8e9b6636ce6c46829f9c20052ba61d15")
    @GET("/reisinformatie-api/api/v3/trips/")
    suspend fun getTripsFromTo(
        @Query("fromStation") fromStation: String,
        @Query("toStation") toStation: String,
        @Query("context") context: String?
    ) : TripsApiResponse

    @Headers("Ocp-Apim-Subscription-Key: 8e9b6636ce6c46829f9c20052ba61d15")
    @GET("/reisinformatie-api/api/v2/stations")
    suspend fun getAllStations() : StationsApiResult
}