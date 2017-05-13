package ltns.time.utils;

import android.Manifest;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * Created by guyuepeng on 2017/5/9.
 * Email: gu.yuepeng@foxmail.com
 */

public class LocationUtils {
    public static void getLocation(final Context context, LocationListener locationListener) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        AndPermission.with(context)
                .requestCode(100)
                .permission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {

                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        Toast.makeText(context, "未得到定位授权，如果需要使用天气功能，请手动打开授权。", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 10, locationListener);
    }
}
