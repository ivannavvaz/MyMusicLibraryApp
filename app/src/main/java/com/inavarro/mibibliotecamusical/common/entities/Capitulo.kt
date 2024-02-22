package com.inavarro.mibibliotecamusical.common.entities

import java.time.Duration
import java.util.Date

data class Capitulo(
    val id: Long,
    val titulo: String,
    val descripcion: String,
    val duracion: Long,
    val fecha: Date,
    val numeroReproducciones: Long,
    val podcast: Podcast
    ) {

    override fun toString(): String {
        return titulo
    }
}