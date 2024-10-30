package com.project.notesapp.model.userRequestModel

data class UserRequest(
    val emailId: String,
    val name: String,
    val password: String,
    val phoneNumber: Long
)