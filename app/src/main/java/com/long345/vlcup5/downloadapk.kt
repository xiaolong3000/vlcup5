package com.long345.vlcup5

import android.app.DownloadManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.os.IBinder
import java.net.URL

class downloadapk : Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //  return super.onStartCommand(intent, flags, startId)
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
                val uri = Uri.parse("http://${chaege.mainip}:8080/vlc/app.apk")
                val request = DownloadManager.Request(uri)
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "app.apk")
                request.setMimeType("application/vnd.android.package-archive")
                request.setTitle("app.apk")
                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val downid = dm.enqueue(request)
                val receiver=downloadreceiver()
                registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))


//                val query = DownloadManager.Query().setFilterById(downid)
//                val cusor = dm.query(query)
//                try {
//                    if (cusor != null && cusor.moveToFirst()) {
//
//                        val status = cusor.getInt(cusor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
//                        println(DownloadManager.STATUS_FAILED)
//                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
//                            println("123")
//                        }
//                    }
//                } finally {
//                    cusor?.close()
//
//                }
            }

        } }).start()

        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
