package ltns.time.activity.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.Calendar;

import butterknife.ButterKnife;
import ltns.time.api.Config;
import ltns.time.utils.PreferencesUtils;
import ltns.time.utils.TypefaceUtil;

import static java.util.Calendar.DATE;
import static ltns.time.api.Config.TYPEFACE_CHANGE_ACTION;

/**
 * Created by guyuepeng on 2017/5/10.
 * Email: gu.yuepeng@foxmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected int diffOpinion;//显示的形式
    protected String username;//用户昵称
    protected String typeface;//字体
    protected Calendar mImportantDay;//重要的时刻
    protected String importantDate;

    protected abstract int getLayoutRes();

    private TypefaceChangeReceiver typefaceChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);

        mContext = BaseActivity.this;

    }

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption;

    private void initAMapLocation(AMapLocationListener mLocationListener) {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
    }

    protected void startLocation(final AMapLocationListener mLocationListener) {
        initAMapLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
//                Log.i("-->", aMapLocation.getErrorInfo() + aMapLocation.getLatitude() + "-" + aMapLocation.getLongitude());
                mLocationListener.onLocationChanged(aMapLocation);
                //获取到定位信息后将定位服务关闭
                mLocationClient.onDestroy();
            }
        });
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    protected void initTypeface() {
        typeface = PreferencesUtils.getString(mContext
                , Config.SharePreference.KEY_TYPEFACE, Config.SharePreference.SP_DEFAULT_TYPEFACE);
        //改变新创建Activity的字体
        onTypefaceChange(typeface);
        typefaceChangeReceiver = new TypefaceChangeReceiver();

        IntentFilter typefaceFilter = new IntentFilter();
        typefaceFilter.addAction(TYPEFACE_CHANGE_ACTION);
        registerReceiver(typefaceChangeReceiver, typefaceFilter);
    }


    /**
     * 字体改变
     */
    protected void onTypefaceChange(String typeface) {
        TypefaceUtil.replaceFont(this, typeface);
    }

    /**
     * 字体改变监听，用于改变整个APP字体
     */
    public class TypefaceChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TYPEFACE_CHANGE_ACTION.equals(intent.getAction())) {
                String typeface = intent.getStringExtra("typeface");
                //改变未销毁尚存在的Activity的字体
                onTypefaceChange(typeface);
            }
        }
    }

    protected void startActivity(Class<? extends BaseActivity> clazz) {
        startActivity(new Intent(mContext, clazz));
    }

    protected void startActivityAndFinishCurrent(Class<? extends BaseActivity> clazz) {
        startActivity(new Intent(mContext, clazz));
        finish();
    }

    protected void initOpinions() {
        diffOpinion = PreferencesUtils.getInt(
                mContext, Config.SharePreference.KEY_DIFF_OPINION
                , Config.SharePreference.SP_DEFAULT_DIFF_OPINION);
    }

    protected void initUsername() {
        username = PreferencesUtils.getString(mContext, Config.SharePreference.KEY_USERNAME, Config.SharePreference.SP_DEFAULT_USERNAME);
    }

    protected void initImportantDate() {
        mImportantDay = PreferencesUtils.readCalendar(mContext);
        importantDate = mImportantDay.get(Calendar.YEAR) + "年" + (mImportantDay.get(Calendar.MONTH) + 1) + "月"
                + mImportantDay.get(DATE) + "日" + mImportantDay.get(Calendar.HOUR_OF_DAY) + "时" + mImportantDay.get(Calendar.MINUTE) + "分";
    }


    @Override
    protected void onResume() {
        super.onResume();
        initOpinions();
        initTypeface();
        initUsername();
        initImportantDate();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(typefaceChangeReceiver);
        super.onDestroy();
    }
}
