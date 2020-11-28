package com.example.trainapp.model

import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name") var name: String,
    @SerializedName("plannedDateTime") var plannedDateTime: String,
    @SerializedName("actualDateTime") var actualDateTime: String,
    @SerializedName("plannedTrack") var plannedTrack: String

)