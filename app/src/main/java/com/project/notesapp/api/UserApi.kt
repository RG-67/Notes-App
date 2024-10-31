package com.project.notesapp.api

import com.project.notesapp.model.userRequestModel.UserLoginRequest
import com.project.notesapp.model.userRequestModel.UserRegisterRequest
import com.project.notesapp.model.userResponseModel.UserLoginResponse
import com.project.notesapp.model.userResponseModel.UserRegisterResponse
import com.project.notesapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST(Constants.CREATE_USER)
    suspend fun createUser(@Body userRequest: UserRegisterRequest): Response<UserRegisterResponse>

    @POST(Constants.LOGIN_USER)
    suspend fun loginUser(@Body userLoginRequest: UserLoginRequest): Response<UserLoginResponse>

}