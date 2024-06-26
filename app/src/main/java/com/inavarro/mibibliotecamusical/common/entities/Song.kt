package com.inavarro.mibibliotecamusical.common.entities

data class Song(
    val id: Long,
    val titulo: String,
    val duracion: Int,
    val ruta: String,
    val numeroReproducciones: Int,
    val album: Album
) {

    override fun toString(): String {
        return titulo
    }
}
