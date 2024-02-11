package com.inavarro.mibibliotecamusical.common.entities

data class Artist(
    val id: Long,
    val name: String,
    val image: String,
    val user: User
)
