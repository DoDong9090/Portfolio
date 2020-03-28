package com.example.itube


import android.app.Application
import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {

    lateinit var service: RetrofitService
    override fun onCreate() {
        super.onCreate()
        createRetrofit()

    }

    fun createRetrofit() {
        val header = Interceptor {
            //Interceptor은 휴대폰으로부터 통신이 나갈때 통신을 가로챔
            val original = it.request()//그걸 original에 담아두고
            if (checkIsLogin()) {
                getUserToken()?.let { token ->
                    val requeset = original.newBuilder()//그걸 개조함(헤더를 붙이고)
                        .header("Authorization", "token " + token)
                        .build()
                    it.proceed(requeset)
                }
            } else {
                it.proceed(original)
            }
        }

        val client = OkHttpClient.Builder()//okhttpclient로 클라이언트를 만들고 거기에
            .addInterceptor(header)//인터셉터엔 위에서 만든 헤더를 붙여줌
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)//클라이언트는 위에서 만든 client변수로, 그리고 그 client변수안에는 위에서만든 header가 붙어있음
            .build() //빌드하게되면 위에거까지 다들어가게됨
        service = retrofit.create(RetrofitService::class.java)
    }

    //로그인을 했다면 헤더가 붙어야되고, 안했따면 헤더가 붙을 필요가 없음
    fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if (token != "null") return true
        else  return false
    }

    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        return if (token == "null") null
        else token
    }

}