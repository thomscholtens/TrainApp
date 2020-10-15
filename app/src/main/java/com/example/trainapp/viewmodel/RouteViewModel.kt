package com.example.trainapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.trainapp.api.RouteApi
import com.example.trainapp.api.RouteApiService
import com.example.trainapp.api.TripRepository
import com.example.trainapp.database.RouteRepository
import com.example.trainapp.model.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteViewModel (application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val routeRepository = RouteRepository(application.applicationContext)
    private val tripRepository = TripRepository()

    val stations = tripRepository.stations

    val routes: LiveData<List<Route>> = routeRepository.getAllRoutes()

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val errorText: LiveData<String>
        get() = _errorText

    fun insertRoute(route: Route) {
        ioScope.launch {
            routeRepository.insertRoute(route)
        }
    }

    fun deleteRoute(route: Route) {
        ioScope.launch {
            routeRepository.deleteRoute(route)
        }
    }

    fun getAllStations() {
        viewModelScope.launch {
            try {
                tripRepository.getAllStations()
            } catch (error: TripRepository.RouteError) {
                _errorText.value = error.message
                Log.e("Stations error", error.cause.toString())
            }
        }
    }
}