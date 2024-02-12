package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val email: String,
    val genero: String,
    val fechaNacimiento: Date,
    val pais: String,
    val codigoPostal: String
)
