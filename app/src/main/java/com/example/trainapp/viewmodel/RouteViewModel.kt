package com.example.trainapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.trainapp.api.TripRepository
import com.example.trainapp.database.RouteRepository
import com.example.trainapp.model.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteViewModel (application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val routeRepository = RouteRepository(application.applicationContext)

    /**
     * This property points direct to the LiveData in the repository, that value
     * get's updated when user clicks FAB. This happens through the getTriviaNumber() in this class :)
     */
    val routes: LiveData<List<Route>> = routeRepository.getAllRoutes()

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
}