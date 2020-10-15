package com.example.trainapp.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.trainapp.model.Route
import com.example.trainapp.model.Station

class TripRepository {
    private val routeApiService: RouteApiService = RouteApi.createApi()

    private val _routes: MutableLiveData<List<Route>> = MutableLiveData()

    private var _stations: MutableLiveData<List<Station>> = MutableLiveData()


    /**
     * Expose non MutableLiveData via getter
     * Encapsulation :)
     */
    val routes: LiveData<List<Route>> get() = _routes

    val stations: LiveData<List<Station>> get() = _stations



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



    suspend fun getAllStations() {

        try {
            val result = routeApiService.getAllStations()
            _stations.value = result.stations

        } catch (error: Throwable) {
            throw RouteError("Something went wrong", error)
        }

    }



    class RouteError(message: String, cause: Throwable) : Throwable(message, cause)
}