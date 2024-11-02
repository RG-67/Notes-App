package com.project.notesapp.utils

object Constants {

    const val BASE_URL = "http://192.168.0.103:5000/api/v1/"

    /* user api endpoint */
    const val CREATE_USER = "user/createUser"
    const val LOGIN_USER = "user/loginUser"

    /* note api endpoint */
    const val CREATE_NOTE = "note/createNote"
    const val READ_NOTE = "note/getNote"
    const val UPDATE_NOTE = "note/updateNote"
    const val DELETE_NOTE = "note/deleteNote"

}