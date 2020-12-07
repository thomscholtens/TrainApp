package com.example.trainapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.*
import com.example.trainapp.api.StationRepository
import com.example.trainapp.api.TripRepository
import com.example.trainapp.database.RouteRepository
import com.example.trainapp.model.Route
import com.example.trainapp.model.Trip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteViewModel (application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val routeRepository = RouteRepository(application.applicationContext)
    private val tripRepository = TripRepository()
    private val stationRepository = StationRepository()

    private val _selectedRoute =  MutableLiveData<Route>()
    private val _selectedTrip =  MutableLiveData<Trip>()
    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val stations = stationRepository.stations
    val trips = tripRepository.trips
    val loading = tripRepository.loading
    val scrollContext = tripRepository.scrollContext
    val routes: LiveData<List<Route>> = routeRepository.getAllRoutes()

    //Getters
    val errorText: LiveData<String> get() = _errorText
    val selectedRoute: LiveData<Route> get() = _selectedRoute
    val selectedTrip: LiveData<Trip> get() = _selectedTrip

    fun selectRoute(route: Route) {
        _selectedRoute.value = route
    }

    fun selectTrip(trip: Trip) {
        _selectedTrip.value = trip
    }

    fun insertRoute(route: Route) {
        ioScope.launch {
            routeRepository.insertRoute(route)
        }
    }

    fun updateRoute(route: Route) {
        ioScope.launch {
            routeRepository.updateRoute(route)
        }
    }

    fun deleteRoute(route: Route) {
        ioScope.launch {
            routeRepository.deleteRoute(route)
        }
    }

    fun getTripsFromTo(fromStation: String, toStation: String) {
        viewModelScope.launch {
            try {
                tripRepository.getTripsFromTo(fromStation, toStation)
            } catch (error: TripRepository.TripError) {
                _errorText.value = error.message
            }
        }
    }

    fun loadMore(fromStation: String, toStation: String, context: String) {
        viewModelScope.launch {
            try {
                tripRepository.loadMore(fromStation, toStation, context)
            } catch (error: TripRepository.TripError) {
                _errorText.value = error.message
            }
        }
    }

    fun getAllStations() {
        viewModelScope.launch {
            try {
                stationRepository.getAllStations()
            } catch (error: StationRepository.StationError) {
                _errorText.value = error.message
            }
        }
    }
}