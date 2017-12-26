package com.long345.vlcup5

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.widget.Switch
import com.long345.vlcup5.chaege.mainip
import java.net.URL

class ad : AppCompatActivity() {
    val handler= Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)


        val intent=Intent()
        intent.setClass(this,downloadapk::class.java)
        startService(intent)

    }
}
