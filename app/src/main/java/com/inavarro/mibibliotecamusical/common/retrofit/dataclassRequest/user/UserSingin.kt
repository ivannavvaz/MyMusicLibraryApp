package com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user

import com.google.gson.annotations.SerializedName
import com.inavarro.mibibliotecamusical.common.Constants
import java.util.Date

class UserSingin (
    @SerializedName(Constants.USERNAME_PARAM) var username: String,
    @SerializedName(Constants.PASSWORD_PARAM) var password: String,
    @SerializedName(Constants.EMAIL_PARAM) var email: String,
    @SerializedName(Constants.GENERO_PARAM) var genero: String,
    @SerializedName(Constants.FECHA_NACIMIENTO_PARAM) var fechaNacimiento: String,
    @SerializedName(Constants.PAIS_PARAM) var pais: String,
    @SerializedName(Constants.CODIGO_POSTAL_PARAM) var codigoPostal: String,
)