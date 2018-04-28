package com.long345.vlcup5

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.widget.Switch
import com.long345.vlcup5.chaege.mainip
import kotlinx.android.synthetic.main.activity_ad.*
import java.io.File
import java.net.URL
import java.util.*

class ad : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)
        val bumen=chaege.nowbanben

        var filepath = "" + Environment.getExternalStorageDirectory()
            Thread(Runnable { kotlin.run {
                val ftp = f1(mainip, 21, "ls", "ls")
                val login = ftp.ftpLogin()
                if (login){


              //  val versiontxt = URL("ftp://ls:ls@${chaege.mainip}/version.txt").readText()//http://192.168.31.190:8080/vlc/version.xml
                val su=ftp.downfile("version.txt",filepath+"/version.txt")
                val versiontxt=File(filepath+"/version.txt").readText()
                val regex = Regex("<$bumen>([0-9]+)<$bumen>")
                val result = regex.find(versiontxt)!!.value.replace("<$bumen>", "")
                val version = result.toInt()
                    val mytxt=File(filepath+"/myversion.txt")
                    if (!mytxt.exists()){
                        mytxt.mkdir()
                       mytxt.writeText("0")
                    }

//                val share_version = getSharedPreferences("myversion", Context.MODE_PRIVATE)
//                val thisversion = share_version.getString("myversion", "0").toInt()
                val thisversion=mytxt.readText().toInt()
                textview2.text="服务器ip:$mainip,部门:$bumen,版本号:$version,本机版本号:$thisversion"

               if (version > thisversion) {
                   // println("here")
                        val success=ftp.downfile("$bumen.apk", "$filepath/$bumen.apk")
                        if (success){
                            val intent=Intent(Intent.ACTION_VIEW)
                            intent.setDataAndType(Uri.fromFile(File("$filepath/$bumen.apk")),"application/vnd.android.package-archive")
                         //   share_version.edit().putString("myversion",version.toString()).apply()
                            mytxt.writeText("$version")
                            startActivity(intent)
                        }
                    }else{
//                    println("正常启动")
                    val timer=Timer()
                   timer.schedule(object :TimerTask(){
                       override fun run() {
                           val intent=Intent(this@ad,MainActivity::class.java)
                           startActivity(intent)
                           finish()
                       }
                   },1000)
                    }
                  }else{//网络没有的话立即跳转

//                            val intent=Intent(this@ad,MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
                    textview2.text="网络无法联通，请检查网络"
                }


                ftp.ftpLogOut()

            } }).start()

//        button2.setOnClickListener {
//            val success=ftp.downfile("$bumen.apk", "$filepath/$bumen.apk")
//            if (success){
//                val intent=Intent(Intent.ACTION_VIEW)
//                intent.setDataAndType(Uri.fromFile(File("$filepath/$bumen.apk")),"application/vnd.android.package-archive")
//                mytxt.writeText("$version")
//                startActivity(intent)
//            }
//        }
//        button3.setOnClickListener {
//            val intent=Intent(this@ad,MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

    }
}
