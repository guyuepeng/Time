package ltns.time.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import ltns.time.R;
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

    private String apkSavePath = FileUtils.getAppRootPath() + "apk/";
    private String apkName = "Time.apk";

    private void startDownload() {

        Toast.makeText(this, getString(R.string.startDownload), Toast.LENGTH_SHORT).show();
        //设置下载地址  
        String urlPath = PreferencesUtils.getString(this, Config.SharePreference.KEY_UPDATE_DOWNLOAD_URL);
        NetUtils.downloadFile(getBaseContext(), urlPath, new FileCallBack(apkSavePath, apkName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(UpdateService.this, getString(R.string.updateError) + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                stopSelf();
            }

            @Override
            public void onResponse(File response, int id) {

                Log.i("--->", "done");
                Toast.makeText(UpdateService.this, getString(R.string.updateSuccess), Toast.LENGTH_SHORT).show();
                installApkFile(UpdateService.this, apkSavePath + apkName);
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // start
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 兼容N版本之后的Uri.fromFile不支持
     * Thanks：http://www.cnblogs.com/yongdaimi/p/6067319.html
     *
     * @param context
     * @param filePath
     */
    public static void installApkFile(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "ltns.time.fileprovider", new File(filePath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}  