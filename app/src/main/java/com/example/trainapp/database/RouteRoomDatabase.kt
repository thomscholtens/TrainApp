package com.example.trainapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trainapp.model.Route

@Database(entities = [Route::class], version = 1, exportSchema = false)
abstract class RouteRoomDatabase : RoomDatabase() {

    abstract fun routeDao(): RouteDao

    companion object {
        private const val DATABASE_NAME = "ROUTE_DATABASE"

        @Volatile
        private var routeRoomDatabaseInstance: RouteRoomDatabase? = null

        fun getDatabase(context: Context): RouteRoomDatabase? {
            if (routeRoomDatabaseInstance == null) {
                synchronized(RouteRoomDatabase::class.java) {
                    if (routeRoomDatabaseInstance == null) {
                        routeRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            RouteRoomDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return routeRoomDatabaseInstance
        }
    }
}