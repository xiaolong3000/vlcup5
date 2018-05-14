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
import android.view.View
import android.widget.Switch
import com.long345.vlcup5.chaege.*
import kotlinx.android.synthetic.main.activity_ad.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

class ad : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)
        val bumen=chaege.nowbanben
        val handler=Handler()
        var version=0
        var update=false
        button2.visibility=View.INVISIBLE
        var filepath = "" + Environment.getExternalStorageDirectory()
        val mytxt=File(filepath+"/myversion.txt")
        if(mytxt.isDirectory){
            mytxt.delete()
        }
        if (!mytxt.exists()){
          mytxt.createNewFile()
            mytxt.writeText("0")
        }
            Thread(Runnable { kotlin.run {
                val ftp = f1(mainip, 21, "ls", "ls")
                val login = ftp.ftpLogin()
                if (login) {
                    ftp.downfile("version.txt", filepath + "/version.txt")
                    ftp.downfile("name.txt",filepath+"/name.txt")
                    val versiontxt = File(filepath + "/version.txt").readText()
                  //  val names=File(filepath + "/name.txt").readLines()
                    val names=BufferedReader(InputStreamReader(FileInputStream(filepath + "/name.txt"),"utf-8")).readLines()
                    names.forEach {

                        map_name.put(it.split("/")[0],it.split("/")[1])

                    }


                    val regex = Regex("<$bumen>([0-9]+)<$bumen>")
                    val result = regex.find(versiontxt)!!.value.replace("<$bumen>", "")
                    version = result.toInt()

//                val share_version = getSharedPreferences("myversion", Context.MODE_PRIVATE)
//                val thisversion = share_version.getString("myversion", "0").toInt()
                    val thisversion = mytxt.readText().toInt()
                    handler.post(Runnable {
                        kotlin.run {

                            textview2.text = "服务器ip:$mainip,部门:$bumen,版本号:$version,本机版本号:$thisversion"

                        }
                    })

               if (version > thisversion) {
                        val success=ftp.downfile("$bumen.apk", "$filepath/$bumen.apk")
                        if (success){
//                            val intent=Intent(Intent.ACTION_VIEW)
//                            intent.setDataAndType(Uri.fromFile(File("$filepath/$bumen.apk")),"application/vnd.android.package-archive")
//                         //   share_version.edit().putString("myversion",version.toString()).apply()
//                            mytxt.writeText("$version")
//                            startActivity(intent)
                            handler.post(Runnable {
                                kotlin.run {

                                    textview2.text = "有新版本可以更新"
                                    update=true

                                }
                            })
                        }
                    }
//               else{
//                    val timer=Timer()
//                   timer.schedule(object :TimerTask(){
//                       override fun run() {
//                           val intent=Intent(this@ad,MainActivity::class.java)
//                           startActivity(intent)
//                           finish()
//                       }
//                   },1000)
//                  }
                  }else{//网络没有的话立即跳转
                    textview2.text="网络无法联通，请检查网络"
                }
                    ftp.ftpLogOut()
                }
             }).start()

          if (update){
              button2.visibility= View.VISIBLE
              button2.setOnClickListener {
                  val intent=Intent(Intent.ACTION_VIEW)
                  intent.setDataAndType(Uri.fromFile(File("$filepath/$bumen.apk")),"application/vnd.android.package-archive")
                  //   share_version.edit().putString("myversion",version.toString()).apply()
                  mytxt.writeText("$version")
                  startActivity(intent)

              }
          }





        button3.setOnClickListener {
            val intent=Intent(this@ad,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
