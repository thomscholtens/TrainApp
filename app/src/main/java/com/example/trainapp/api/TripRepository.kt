package com.example.trainapp.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trainapp.model.Route

class TripRepository {
    private val routeApiService: RouteApiService = RouteApi.createApi()

    private val _routes: MutableLiveData<List<Route>> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * Encapsulation :)
     */
    val routes: LiveData<List<Route>> get() = _routes

    /**
     * suspend function that calls a suspend function from the numbersApi call
     */
    suspend fun getRouteFromAndTo(fromStation: String, toStation: String)  {
        try {
            val result = routeApiService.getRouteFromAndTo(fromStation, toStation)
            _routes.value = result.routes

        } catch (error: Throwable) {
            throw RouteError("Unable to get route", error)
        }
    }

    class RouteError(message: String, cause: Throwable) : Throwable(message, cause)
}