package com.long345.vlcup5

import android.app.DownloadManager
import android.content.Context
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


        Thread(Runnable { kotlin.run {
                val versiontxt= URL("http://${mainip}:8080/vlc/version.txt").readText()//http://192.168.31.190:8080/vlc/version.xml
                val regex=Regex( "<version>([0-9]+)<version>")
                val result=regex.find(versiontxt)!!.value.replace("<version>","")
               val  version= result.toInt()

             val share_version = getSharedPreferences("myversion", Context.MODE_PRIVATE)

            val thisversion = share_version.getString("myversion", "0").toInt()
            if(thisversion==0){
           share_version.edit().putString("myversion",""+version).apply()
        }else if(version>thisversion){
              val uri=Uri.parse("http://${mainip}:8080/vlc/app.apk")
                val request=DownloadManager.Request(uri)
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "app.apk");
                request.setMimeType("application/vnd.android.package-archive")
                val dm=getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val downid=dm.enqueue(request)
                val query=DownloadManager.Query().setFilterById(downid)
                val cusor=dm.query(query)
                if (cusor!=null && cusor.moveToFirst()){
                    val status=cusor.getInt(cusor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                    if (status==DownloadManager.STATUS_SUCCESSFUL){
                        println("123")
                    }
                }
                if (cusor!=null)
                    cusor.close()



            }



        } }).start()
    }
}
