package com.inavarro.mibibliotecamusical.common.entities

import com.google.gson.annotations.SerializedName
import com.inavarro.mibibliotecamusical.common.Constants

class UserInfo (
    @SerializedName(Constants.EMAIL_PARAM) val email: String,
    @SerializedName(Constants.PASSWORD_PARAM) val password: String,
)