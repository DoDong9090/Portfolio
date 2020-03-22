package com.example.ingstargram

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class Email_Signup_Activity : AppCompatActivity() {

    lateinit var usernameView : EditText
    lateinit var userPassword1View : EditText
    lateinit var userPassword2View : EditText
    lateinit var register : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email__signup_)

        initView(this@Email_Signup_Activity)

        }


    fun setupListener(){
        register

    }
    fun initView(activity : Activity){
        usernameView = activity.findViewById(R.id.username_inpubox)
        userPassword1View = activity.findViewById(R.id.password1_inpubox)
        userPassword2View = activity.findViewById(R.id.password2_inpubox)
        register = activity.findViewById(R.id.register)
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
