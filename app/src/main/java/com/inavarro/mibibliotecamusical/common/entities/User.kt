package com.inavarro.mibibliotecamusical.common.entities

import java.util.Date

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val email: String,
    val gender: String,
    val birthday: Date,
    val country: String,
    val code: String
)
