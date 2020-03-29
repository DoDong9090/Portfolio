package com.example.ingstargram

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_belon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class BelonActivity : AppCompatActivity() {


    var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_belon)
        (application as MasterApplication).service.getSongList().enqueue(
            object : Callback<ArrayList<Song>>{
                override fun onFailure(call: Call<ArrayList<Song>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<ArrayList<Song>>,
                    response: Response<ArrayList<Song>>
                ) {
                    if(response.isSuccessful) {
                        val songList = response.body()
                        val adapter = BelonAdapter(
                            songList!!,
                            LayoutInflater.from(this@BelonActivity),
                            Glide.with(this@BelonActivity),
                            this@BelonActivity
                        )
                        song_list.adapter = adapter
                    }

                }
            }
        )
    }

    override fun onPause() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onPause()
    }

    inner class BelonAdapter(
        var songList : ArrayList<Song>,
        val inflater : LayoutInflater,
        val glide: RequestManager,
        val activity: Activity
    ) : RecyclerView.Adapter<BelonAdapter.ViewHolder>(){
        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            val title: TextView
            val thumnail: ImageView
            val play: ImageView
            init{
                title = itemView.findViewById(R.id.song_title)
                thumnail = itemView.findViewById(R.id.song_img)
                play = itemView.findViewById(R.id.song_play)

                play.setOnClickListener{
                    val position: Int = adapterPosition
                    val path = songList.get(position).song

                    try{
                        mediaPlayer?.stop()
                        mediaPlayer?.release()//영샹이나 mp3재생할때 리고스를 많이 붙잡게되는데 이걸 놔주는 용도
                        mediaPlayer = null
                        mediaPlayer = MediaPlayer.create(
                            this@BelonActivity,
                            Uri.parse(path)
                        )

                        mediaPlayer?.start()
                    }catch(e:Exception){

                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.song_item_view, parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return songList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.setText(songList.get(position).title)
            glide.load(songList.get(position).thumbnail).into(holder.thumnail)
        }
    }
}

