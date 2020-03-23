package com.example.ingstargram

import android.app.Person
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService{
    @GET("json/students/")
    fun getStudentsList(): Call<ArrayList<PersonFromServer>>

    @POST("json/student/")
    fun createStudent(
        @Body params : HashMap<String, Any>
    ): Call<PersonFromServer>

    @POST("json/student/")
    fun createStudentEasy(
        @Body person : PersonFromServer
    ): Call<PersonFromServer>

    @POST("user/signup/")
    fun register(
        @Body register : Register
    ): Call<User>

}