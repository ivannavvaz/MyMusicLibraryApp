package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class Album(
    val id: Long,
    val titulo: String,
    val imagen: String,
    val patrocinado: Boolean,
    val fechaInicioPatrocinado: Date,
    val fechaFinPatrocinado: Date,
    val anyo: Date,
    val artitsta: Artist,
    val usuario: User
)
