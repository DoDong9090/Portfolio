package com.example.itube

import android.app.Person
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService{


    @GET("youtube/list/")
    fun getITubeList():Call<ArrayList<ITube>>
}