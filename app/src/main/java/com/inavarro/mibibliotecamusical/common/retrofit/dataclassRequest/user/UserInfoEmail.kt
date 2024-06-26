package com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user

import com.google.gson.annotations.SerializedName
import com.inavarro.mibibliotecamusical.common.Constants

class UserInfoEmail (
    @SerializedName(Constants.EMAIL_PARAM) val email: String,
    @SerializedName(Constants.PASSWORD_PARAM) val password: String,
)