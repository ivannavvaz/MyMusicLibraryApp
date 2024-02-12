package com.inavarro.mibibliotecamusical.mainModule.homeFragment

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.common.entities.User
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoUsername
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface HomeService {
    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.USUARIO_PATH + Constants.PLAYLISTS_PATH)
    suspend fun getPlaylistUser(): Response<MutableList<Playlist>>

    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.USUARIO_PATH + Constants.PLAYLISTS_PATH)
    suspend fun getPodcastUser(): Response<MutableList<Podcast>>

    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.USUARIO_PATH + Constants.PLAYLISTS_PATH)
    suspend fun getAlbumstUser(): Response<MutableList<Album>>
}