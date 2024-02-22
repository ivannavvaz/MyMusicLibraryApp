package com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters

import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.common.entities.Song

interface OnClickListener {
    fun onClick(songEntity: Song)
    fun onClick(playlist: Playlist)
    fun onClick(podcast: Podcast)
    fun onClick(album: Album)
}