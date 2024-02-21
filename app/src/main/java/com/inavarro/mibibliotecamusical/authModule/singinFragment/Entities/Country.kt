package com.inavarro.mibibliotecamusical.authModule.singinFragment.Entities

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: CountryName,
    @SerializedName("flag") val flag: String
)
