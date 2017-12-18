package com.long345.vlcup5;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.*;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;



public class f1 {

    private FTPClient ftpclient;
    private String IP;
    private int port;
    private String user;
    private String pass;


    public f1(String IP, int port, String user, String pass) {
        this.IP = IP;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.ftpclient = new FTPClient();
    }

	/*
	 * @return try login is right
	 */

    public boolean ftpLogin() {
        boolean isLogin = false;
        FTPClientConfig ftpClientConfig = new FTPClientConfig();
        ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
        this.ftpclient.setControlEncoding("utf-8");
        this.ftpclient.configure(ftpClientConfig);

        try {
            if (this.port > 0) {
                this.ftpclient.connect(this.IP, this.port);
            }else{
                this.ftpclient.connect(this.IP);
            }

            int reply=this.ftpclient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                this.ftpclient.disconnect();

                return isLogin;
            }
            this.ftpclient.login(this.user, this.pass);
            this.ftpclient.enterLocalPassiveMode();
            this.ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE);

            System.out.println("ftp login sucess");
            isLogin=true;



        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


        }

        this.ftpclient.setBufferSize(1024*2);
        this.ftpclient.setDataTimeout(30*1000);
        return isLogin;
    }

    /*
     * @return close connect to server
     *
     * */
    public void ftpLogOut(){
        if(null!=this.ftpclient && this.ftpclient.isConnected()){
            try {
                boolean result=this.ftpclient.logout();
                if(result){
                    System.out.println("success to logout");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("logot error");
            }finally{
                try{
                    this.ftpclient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                    System.out.println("close connect to server error");
                }
            }
        }
    }



    public FTPClient getFtpclient() {
        return ftpclient;
    }

    public void setFtpclient(FTPClient ftpclient) {
        this.ftpclient = ftpclient;
    }

    /*
         * @return upload file
         * */
    public boolean uploadFile(File localFile, String renyuan, ProgressBar process){
        BufferedInputStream inStream=null;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        long startTime=System.currentTimeMillis();
        String time=df.format(new Date());
        boolean success=false;
           try {
                 String  remotePath="/"+time+"/";
               if (!this.ftpclient.changeWorkingDirectory(remotePath)){
                   this.ftpclient.makeDirectory(remotePath);
                   this.ftpclient.changeWorkingDirectory(remotePath);
               }
               remotePath=remotePath+"/"+renyuan +"/";
               if (!this.ftpclient.changeWorkingDirectory(remotePath)){
                   this.ftpclient.makeDirectory(remotePath);
                   this.ftpclient.changeWorkingDirectory(remotePath);

               }

               inStream = new BufferedInputStream(new FileInputStream(localFile));
           //    System.out.println(localFile.getName() + " start to upload  " + time);
             //  success = this.ftpclient.storeFile(localFile.getName(), inStream);
               this.ftpclient.setCopyStreamListener(new FTPProcess(localFile.length(),startTime,process));
               success=this.ftpclient.storeFile(localFile.getName(), inStream);
               if (success) {
                   System.out.println(localFile.getName() + " success to upload to server");

               }
           }catch(Exception e){
               e.printStackTrace();
           }
            finally {
               if (inStream!=null){
                   try {
                       inStream.close();
                   } catch (IOException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                       System.out.println("close inStream error");
                   }
               }
           }


        return success;
    }

    public  boolean downfile(String remotefile,String localpath){
        BufferedOutputStream bufferedOutputStream=null;
        boolean success=false;
        try {
            this.ftpclient.changeWorkingDirectory(localpath);
            bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(localpath+"\\"+remotefile));
            success=this.ftpclient.retrieveFile(remotefile,bufferedOutputStream);
            if(success){
                 final SimpleDateFormat df=new SimpleDateFormat("HH:mm");
                System.out.println("down file success   "+ df.format(new Date()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedOutputStream!=null){
                try{
                    bufferedOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


        return success;

    }







    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        f1 ftp=new f1("10.103.17.167", 21, "ls", "ls");
        ftp.ftpLogin();

       // ftp.uploadFile(new File("F://Visio_201012530_XiaZaiZhiJia.zip"), "");
        ftp.downfile("weekend.xls","c:");
        ftp.ftpLogOut();
    }

}
