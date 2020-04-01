package com.example.ingstargram

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_ing_stargram_upload.*


class IngStargramUpload : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ing_stargram_upload)

        view_pictures.setOnClickListener {
            getPicture()
        }
    }

    //    homebutton.setOnClickListener {startActivity(Intent(this,IngStargramPostListActivity::class.java))}
    //    settingbutton.setOnClickListener {startActivity(Intent(this,IngStargramUserInfo::class.java))}
    //    mybutton.setOnClickListener {startActivity(Intent(this,IngStargramMyPostListActivity::class.java))}
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
}
