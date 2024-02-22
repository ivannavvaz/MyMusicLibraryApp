package com.inavarro.mibibliotecamusical.common

object Constants {
    // FIREBASE STORAGE
    const val STORAGE_URL = "gs://mibiblitecamusical.appspot.com"

    // RUTAS
    const val BASE_URL = "http://lab3.navelsystems.com"

    const val LOGINBYEMAIL_PATH = "/loginByEmail"
    const val LOGINBYUSENAME_PATH = "/loginByUsername"

    const val USUARIO_PATH = "/usuario"
    const val PLAYLISTS_PATH = "/playlists"
    const val PLAYLIST_PATH = "/playlist"
    const val PODCASTS_PATH = "/podcasts"
    const val PODCAST_PATH = "/podcast"
    const val ALBUMS_PATH = "/albums"
    const val SONGS_PATH = "/canciones"
    const val SINGIN_PATH = "/usuarios"
    const val VALIDATE_PATH = "/usuario/validar"
    const val SONG_PATH = "/cancion"
    const val CAPITULOS_PATH = "/capitulos"


    // RESULTADOS
    const val EMAIL_PARAM = "email"
    const val USERNAME_PARAM = "username"
    const val PASSWORD_PARAM = "password"
    const val GENERO_PARAM = "genero"
    const val PAIS_PARAM = "pais"
    const val CODIGO_POSTAL_PARAM = "codigoPostal"
    const val FECHA_NACIMIENTO_PARAM = "fechaNacimiento"
    const val TITULO_PARAM = "titulo"


    // DEFAULTS IMAGES
    const val DEFAULT_ALBUM_IMAGE = "https://pngimg.com/uploads/vinyl/vinyl_PNG92.png"
    const val DEFAULT_PLAYLIST_IMAGE = "https://cdn.icon-icons.com/icons2/3001/PNG/512/default_filetype_file_empty_document_icon_187718.png"
    const val DEFAULT_PODCAST_IMAGE = "https://cdn.icon-icons.com/icons2/3001/PNG/512/default_filetype_file_empty_document_icon_187718.png"
    const val DEFAULT_SONG_IMAGE = "https://cdn.icon-icons.com/icons2/3001/PNG/512/default_filetype_file_empty_document_icon_187718.png"
    const val FAVORITE_PLAYLIST_IMAGE = "https://misc.scdn.co/liked-songs/liked-songs-300.png"

    // EXTRAS

    const val API_REST_COUNTRIES = "https://restcountries.com/v3.1/all"
}