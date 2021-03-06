package com.example.ingstargram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_ingstagram_post_list.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.zip.Inflater

class IngStargramPostListActivity : AppCompatActivity() {

    lateinit var glide : RequestManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingstagram_post_list)

        glide = Glide.with(this)

        (application as MasterApplication).service.getAllPosts().enqueue(
            object : Callback<ArrayList<Post>>{
                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if(response.isSuccessful){
                        val postList = response.body()
                        val adatper = PostAdatper(
                            postList!!,
                            LayoutInflater.from(this@IngStargramPostListActivity),
                            glide
                        )
                        post_recyclerview.adapter = adatper
                        post_recyclerview.layoutManager = LinearLayoutManager(this@IngStargramPostListActivity)



                    }

                }
            }
        )
        //homebutton.setOnClickListener {startActivity(Intent(this,IngStargramPostListActivity::class.java))}
        settingbutton.setOnClickListener {startActivity(Intent(this,IngStargramUserInfo::class.java))}
        mybutton.setOnClickListener {startActivity(Intent(this,IngStargramMyPostListActivity::class.java))}
        uploadbutton.setOnClickListener {startActivity(Intent(this,IngStargramUpload::class.java))}



    }
}
class PostAdatper(
    var postList : ArrayList<Post>,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<PostAdatper.viewHolder>(){
    inner class viewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val postOnwer : TextView
        val postTopOnwer : TextView
        val postImage : ImageView
        val postContent : TextView
        init{
            postOnwer = itemView.findViewById(R.id.post_onwer)
            postTopOnwer = itemView.findViewById(R.id.post_topOnwer)
            postImage = itemView.findViewById(R.id.post_img)
            postContent = itemView.findViewById(R.id.post_content)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = inflater.inflate(R.layout.ingstargram_item_view, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.postOnwer.setText(postList.get(position).owner)
        holder.postTopOnwer.setText(postList.get(position).owner)
        holder.postContent.setText(postList.get(position).content)
        glide.load(postList.get(position).image).into(holder.postImage)


    }
}