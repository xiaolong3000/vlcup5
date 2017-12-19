package com.long345.vlcup5

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.widget.Button

import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*




import java.io.File

import java.text.SimpleDateFormat
import java.util.*
import android.content.DialogInterface
import android.net.wifi.WifiManager



class MainActivity : AppCompatActivity() {

    var filename = ""
    var filepath = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var wifi= applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifi.disconnect()){
            var builder=AlertDialog.Builder(this@MainActivity)
            builder.setTitle("").setMessage("wifi没有连接").setPositiveButton("OK",DialogInterface.OnClickListener(
                    fun(dia:DialogInterface,num:Int) {
                this@MainActivity.finish()

            }))
        }
        setContentView(R.layout.activity_main)






        button2.setOnClickListener {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent, 1)
            val builder=AlertDialog.Builder(this@MainActivity)
            val path="" + Environment.getExternalStorageDirectory() + "/DCIM/Camera"
            val allfile=File(path)
            val list=allfile.list()
        //    list.filter { it.contains(".") }
            builder.setTitle("选择上传文件").setItems(list,DialogInterface.OnClickListener(
                    fun (_,num:Int){
                       // println(allfile.list().get(num))

                        filename=allfile.list().get(num)
                        filepath = "" + Environment.getExternalStorageDirectory() + "/DCIM/Camera"
                    }
            )).show()
        }





        button.setOnClickListener {
            Thread(runnable).start()

        }


wenhao.setOnClickListener {
    var builder=AlertDialog.Builder(this@MainActivity)
    builder.setTitle("").setMessage("点击上传没有反应，无任何提示，进度条不动。\r\n第一请检查视频是否存储在SD卡上!\r\n第二请检查是否选错了上传部门").show()
}

        imageButton.setOnClickListener{

           var builder=AlertDialog.Builder(this@MainActivity)
           builder.setTitle("").setItems(chaege.bumens, DialogInterface.OnClickListener(
                    fun(d:DialogInterface, num:Int) {
                       var share=getSharedPreferences("bumen",Context.MODE_PRIVATE)
                        share.edit().putString("bumen",chaege.bumens[num]).apply()
                        bumen.text=chaege.bumens[num]

                    })
            )

            builder.show()

        }
        textView4.text = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var share=getSharedPreferences("bumen", Context.MODE_PRIVATE)
        bumen.text=share.getString("bumen","未设置")

        imageButton5.setOnClickListener {
            val builder=AlertDialog.Builder(this@MainActivity)
            builder.setTitle("").setItems(chaege.renyuans,DialogInterface.OnClickListener(
                    fun (d:DialogInterface,num:Int){
                        val share=getSharedPreferences("renyuan",Context.MODE_PRIVATE)
                        share.edit().putString("renyuan",chaege.renyuans[num]).apply()
                        renyuan.text=chaege.renyuans[num]
                    }
            ))
            builder.show()
        }
        var share2=getSharedPreferences("renyuan",Context.MODE_PRIVATE)
        renyuan.text=share2.getString("renyuan","未设置")


    }

    var runnable = Runnable {
        kotlin.run {
          if (!bumen.equals("未设置")&&!renyuan.equals("未设置")) {


              if(filename!="") {
                  Looper.prepare()
                  val ftp = f1(chaege.c(bumen.text.toString()), 21, "ls", "ls")
                  val login= ftp.ftpLogin()
                  if (login) {
                      val localfile=File(filepath + "/" + filename)
                         if(localfile.exists()){
                       val   success = ftp.uploadFile(localfile, renyuan.text.toString(), progressBar)
                         if (success) {

                             filename=""//重置
                             Toast.makeText(this@MainActivity, "上传成功", Toast.LENGTH_LONG).show()

                         }
                     }else{
                         Toast.makeText(this@MainActivity, "选择的文件已经被删除", Toast.LENGTH_LONG).show()
                     }


                      ftp.ftpLogOut()

                  }else{
                      Toast.makeText(this@MainActivity, "连接服务器失败", Toast.LENGTH_LONG).show()
                  }
                  Looper.loop()

              }else{
                  Looper.prepare()
                  Toast.makeText(this@MainActivity,"选择文件为空,请选择文件",Toast.LENGTH_LONG).show()
                  Looper.loop()
              }
           }else{
              Looper.prepare()
              Toast.makeText(this@MainActivity,"部门及人员均要设置",Toast.LENGTH_LONG).show()
              Looper.loop()
          }
//            Looper.prepare()
//            if (ftplogin("192.168.31.190","ls","ls")){
//                Toast.makeText(this@MainActivity, "连接成功", Toast.LENGTH_LONG).show()
//                uploadfile(File(filepath + "/" + filename), "")
//
//                ftplogout()
//            }
//
//
//
//
//
//            Looper.loop()

        }
    }




//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1) {
//            if (resultCode == Activity.RESULT_OK) {
//                var uri = data?.data
//                var cosur = contentResolver.query(uri, null, null, null, null)
//                cosur.moveToFirst()
//                var v_name = cosur.getString(2)
//
//                filename = v_name
//
//
//
//            }
//        }
//    }

//    fun ftplogin(ip: String, ftpuser: String, ftppass: String): Boolean {
//        var isLogin = false
//        var ftpconfig = FTPClientConfig()
//
//        ftpconfig.serverTimeZoneId = TimeZone.getDefault().id
//        ftpclient.controlEncoding = "utf-8"
//        ftpclient.configure(ftpconfig)
//
//        ftpclient.connect(ip,21)
//        var reply = ftpclient.replyCode
//        if (!FTPReply.isPositiveCompletion(reply)) {
//            ftpclient.disconnect()
//        //    print("??????????????????????????")
//            return isLogin
//        }
//
//       ftpclient.login(ftpuser, ftppass)
//
//        ftpclient.enterLocalPassiveMode()
//        ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE)
//        isLogin = true
//
//
//        return isLogin
//    }

//    fun ftplogout() {
//        try {
//            if (null != ftpclient && ftpclient.isConnected) {
//                ftpclient.logout()
//              //  Toast.makeText(this@MainActivity,"断开连接",Toast.LENGTH_SHORT)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            ftpclient.disconnect()
//        }
//
//    }

//    fun uploadfile(localfile: File, remotepath: String) {
//
//        var startTime = System.currentTimeMillis()
//        var instream = BufferedInputStream(FileInputStream(localfile))
//        try {
//
//
//            ftpclient.changeWorkingDirectory(remotepath)
//            ftpclient.copyStreamListener = FTPProcess(this@MainActivity,startTime)
//             ftpclient.storeFile(localfile.name, instream)
//
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            instream.close()
//        }
//
//    }
}







