package com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters

import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast

interface OnClickListener {
    fun onClick(albumEntity: Album)
    fun onClick(playlistEntity: Playlist)
    fun onClick(podcastEntity: Podcast)
}