package com.inavarro.mibibliotecamusical.mainModule.newPlaylistFragment.service

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.playlist.PlaylistInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface NewPlaylistService {

    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.USUARIO_PATH + "/{usuario_id}" + Constants.PLAYLISTS_PATH)
    suspend fun createPlaylist(@Path("usuario_id") iduser: Long, @Body data: PlaylistInfo): Response<Playlist>
}