package com.inavarro.mibibliotecamusical.mainModule.songsFragment.adapters

import com.inavarro.mibibliotecamusical.common.entities.Song

interface OnClickListener {
    fun onLongClick(songEntity: Song):Boolean
}