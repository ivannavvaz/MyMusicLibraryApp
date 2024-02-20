package com.inavarro.mibibliotecamusical.mainModule.findFragment.bottomSheetDialogFragment.services

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import com.inavarro.mibibliotecamusical.common.Constants
import retrofit2.http.POST
import retrofit2.http.Path

interface BottomSheetDialogService {

    @POST(Constants.BASE_URL + Constants.PLAYLIST_PATH + "/{id_playlist}" + Constants.SONG_PATH + "/{id_song}" + Constants.USUARIO_PATH + "/{id_user}")
    suspend fun postPlaylistUser(@Path("id_playlist") idPlaylist: Long, @Path("id_song") idSong: Long, @Path("id_user") idUser: Long)
}