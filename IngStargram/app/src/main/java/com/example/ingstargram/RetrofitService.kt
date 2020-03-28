package com.example.ingstargram

import android.app.Person
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService{

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username")username : String,
        @Field("password1")password1 : String,
        @Field("password2")password2: String
    ): Call<User>

    @GET("youtube/list/")
    fun getITubeList():Call<ArrayList<ITube>>

    @GET("melon/list/")
    fun getSongList():Call<ArrayList<Song>>
}