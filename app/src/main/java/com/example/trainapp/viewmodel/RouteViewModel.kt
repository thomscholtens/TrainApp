package com.example.trainapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.*
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

    private val _selectedRoute =  MutableLiveData<Route>()
    private val _selectedTrip =  MutableLiveData<Trip>()
    private val _editRoute = MutableLiveData<Route>()
    private val _errorText: MutableLiveData<String> = MutableLiveData()
    private val _editMode: MutableLiveData<Boolean> = MutableLiveData()

    val stations = tripRepository.stations
    val trips = tripRepository.trips
    val loading = tripRepository.loading
    val scrollContext = tripRepository.scrollContext
    val routes: LiveData<List<Route>> = routeRepository.getAllRoutes()

    //Getters
    val errorText: LiveData<String> get() = _errorText
    val selectedRoute: LiveData<Route> get() = _selectedRoute
    val selectedTrip: LiveData<Trip> get() = _selectedTrip
    val editRoute: LiveData<Route> get() = _editRoute
    val editMode: LiveData<Boolean> get() = _editMode

    fun selectRoute(route: Route) {
        _selectedRoute.value = route
    }

    fun selectTrip(trip: Trip) {
        _selectedTrip.value = trip
    }

    fun selectEditRoute(route: Route) {
        _editRoute.value = route
    }

    fun setEditMode(editMode: Boolean) {
        _editMode.value = editMode
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
            } catch (error: TripRepository.RouteError) {
                _errorText.value = error.message
            }
        }
    }

    fun loadMore(fromStation: String, toStation: String, context: String) {
        viewModelScope.launch {
            try {
                tripRepository.loadMore(fromStation, toStation, context)
            } catch (error: TripRepository.RouteError) {
                _errorText.value = error.message
            }
        }
    }

    fun getAllStations() {
        viewModelScope.launch {
            try {
                tripRepository.getAllStations()
            } catch (error: TripRepository.RouteError) {
                _errorText.value = error.message
            }
        }
    }
}