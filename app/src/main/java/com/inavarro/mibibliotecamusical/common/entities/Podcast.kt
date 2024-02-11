package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class Podcast(
    val id: Long,
    val title: String,
    val image: String,
    val description: String,
    val year: Date,
    val user: User
)
