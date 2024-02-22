package com.inavarro.mibibliotecamusical.mainModule.songsFragment.service

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Song

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface SongsService {

    @GET(Constants.BASE_URL + Constants.PLAYLIST_PATH + "/{playlist_id}" + Constants.SONGS_PATH)
    suspend fun getSongs(@Path("playlist_id") idplaylist: Long): Response<MutableList<Song>>

    @GET(Constants.BASE_URL + Constants.PLAYLIST_PATH + "/{id}")
    suspend fun getPlaylist(@Path("id") idplaylist: Long): Response<Playlist>

    @DELETE(Constants.BASE_URL + Constants.PLAYLIST_PATH + "/{playlist_id}" + Constants.SONG_PATH + "/{song_id}" + Constants.USUARIO_PATH + "/{usuario_id}")
    suspend fun deleteSongPlaylist(@Path("usuario_id") iduser: Long, @Path("playlist_id") idPlaylist: Long, @Path("song_id") idSong: Long): Response<Playlist>
}