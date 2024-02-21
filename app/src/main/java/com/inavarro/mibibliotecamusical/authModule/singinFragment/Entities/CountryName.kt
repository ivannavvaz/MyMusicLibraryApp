package com.inavarro.mibibliotecamusical.authModule.singinFragment.Entities

import com.google.gson.annotations.SerializedName

data class CountryName(
    @SerializedName("common") val common: String,
    @SerializedName("official") val official: String,
    @SerializedName("nativeName") val nativeName: Map<String, NativeName>
)


