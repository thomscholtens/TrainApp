package com.example.trainapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("namen") val namen : Namen
)