package com.example.trainapp.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trainapp.model.Station

class StationRepository {
    private val nsApiService: RouteApiService = NsApi.createApi()
    private var _stations: MutableLiveData<List<Station>> = MutableLiveData()

    //Getters
    val stations: LiveData<List<Station>> get() = _stations

    suspend fun getAllStations() {
        try {
            val result = nsApiService.getAllStations()
            _stations.value = result.stations
        } catch (error: Throwable) {
            throw StationError("Unable to get stations", error)
        }
    }

    class StationError(message: String, cause: Throwable) : Throwable(message, cause)

}