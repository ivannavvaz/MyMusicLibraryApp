package com.inavarro.mibibliotecamusical.common.entities

data class Artist(
    val id: Long,
    val nombre: String,
    val imagen: String,
    val usuario: User
)
