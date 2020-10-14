package com.example.trainapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routeTable")
data class Route (

    @ColumnInfo(name= "fromStation")
    var fromStation: String,

    @ColumnInfo(name= "toStation")
    var toStation: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

)