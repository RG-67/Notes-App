package com.project.notesapp.model.userRequestModel

data class UserRegisterRequest(
    val emailId: String,
    val name: String,
    val password: String,
    val phoneNumber: Long
)