package ltns.time.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import ltns.time.api.Config;
import ltns.time.service.UpdateService;
import ltns.time.network.bean.VersionBean;
import ltns.time.network.callback.CheckUpdateCallback;

/**
 * Created by guyuepeng on 2017/5/12.
 * Email: gu.yuepeng@foxmail.com
 */

public class AppUtils {
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    public static void checkUpdate(final Context mContext, final CheckUpdateCallback mCallback){
        FIR.checkForUpdateInFIR(Config.FIR_KEY , new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                VersionBean mVersionBean=new Gson().fromJson(versionJson,VersionBean.class);
                Log.i("--->", mVersionBean.getInstallUrl());
                PreferencesUtils.put(mContext,Config.SharePreference.KEY_UPDATE_DOWNLOAD_URL,mVersionBean.getInstallUrl());
                mCallback.onCheckSucceed(mVersionBean);
            }

            @Override
            public void onFail(Exception exception) {
                Log.i("fir", "check fir.im fail! " + "\n" + exception.getMessage());
                mCallback.onCheckFailed(exception);
            }

            @Override
            public void onStart() {
                Toast.makeText(mContext, "检查版本信息...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
            }
        });
    }
    public static void doUpdate(Context mContext){
        Intent service = new Intent(mContext,UpdateService.class);
        mContext.startService(service);
    }
}
