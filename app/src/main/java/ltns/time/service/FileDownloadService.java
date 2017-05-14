package ltns.time.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FileDownloadService extends Service {

    public FileDownloadService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 初始化下载器
     **/
    private void startDownload() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 调用下载  
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


}  