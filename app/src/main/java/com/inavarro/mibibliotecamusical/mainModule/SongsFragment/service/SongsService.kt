package com.inavarro.mibibliotecamusical.mainModule.libraryFragment.service

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LibraryService {

    @GET(Constants.BASE_URL + Constants.USUARIO_PATH + "/{id_user}" + Constants.PLAYLISTS_PATH)
    suspend fun getPlaylistUser(@Path("id_user") iduser: Long): Response<MutableList<Playlist>>
}