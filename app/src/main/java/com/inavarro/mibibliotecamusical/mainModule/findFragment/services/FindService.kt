package com.inavarro.mibibliotecamusical.mainModule.findFragment.services

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.common.entities.Song
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FindService {

    @GET(Constants.BASE_URL + Constants.PLAYLISTS_PATH)
    suspend fun getPlaylists(): Response<MutableList<Playlist>>

    @GET(Constants.BASE_URL + Constants.PODCASTS_PATH)
    suspend fun getPodcasts(): Response<MutableList<Podcast>>

    @GET(Constants.BASE_URL + Constants.ALBUMS_PATH)
    suspend fun getAlbums(): Response<MutableList<Album>>

    @GET(Constants.BASE_URL + Constants.SONGS_PATH)
    suspend fun getSongs(): Response<MutableList<Song>>
}