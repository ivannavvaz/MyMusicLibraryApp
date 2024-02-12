package com.inavarro.mibibliotecamusical.mainModule.homeFragment.services

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET(Constants.BASE_URL + Constants.USUARIO_PATH + Constants.PLAYLISTS_PATH)
    suspend fun getPlaylistUser(): Response<MutableList<Playlist>>

    @GET(Constants.BASE_URL + Constants.USUARIO_PATH + Constants.PODCAST_PATH)
    suspend fun getPodcastUser(): Response<MutableList<Podcast>>

    @GET(Constants.BASE_URL + Constants.USUARIO_PATH + Constants.ALBUMS_PATH)
    suspend fun getAlbumstUser(): Response<MutableList<Album>>
}