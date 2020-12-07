package com.example.trainapp.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trainapp.model.Trip

class TripRepository {
    private val nsApiService: RouteApiService = NsApi.createApi()
    private val _trips: MutableLiveData<MutableList<Trip>> = MutableLiveData()
    private val _scrollContext: MutableLiveData<String> = MutableLiveData()
    private val _loading = MutableLiveData<Boolean>()

    //Getters
    val trips: LiveData<MutableList<Trip>> get() = _trips
    val loading: LiveData<Boolean> get() = _loading
    val scrollContext: LiveData<String> get() = _scrollContext

    init {
        _trips.value = ArrayList()
    }

     suspend fun getTripsFromTo(fromStation: String, toStation: String)  {
         _loading.value = true;
        try {
            val result = nsApiService.getTripsFromTo(fromStation, toStation, context = null)
            _trips.value = result.trips
            _scrollContext.value = result.scrollRequestForwardContext
            _loading.value = false;

        } catch (error: Throwable) {
            throw TripError("Unable to get trips", error)
        }
    }

    suspend fun loadMore(fromStation: String, toStation: String, context: String) {
        _loading.value = true;
        try {
            val result = nsApiService.getTripsFromTo(fromStation, toStation, context)
            _trips.value?.addAll(result.trips)
            _trips.value = _trips.value
            _scrollContext.value = result.scrollRequestForwardContext
            _loading.value = false
        } catch (error: Throwable) {
            throw TripError("Unable to get trips", error)
        }
    }

    class TripError(message: String, cause: Throwable) : Throwable(message, cause)
}