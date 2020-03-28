package com.example.itube

import android.app.Activity
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_i_tube.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ITubeActivity : AppCompatActivity() {
    lateinit var glide : RequestManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_tube)

        (application as MasterApplication).service.getITubeList()
            .enqueue(object : Callback<ArrayList<ITube>>{
                override fun onFailure(call: Call<ArrayList<ITube>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<ArrayList<ITube>>,
                    response: Response<ArrayList<ITube>>
                ) {
                    if(response.isSuccessful){
                        glide = Glide.with(this@ITubeActivity)
                        val itubeList = response.body()
                        val adapter = ItubeAdapter(
                            itubeList!!,
                            LayoutInflater.from(this@ITubeActivity),
                            glide,
                            this@ITubeActivity
                        )
                        itube_list_recycler.adapter = adapter
                    }
                }
            })
    }
}
class ItubeAdapter(
    var itubeList : ArrayList<ITube>,
    val inflater : LayoutInflater,
    val glide: RequestManager,
    val activity: Activity
) : RecyclerView.Adapter<ItubeAdapter.ViewHolder>(){
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title: TextView
        val thumnail: ImageView
        val content: TextView
        init{
            title = itemView.findViewById(R.id.itube_title)
            thumnail = itemView.findViewById(R.id.itube_thumbnail)
            content = itemView.findViewById(R.id.itube_content)

            itemView.setOnClickListener{
                val position: Int = adapterPosition
                val intent = Intent(activity, Itube_detail::class.java)
                intent.putExtra("video_url",itubeList.get(position).video)
                intent.putExtra("video_title",itubeList.get(position).title)
                intent.putExtra("video_content",itubeList.get(position).content)
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.itube_itemview, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itubeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(itubeList.get(position).title)
        holder.content.setText(itubeList.get(position).content)
        glide.load(itubeList.get(position).thumbnail).into(holder.thumnail)
    }
}