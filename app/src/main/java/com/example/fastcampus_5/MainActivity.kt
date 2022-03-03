package com.example.fastcampus_5

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val photoAddButton : Button by lazy {
        findViewById(R.id.photoAddButton)
    }

    private val photoFrameStartButton : Button by lazy {
        findViewById(R.id.photoFrameStartButton)
    }

    private val imageViewList : List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(findViewById(R.id.imageView11))
            add(findViewById(R.id.imageView12))
            add(findViewById(R.id.imageView13))
            add(findViewById(R.id.imageView21))
            add(findViewById(R.id.imageView22))
            add(findViewById(R.id.imageView23))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPhotoAddButton()
        initPhotoFrameStartButton()
    }

    private fun initPhotoAddButton(){
        photoAddButton.setOnClickListener {
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //TODO 권한이 있을 때 사진을 추가하는 기능
                    getPhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionPopUP()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1000 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //todo 권한이 부여가 된 것
                    getPhotos()
                } else {
                    Toast.makeText(this, "권한이 거부되어 있습니다.",Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                //다른 요청은 없으므로 구현 x
            }
        }
    }

    private fun getPhotos() {
        val intent= Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2000) //선택된 컨텐츠가 넘어와야 하기 때문
    }

    private fun showPermissionPopUP() {
        AlertDialog.Builder(this)
            .setTitle("권한 요청 안내")
            .setMessage("사진을 추가하기 위해선 권한이 필요합니다")
            .setPositiveButton("권한추가") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소",null)
            .create()
            .show()
    }

    private fun initPhotoFrameStartButton(){

    }

}