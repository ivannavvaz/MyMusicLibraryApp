package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class Playlist(
    val id: Long,
    val title: String,
    val songsNumber: Int,
    val creationDate: Date,
    val user: User,
    val followerUser: MutableList<User>
)
