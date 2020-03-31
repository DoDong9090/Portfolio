package com.example.ingstargram

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ing_stargram_user_info.*

class IngStargramUserInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ing_stargram_user_info)
        homebutton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    IngStargramPostListActivity::class.java
                )
            )
        }
//        settingbutton.setOnClickListener {startActivity(Intent(this,IngStargramUserInfo::class.java))}
        mybutton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    IngStargramMyPostListActivity::class.java
                )
            )
        }
        uploadbutton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    IngStargramUpload::class.java
                )
            )
        }
        logout.setOnClickListener {
            val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("login_sp","null")
            editor.commit()
            (application as MasterApplication).createRetrofit()
            finish()
            startActivity(Intent(this, loginActivity::class.java))
        }
    }
}
