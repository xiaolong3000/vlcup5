package com.long345.vlcup5;



import android.widget.ProgressBar;



import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class FTPProcess implements CopyStreamListener {
    private long fileSize;
    private long startTime;
    private ProgressBar process;

    public FTPProcess(long fileSize, long startTime, ProgressBar process) {
        this.fileSize = fileSize;
        this.startTime = startTime;
        this.process = process;


    }

    @Override
    public void bytesTransferred(CopyStreamEvent copyStreamEvent) {
    }

    @Override
    public void bytesTransferred(long totalBytesTransferred, int bytes, long streamSize) {
        long end_time = System.currentTimeMillis();
        long time = (end_time - startTime) / 1000;
        long speed;
        if (time == 0) {
            speed = 0;
        } else {
            speed = totalBytesTransferred / 1024 / time;
        }
        //    System.out.printf("\r\n %d%%  %d  %dKB/s  %d  %ds",totalBytesTransferred * 100/fileSize,totalBytesTransferred,speed,fileSize,time);
        //  Toast.makeText(context,""+totalBytesTransferred * 100/fileSize+"%",Toast.LENGTH_SHORT).show();
          process.setProgress((int) (totalBytesTransferred * 100/fileSize));
      //  process.setText((int) (totalBytesTransferred*100/fileSize));
    }
}