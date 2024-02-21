package com.inavarro.mibibliotecamusical.mainModule.homeFragment.services

import com.google.gson.JsonArray
import com.inavarro.mibibliotecamusical.authModule.singinFragment.Entities.Country
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.User
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfo
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserSingin
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface SinginService {

    @GET(Constants.API_REST_COUNTRIES)
    suspend fun getCountries(): Response<MutableList<Country>>

    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.SINGIN_PATH)
    suspend fun singinUser(@Body data: UserSingin): Response<User>

    @Headers("Content-Type: application/json")
    @POST(Constants.BASE_URL + Constants.VALIDATE_PATH)
    fun validateUser(@Body data: UserInfo): Response<User>

}