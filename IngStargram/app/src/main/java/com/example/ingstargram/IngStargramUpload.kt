package com.example.ingstargram

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_ing_stargram_upload.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class IngStargramUpload : AppCompatActivity() {

    lateinit var filePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ing_stargram_upload)

        homebutton.setOnClickListener {startActivity(Intent(this,IngStargramPostListActivity::class.java))}
        settingbutton.setOnClickListener {startActivity(Intent(this,IngStargramUserInfo::class.java))}
        mybutton.setOnClickListener {startActivity(Intent(this,IngStargramMyPostListActivity::class.java))}


        view_pictures.setOnClickListener {
            getPicture()
        }
        upload.setOnClickListener {
            uploadPost(this)
        }
    }

    fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000){
            val uri: Uri = data!!.data!!
            filePath = getImageFilePath(uri)
        }
    }
    fun getImageFilePath(contentUri: Uri): String{
        var columnIndex = 0
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null,null,null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }
    fun uploadPost(activity: Activity){
        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"),file)
        val part = MultipartBody.Part.createFormData("image", file.name, fileRequestBody)
        val content = RequestBody.create(MediaType.parse("text/plain"), getContent())

        (application as MasterApplication).service.uploadPost(
            part, content
        ).enqueue(object : Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {

                Toast.makeText(activity,"업로드 실패",Toast.LENGTH_LONG)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(response.isSuccessful){
                    val post = response.body()
                    Toast.makeText(activity,"업로드 성공",Toast.LENGTH_LONG)
                    finish()
                    mybutton.setOnClickListener {startActivity(Intent(this@IngStargramUpload,IngStargramMyPostListActivity::class.java))}

                }
            }
        })
    }
    fun getContent():String{
        return content_input.text.toString()
    }
}
