package com.example.trainapp.model

import com.google.gson.annotations.SerializedName

data class StationsApiResult (
    @SerializedName("payload") var stations: List<Station>
)