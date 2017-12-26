package com.long345.vlcup5

import android.annotation.SuppressLint

import android.app.AlertDialog

import android.content.Context

import android.os.*
import android.support.v7.app.AppCompatActivity


import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*




import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.content.DialogInterface
import android.content.Intent
import android.net.wifi.WifiManager


import com.long345.vlcup5.chaege.*
import java.net.URL


class MainActivity : AppCompatActivity() {


    var filepath = "" + Environment.getExternalStorageDirectory() + "/DCIM/Camera"
    val handler=Handler()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifi.disconnect()) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("").setMessage("wifi没有连接").setPositiveButton("OK", DialogInterface.OnClickListener(
                    fun(dia: DialogInterface, num: Int) {
                        this@MainActivity.finish()
                    }))
        }
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            Thread(runnable).start()
        }
        bumen_image.setOnClickListener {

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("").setItems(chaege.bumens, DialogInterface.OnClickListener(
                    fun(d: DialogInterface, num: Int) {
                        val share = getSharedPreferences("bumen", Context.MODE_PRIVATE)
                        share.edit().putString("bumen", chaege.bumens[num]).apply()
                        bumen.text = chaege.bumens[num]
                    })
            )
            builder.show()
        }

        textView4.text = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val share = getSharedPreferences("bumen", Context.MODE_PRIVATE)
        bumen.text = share.getString("bumen", "未设置")

        banci_image.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("").setItems(chaege.bancis, DialogInterface.OnClickListener(
                    fun(d: DialogInterface, num: Int) {
                        val share = getSharedPreferences("banci", Context.MODE_PRIVATE)
                        share.edit().putString("banci", chaege.bancis[num]).apply()
                        banci.text = chaege.bancis[num]
                    }
            ))
            builder.show()
        }
        val share3=getSharedPreferences("banci",Context.MODE_PRIVATE)
        banci.text=share3.getString("banci","未设置")

    }//oncreate


//      var runable2= Runnable {//测试用
//          kotlin.run {
//              if (filename.contains("dthumb"))//这种方式可以找到这个隐藏文件夹
//              {
//                  handler.post(Runnable { kotlin.run { files.text="123" } })
//
//              }
//          }
//      }
    var runnable = Runnable {
        kotlin.run {
            if (bumen.text!="未设置" &&  renyuan.text.toString()!="" && banci.text!="未设置") {

                    Looper.prepare()
                    val ftp = f1(mainip, 21, "ls", "ls")
                    val login = ftp.ftpLogin()
                    if (login) {
                        val list = File(filepath).list()
                        val size=list.size-1
                        list.forEach {
                            if (!it.contains("dthumb")){
                                val localfile = File(filepath + "/" + it)
                                val success=ftp.uploadFile(localfile,chage_bumen(bumen.text.toString()), chage_banci(banci.text.toString()), renyuan.text.toString(), progressBar)
                                if (success){
                                    handler.post(Runnable { kotlin.run { files.text="共有 $size 个文件，上传 ${list.indexOf(it)} 个" } })
                                }
                            }
                        }
                    } else{
                        Toast.makeText(this@MainActivity, "连接服务器失败", Toast.LENGTH_LONG).show()
                    }
                ftp.ftpLogOut()
                Looper.loop()
            } else {
                Looper.prepare()
                Toast.makeText(this@MainActivity, "岗点班次及人员均要设置", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }
    }

}













