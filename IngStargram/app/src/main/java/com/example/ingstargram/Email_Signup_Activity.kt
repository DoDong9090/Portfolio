package com.example.ingstargram

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Email_Signup_Activity : AppCompatActivity() {

    lateinit var usernameView : EditText
    lateinit var userPassword1View : EditText
    lateinit var userPassword2View : EditText
    lateinit var registerBtn : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email__signup_)

        initView(this@Email_Signup_Activity)
        setupListener()

        }


    fun setupListener(){
        registerBtn.setOnClickListener {
            register(this@Email_Signup_Activity)
        }
    }
    fun register(activity: Activity){
        val username = usernameView.text.toString()
        val password1 = userPassword1View.text.toString()
        val password2 = userPassword2View.text.toString()
        val register = Register(username,password1,password2)

        (application as MasterApplication).service.register(register).enqueue(object:
            Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(activity, "가입에 실패하였습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    Toast.makeText(activity, "가입에 성공하였습니다.", Toast.LENGTH_LONG).show()
                    val user = response.body()
                    val token = user!!.token
                    saverUserToken(token!!, activity)
                }

            }

        })
    }
    fun saverUserToken(token: String, activity: Activity){
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp",token)
        editor.commit()
    }
    fun initView(activity : Activity){
        usernameView = activity.findViewById(R.id.username_inpubox)
        userPassword1View = activity.findViewById(R.id.password1_inpubox)
        userPassword2View = activity.findViewById(R.id.password2_inpubox)
        registerBtn = activity.findViewById(R.id.register)
    }
    fun getUserName():String{
        return usernameView.text.toString()

    }
    fun getUserPassword1():String{
        return userPassword1View.text.toString()

    }
    fun getUserPassword2():String{
        return userPassword2View.text.toString()

    }
}
