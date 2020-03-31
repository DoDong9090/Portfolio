package com.example.ingstargram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ing_stargram_user_info.*

class IngStargramMyPostListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ing_stargram_my_post_list)
        homebutton.setOnClickListener {startActivity(Intent(this,IngStargramPostListActivity::class.java))}
        settingbutton.setOnClickListener {startActivity(Intent(this,IngStargramUserInfo::class.java))}
        //mybutton.setOnClickListener {startActivity(Intent(this,IngStargramMyPostListActivity::class.java))}
        uploadbutton.setOnClickListener {startActivity(Intent(this,IngStargramUpload::class.java))}
    }
}
