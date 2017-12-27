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
import java.util.*

class ad : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)

        var filepath = "" + Environment.getExternalStorageDirectory()
            Thread(Runnable { kotlin.run {
                val ftp = f1(mainip, 21, "ls", "ls")
                val login = ftp.ftpLogin()
                if (login){
              //  val versiontxt = URL("ftp://ls:ls@${chaege.mainip}/version.txt").readText()//http://192.168.31.190:8080/vlc/version.xml
                val su=ftp.downfile("version.txt",filepath+"/version.txt")
                    val versiontxt=File(filepath+"/version.txt").readText()
                val regex = Regex("<version>([0-9]+)<version>")
                val result = regex.find(versiontxt)!!.value.replace("<version>", "")
                val version = result.toInt()

                val share_version = getSharedPreferences("myversion", Context.MODE_PRIVATE)
                val thisversion = share_version.getString("myversion", "0").toInt()

                if (thisversion == 0) {
                    share_version.edit().putString("myversion", "" + version).apply()
                } else if (version > thisversion) {
                   // println("here")
                        val success=ftp.downfile("app.apk",filepath+"/app.apk")
                        if (success){
                            val intent=Intent(Intent.ACTION_VIEW)
                            intent.setDataAndType(Uri.fromFile(File(filepath+"/app.apk")),"application/vnd.android.package-archive")
                            share_version.edit().putString("myversion",version.toString()).apply()
                            startActivity(intent)
                        }
                    }else{
                    println("正常启动")
                    val timer=Timer()
                   timer.schedule(object :TimerTask(){
                       override fun run() {
                           val intent=Intent(this@ad,MainActivity::class.java)
                           startActivity(intent)
                       }
                   },2500)


                    }


                  }else{//网络没有的话立即跳转

                            val intent=Intent(this@ad,MainActivity::class.java)
                            startActivity(intent)

                }

                ftp.ftpLogOut()

            } }).start()



    }
}
