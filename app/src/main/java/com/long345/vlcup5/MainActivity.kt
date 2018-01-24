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
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager


import com.long345.vlcup5.chaege.*
import java.net.URL


class MainActivity : AppCompatActivity() {


    var filepath = "" + Environment.getExternalStorageDirectory() + "/DCIM/Camera"
    val handler=Handler()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            Thread(runnable).start()
        }
        bumen_image.setOnClickListener {

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("").setItems(chaege.bumens3, DialogInterface.OnClickListener(
                    fun(d: DialogInterface, num: Int) {
                        val share = getSharedPreferences("bumen", Context.MODE_PRIVATE)
                        share.edit().putString("bumen", chaege.bumens3[num]).apply()
                        bumen.text = chaege.bumens3[num]
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
                        val size=list.size
                        list.forEach {
                            var success=false
                            if(it.contains("dthumb")){
                                success=true
                            }else{
                                val localfile = File(filepath + "/" + it)
                                success=ftp.uploadFile(localfile,chage_bumen(bumen.text.toString()), chage_banci(banci.text.toString()), renyuan.text.toString(), progressBar)
                            }
                                if (success){
                                    handler.post(Runnable { kotlin.run { files.text="共有 $size 个文件，上传 ${list.indexOf(it)} 个" } })
                                }
                        }
                        val builder=AlertDialog.Builder(this@MainActivity)
                        val string:CharSequence= "大吉大利"
                        builder.setTitle("全部上传完成").setPositiveButton(string,DialogInterface.OnClickListener(//Array<CharSequence>(1){string}
                                fun(_:DialogInterface,_:Int){
                                    this@MainActivity.finish()
                                }

                        )).show()
                    } else{
                        Toast.makeText(this@MainActivity, "连接服务器失败,请检查wifi及网络", Toast.LENGTH_LONG).show()
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

    override fun onDestroy() {
        super.onDestroy()

    }
}













