package com.example.trainapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RouteApi {
    companion object {

        private const val baseUrl = "https://gateway.apiportal.ns.nl/"

        /**
         * @return [RouteApiService] The service class off the retrofit client.
         */
        fun createApi(): RouteApiService{
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val routeApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit MovieApiService
            return routeApi.create(RouteApiService::class.java)
        }
    }
}