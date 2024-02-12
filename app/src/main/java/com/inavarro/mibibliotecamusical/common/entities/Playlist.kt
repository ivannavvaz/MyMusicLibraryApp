package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class Playlist(
    val id: Long,
    val titulo: String,
    val numeroCanciones: Int,
    val fechaCreacion: Date,
    val usuario: User,
    val usuarioSeguidor: MutableList<User>
)
