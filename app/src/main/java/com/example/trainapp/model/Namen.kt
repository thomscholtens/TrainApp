package com.example.trainapp.model

import com.google.gson.annotations.SerializedName

data class Namen (
    @SerializedName("lang") val lang : String,
    @SerializedName("middel") val middel : String,
    @SerializedName("kort") val kort : String
)