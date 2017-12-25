package com.long345.vlcup5

import android.annotation.SuppressLint
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
import android.graphics.drawable.Drawable
import android.net.wifi.WifiManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.long345.vlcup5.R.id.files
import java.text.CharacterIterator
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var filename = ""
    var filepath = ""
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






        button2.setOnClickListener {
            //            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent, 1)
            val builder = AlertDialog.Builder(this@MainActivity)
            val path = "" + Environment.getExternalStorageDirectory() + "/DCIM/Camera"
            val allfile = File(path)
            val list = allfile.list()








            //    list.filter { it.contains(".") }

            builder.setTitle("选择上传文件").setItems(list, DialogInterface.OnClickListener(
                    fun(_, num: Int) {
                        // println(allfile.list().get(num))

                        filename = list.get(num)
                        filepath = "" + Environment.getExternalStorageDirectory() + "/DCIM/Camera"
                    }
            )).show()
        }





        button.setOnClickListener {

            Thread(runable2).start()


        }




        imageButton.setOnClickListener {

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

        imageButton5.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("").setItems(chaege.renyuans, DialogInterface.OnClickListener(
                    fun(d: DialogInterface, num: Int) {
                        val share = getSharedPreferences("renyuan", Context.MODE_PRIVATE)
                        share.edit().putString("renyuan", chaege.renyuans[num]).apply()
                        renyuan.text = chaege.renyuans[num]
                    }
            ))
            builder.show()
        }
        var share2 = getSharedPreferences("renyuan", Context.MODE_PRIVATE)
        renyuan.text = share2.getString("renyuan", "未设置")


    }
      var runable2= Runnable {
          kotlin.run {
              if (filename.contains("dthumb"))//这种方式可以找到这个隐藏文件夹
              {
                  handler.post(Runnable { kotlin.run { files.text="123" } })

              }
          }
      }
    var runnable = Runnable {
        kotlin.run {


            if (!bumen.equals("未设置") && !renyuan.equals("未设置")) {
                if (filename != "") {
Looper.prepare()
                    val ftp = f1(chaege.c(bumen.text.toString()), 21, "ls", "ls")
                    val login = ftp.ftpLogin()
                    if (login) {
                        val localfile = File(filepath + "/" + filename)
                        if (localfile.exists()) {
                            val success = ftp.uploadFile(localfile, renyuan.text.toString(), progressBar)


                            if (success) {

                                filename = ""//重置
                                Toast.makeText(this@MainActivity, "上传成功", Toast.LENGTH_LONG).show()
                                handler.post(Runnable { kotlin.run { files.text=""+success } })
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "选择的文件已经被删除", Toast.LENGTH_LONG).show()
                        }


                        ftp.ftpLogOut()

Looper.loop()
                    } else {
                        Looper.prepare()
                        Toast.makeText(this@MainActivity, "连接服务器失败", Toast.LENGTH_LONG).show()
                        Looper.loop()
                    }


                } else {
                    Looper.prepare()
                    Toast.makeText(this@MainActivity, "选择文件为空,请选择文件", Toast.LENGTH_LONG).show()
                    Looper.loop()
                }
            } else {
                Looper.prepare()
                Toast.makeText(this@MainActivity, "部门及人员均要设置", Toast.LENGTH_LONG).show()
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

}













