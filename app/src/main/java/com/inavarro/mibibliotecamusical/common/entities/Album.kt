package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class Album(
    val id: Long,
    val titulo: String,
    val imagen: String,
    val patrocinado: Boolean,
    val fechaInicioPatrocinio: Date,
    val fechaFinPatrocinio: Date,
    val anyo: Date,
    val artista: Artist,
    val usuario: User
)
