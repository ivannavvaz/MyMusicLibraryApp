package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class Album(
    val id: Long,
    val title: String,
    val image: String,
    val sponsored: Boolean,
    val sponsoredStartDate: Date,
    val sponsoredEndDate: Date,
    val year: Date,
    val artist: Artist,
    val user: User
)
