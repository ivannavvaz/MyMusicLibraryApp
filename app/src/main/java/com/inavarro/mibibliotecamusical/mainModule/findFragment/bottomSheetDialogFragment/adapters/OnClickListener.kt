package com.inavarro.mibibliotecamusical.mainModule.findFragment.bottomSheetDialogFragment.adapters

import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Song

interface OnClickListener {
    fun onClick(playlistEntity: Playlist)
}