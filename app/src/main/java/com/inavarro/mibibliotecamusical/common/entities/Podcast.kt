package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class Podcast(
    val id: Long,
    val titulo: String,
    val imagen: String,
    val description: String,
    val anyo: Date,
    val usuario: User
) {

    override fun toString(): String {
        return titulo
    }
}
