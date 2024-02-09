package com.inavarro.mibibliotecamusical.authModule.services

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.User
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoEmail
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoUsername
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.LOGINBYUSENAME_PATH)
    suspend fun loginUserByUsername(@Body data: UserInfoUsername): Response<User>

    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.LOGINBYEMAIL_PATH)
    suspend fun loginUserByEmail(@Body data: UserInfoEmail): Response<User>

}