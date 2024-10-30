package com.project.notesapp.api

import com.project.notesapp.model.userRequestModel.UserRequest
import com.project.notesapp.model.userResponseModel.UserResponse
import com.project.notesapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST(Constants.CREATE_USER)
    suspend fun createUser(@Body userRequest: UserRequest): Response<UserResponse>

}