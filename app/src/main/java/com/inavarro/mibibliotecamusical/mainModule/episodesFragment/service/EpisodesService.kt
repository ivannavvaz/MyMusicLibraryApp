package com.inavarro.mibibliotecamusical.mainModule.episodesFragment.service

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Capitulo
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.common.entities.Song
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesService {

    @GET(Constants.BASE_URL + Constants.PODCAST_PATH + "/{id}")
    suspend fun getPodcast(@Path("id") idPodcast: Long): Response<Podcast>
    @GET(Constants.BASE_URL + Constants.PODCAST_PATH + "/{id}" + Constants.CAPITULOS_PATH)
    suspend fun getEpisodes(@Path("id") idPodcast: Long): Response<MutableList<Capitulo>>
}