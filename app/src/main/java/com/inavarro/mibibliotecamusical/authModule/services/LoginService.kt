package com.inavarro.mibibliotecamusical.authModule.services

import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.UserInfoEmail
import com.inavarro.mibibliotecamusical.common.retrofit.login.LoginResponse
import com.inavarro.mibibliotecamusical.common.entities.UserInfoUsername
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.USER_PATH)
    suspend fun loginUserByUsername(@Body data: UserInfoUsername): LoginResponse

    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.USER_PATH)
    suspend fun loginUserByEmail(@Body data: UserInfoEmail): LoginResponse

}