package com.inavarro.mibibliotecamusical.common.entities

import com.google.gson.annotations.SerializedName
import com.inavarro.mibibliotecamusical.common.Constants

class UserInfoUsername (
    @SerializedName(Constants.USERNAME_PARAM) val username: String,
    @SerializedName(Constants.PASSWORD_PARAM) val password: String,
)