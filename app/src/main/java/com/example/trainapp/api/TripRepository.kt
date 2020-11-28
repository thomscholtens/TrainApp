package com.example.trainapp.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.trainapp.model.Route
import com.example.trainapp.model.Station
import com.example.trainapp.model.Trip

class TripRepository {
    private val routeApiService: RouteApiService = RouteApi.createApi()
    private val _trips: MutableLiveData<MutableList<Trip>> = MutableLiveData()
    private val _scrollContext: MutableLiveData<String> = MutableLiveData()
    private var _stations: MutableLiveData<List<Station>> = MutableLiveData()
    private val _loading = MutableLiveData<Boolean>()

    //Getters
    val trips: LiveData<MutableList<Trip>> get() = _trips
    val stations: LiveData<List<Station>> get() = _stations
    val loading: LiveData<Boolean> get() = _loading
    val scrollContext: LiveData<String> get() = _scrollContext

    init {
        _trips.value = ArrayList()
    }

     suspend fun getTripsFromTo(fromStation: String, toStation: String)  {
         _loading.value = true;
        try {
            val result = routeApiService.getTripsFromTo(fromStation, toStation)
            _trips.value = result.trips
            _scrollContext.value = result.scrollRequestForwardContext
            _loading.value = false;

        } catch (error: Throwable) {
            throw RouteError("Unable to get route", error)
        }
    }

    suspend fun loadMore(fromStation: String, toStation: String, context: String) {
        _loading.value = true;
        try {
            val result = routeApiService.loadMoreTrips(fromStation, toStation, context)
            _trips.value?.addAll(result.trips)
            _trips.value = _trips.value
            _scrollContext.value = result.scrollRequestForwardContext
            _loading.value = false
        } catch (error: Throwable) {
            throw RouteError("Unable to load more", error)
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