package com.example.trainapp.model

import com.google.gson.annotations.SerializedName

data class Trip (
    @SerializedName("status") var status: String,
    @SerializedName("uid") var uid: String,
    @SerializedName("plannedDurationInMinutes") var plannedDurationInMinutes: Int,
    @SerializedName("actualDurationInMinutes") var actualDurationInMinutes: Int,
    @SerializedName("legs") var legs: List<Legs>,
    @SerializedName("transfers") var transfers: Int
)