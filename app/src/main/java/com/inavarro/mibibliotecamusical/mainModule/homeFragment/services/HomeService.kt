package com.inavarro.mibibliotecamusical.mainModule.homeFragment.services

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.common.entities.User
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoUsername
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeService {

    @GET(Constants.BASE_URL + Constants.USUARIO_PATH + "/{id_user}" + Constants.PLAYLISTS_PATH)
    suspend fun getPlaylistUser(@Path("id_user") iduser: Long): Response<MutableList<Playlist>>

    @GET(Constants.BASE_URL + Constants.USUARIO_PATH + "/{id_user}" + Constants.PODCAST_PATH)
    suspend fun getPodcastUser(@Path("id_user") iduser: Long): Response<MutableList<Podcast>>

    @GET(Constants.BASE_URL + Constants.USUARIO_PATH + "/{id_user}" + Constants.ALBUMS_PATH)
    suspend fun getAlbumstUser(@Path("id_user") iduser: Long): Response<MutableList<Album>>
}