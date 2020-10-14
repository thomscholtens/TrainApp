package com.example.trainapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.trainapp.model.Route

@Dao
interface RouteDao {
    @Query("SELECT * FROM routeTable")
    fun getAllRoutes(): LiveData<List<Route>>

    @Insert
    suspend fun insertRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)

    @Update
    suspend fun updateRoute(route: Route)
}
