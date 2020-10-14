package com.example.trainapp.model

import com.google.gson.annotations.SerializedName

data class Legs(
    @SerializedName("direction") var direction: String,
    @SerializedName("cancelled") var cancelled: Boolean,
    @SerializedName("origin") var origin: Origin,
    @SerializedName("destination") var destination: Destination
)