package com.long345.vlcup5

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class downloadreceiver : BroadcastReceiver() {



  override fun onReceive(context: Context, intent: Intent) {
      val downid=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
      val manager=context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri=manager.getUriForDownloadedFile(downid)
        val intents=Intent()
        intents.action = "android.intent.action.VIEW"
        intents.addCategory("android.intent.category.DEFAULT")
        intents.type = "application/vnd.android.package-archive"
        intents.data = uri
        intents.setDataAndType(uri, "application/vnd.android.package-archive")
        intents.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intents)





    }
}
