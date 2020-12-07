package com.example.trainapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trainapp.model.Route

class RouteRepository(context: Context) {

    private var routeDao: RouteDao

    init {
        val routeRoomDatabase = RouteRoomDatabase.getDatabase(context)
        routeDao = routeRoomDatabase!!.routeDao()
    }

    fun getAllRoutes(): LiveData<List<Route>> {
        return routeDao.getAllRoutes()
    }

    suspend fun insertRoute(route: Route) {
        routeDao.insertRoute(route)
    }

    suspend fun deleteRoute(route: Route) {
        routeDao.deleteRoute(route)
    }

    suspend fun updateRoute(route: Route) {
        routeDao.updateRoute(route)
    }
}