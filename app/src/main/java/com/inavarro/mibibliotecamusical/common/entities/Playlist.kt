package com.inavarro.mibibliotecamusical.common.entities

import com.inavarro.mibibliotecamusical.common.Constants
import java.util.Date

data class Playlist(
    val id: Long,
    val titulo: String,
    val numeroCanciones: Int,
    val fechaCreacion: Date,
    val usuario: User,
    val usuarioSeguidor: MutableList<User>
) {

    override fun toString(): String {

        var tituloModificado = ""

        if (titulo != "favorita_1") {
            tituloModificado = titulo.replace("lista_", "")
            tituloModificado = tituloModificado.replace("_", " ")
            tituloModificado = tituloModificado[0].uppercase() + tituloModificado.substring(1)
        } else {
            tituloModificado = "Canciones que te gustan"
        }

        return tituloModificado
    }
}
