package com.example.ingstargram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_itube_detail.*

class Itube_detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itube_detail)

        val title = intent.getStringExtra("video_title")
        Log.d("titlez",title)
        title2.setText(title)
        val content = intent.getStringExtra("video_content")
        content2.setText(content)






        val url = intent.getStringExtra("video_url")
        val mediaController = MediaController(this@Itube_detail)
        video_view.setVideoPath(url)
        video_view.requestFocus()
        video_view.setMediaController(mediaController)
        video_view.start()
        mediaController.show()

    }
}
