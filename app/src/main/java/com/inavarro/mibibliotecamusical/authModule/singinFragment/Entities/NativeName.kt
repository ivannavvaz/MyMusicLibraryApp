package com.inavarro.mibibliotecamusical.authModule.singinFragment.Entities

import com.google.gson.annotations.SerializedName

data class NativeName(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
)