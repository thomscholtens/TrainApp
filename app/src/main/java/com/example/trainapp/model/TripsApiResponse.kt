package com.example.trainapp.model

import com.example.trainapp.model.Route
import com.example.trainapp.model.Trip
import com.google.gson.annotations.SerializedName

data class TripsApiResponse (
    @SerializedName("scrollRequestForwardContext") var scrollRequestForwardContext: String,
    @SerializedName("trips") var trips: MutableList<Trip>
)