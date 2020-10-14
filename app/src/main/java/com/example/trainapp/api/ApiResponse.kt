package com.example.trainapp.api

import com.example.trainapp.model.Route
import com.google.gson.annotations.SerializedName

data class ApiResponse (
    @SerializedName("results") var routes: List<Route>
)