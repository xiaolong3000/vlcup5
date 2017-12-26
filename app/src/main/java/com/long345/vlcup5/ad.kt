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
import java.io.File
import java.net.URL

class ad : AppCompatActivity() {
    val handler= Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)

        var filepath = "" + Environment.getExternalStorageDirectory()
            Thread(Runnable { kotlin.run {

                val versiontxt = URL("http://${chaege.mainip}:8080/vlc/version.txt").readText()//http://192.168.31.190:8080/vlc/version.xml
                val regex = Regex("<version>([0-9]+)<version>")
                val result = regex.find(versiontxt)!!.value.replace("<version>", "")
                val version = result.toInt()

                val share_version = getSharedPreferences("myversion", Context.MODE_PRIVATE)
                val thisversion = share_version.getString("myversion", "0").toInt()

                if (thisversion == 0) {
                    share_version.edit().putString("myversion", "" + version).apply()
                } else if (version > thisversion) {
                    val ftp = f1(mainip, 21, "ls", "ls")
                    val login = ftp.ftpLogin()
                    println("here")
                    if (login){
                        val success=ftp.downfile("app.apk",filepath)
                        if (success){
                            val intent=Intent(Intent.ACTION_VIEW)
                            intent.setDataAndType(Uri.fromFile(File(filepath+"/app.apk")),"application/vnd.android.package-archive")
                            startActivity(intent)
                        }
                    }
                    ftp.ftpLogOut()



                }


            } }).start()



    }
}
