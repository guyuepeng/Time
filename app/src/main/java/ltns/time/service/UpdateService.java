package ltns.time.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import ltns.time.api.Config;
import ltns.time.utils.FileUtils;
import ltns.time.utils.NetUtils;
import ltns.time.utils.PreferencesUtils;
import okhttp3.Call;

public class UpdateService extends Service {

    public UpdateService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 初始化下载器
     **/
    private void startDownload() {

        Toast.makeText(this, "开始更新", Toast.LENGTH_SHORT).show();
        //设置下载地址  
        String urlPath = PreferencesUtils.getString(this, Config.SharePreference.KEY_UPDATE_DOWNLOAD_URL);
        NetUtils.downloadFile(getBaseContext(), urlPath, new FileCallBack(FileUtils.getAppRootPath() + "apk/", "time.apk") {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(UpdateService.this, "下载新版本应用" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(File response, int id) {

                Toast.makeText(UpdateService.this, "下载完成，开始自动安装", Toast.LENGTH_SHORT).show();
                installApk(response);
            }
        });

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


    //安装apk
    private void installApkNew(Uri uri) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //不加下面这句话是可以的，查考的里面说如果不加上这句的话在apk安装完成之后点击单开会崩溃
        // android.os.Process.killProcess(android.os.Process.myPid());
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

}  