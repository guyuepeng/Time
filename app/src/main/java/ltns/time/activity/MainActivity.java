package ltns.time.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ltns.time.R;
import ltns.time.activity.base.BaseActivity;
import ltns.time.api.Config;
import ltns.time.network.ParamsBuilder;
import ltns.time.network.bean.RandomUnsplashBean;
import ltns.time.network.bean.WeatherBean;
import ltns.time.network.callback.RandomUnsplashCallback;
import ltns.time.network.callback.WeatherCallback;
import ltns.time.utils.DateUtils;
import ltns.time.utils.FileUtils;
import ltns.time.utils.NetUtils;
import ltns.time.utils.PreferencesUtils;
import okhttp3.Call;


public class MainActivity extends BaseActivity {
    @BindView(R.id.iv_background)
    ImageView mIvBackground;
    @BindView(R.id.cardview_refresh)
    CardView mCardviewRefresh;
    @BindView(R.id.iv_refresh)
    ImageView mIvRefresh;
    @BindView(R.id.iv_download)
    ImageView mIvDownload;
    @BindView(R.id.cardview_download)
    CardView mCardviewDownload;
    @BindView(R.id.cardview_settings)
    CardView mCardviewSettinds;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.bottom)
    LinearLayout mBottom;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_units)
    TextView mTvUnits;
    @BindView(R.id.iv_weather)
    ImageView mIvWeather;
    @BindView(R.id.tv_temp)
    TextView mTvTemp;
    @BindView(R.id.ll_header)
    LinearLayout mLlHeader;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;

    private Bitmap mBackground;
    private Thread childThread;//负责无限循环更新TextView内容

    private RotateAnimation mRotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private static final int ANI_DURATION = 1000;

    private void startAni() {
        mRotateAnimation.setDuration(ANI_DURATION);//设置动画持续时间
        mRotateAnimation.setRepeatCount(50);//设置重复次数
        mIvRefresh.startAnimation(mRotateAnimation);
    }

    private void stopAni(Animation mAnimation) {
        mAnimation.cancel();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initBackground();
        startLocation(mAMapLocationListener);

    }

    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //经纬度 例如：location=39.93:116.40（纬度前经度在后，冒号分隔）
            String locationInfo = aMapLocation.getLatitude() + ":" + aMapLocation.getLongitude();
            NetUtils.getNowWeatherInfo(mContext, locationInfo, new WeatherCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    doNotGetWeatherInfo(e);
                }

                @Override
                public void onResponse(WeatherBean response, int id) {
                    doGetWeatherInfo(response);
                }
            });
        }
    };

    private void doNotGetWeatherInfo(Exception e) {
        mIvWeather.setImageResource(R.drawable.n99);
        mTvTemp.setText("获取定位失败");
    }

    private void doGetWeatherInfo(WeatherBean response) {
        String weatherCode = response.weatherCode;
        int resID = getResources().getIdentifier("n" + weatherCode, "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resID);
        mIvWeather.setImageDrawable(image);
        mTvTemp.setText(response.temperature + "℃");

    }


    private void loadNewBackground() {
        mCardviewRefresh.setClickable(false);
        startAni();

        ParamsBuilder mBuilder = new ParamsBuilder();
        mBuilder.addParams("client_id", Config.UnSplash.UNSPLASH_APP_ID);
        NetUtils.get(Config.UnSplash.UNSPLASH_HOST + Config.UnSplash.UNSPLASH_RANDOM, mBuilder, new RandomUnsplashCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mContext, "获取图片资源失败:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                resetCardviewStatusAfterRefresh(false);
            }

            @Override
            public void onResponse(RandomUnsplashBean response, int id) {
                //将对应的下载地址存在本地
                PreferencesUtils.saveDownloadUrl(mContext, response.getLinks().getDownload());
                PreferencesUtils.saveAuthorInfo(mContext, response.getUser().getUsername());
                //获取显示图片的地址，加载到本地缓存，并显示
                String url = response.getUrls().getRegular();
                NetUtils.getBitmap(MainActivity.this, url, new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "加载图片失败:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        resetCardviewStatusAfterRefresh(false);
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        saveCacheAndSetImageRes(response);
                        resetCardviewStatusAfterRefresh(true);
                    }
                });
            }
        });

    }

    private void initAuthorInfo() {
        mTvAuthor.setText("@" + PreferencesUtils.readAuthorInfo(mContext) + "/Unsplash");
    }

    private void saveCacheAndSetImageRes(Bitmap response) {
        mBackground = response;
        initAuthorInfo();
        mIvBackground.setImageBitmap(mBackground);
        FileUtils.saveBitmapCache(MainActivity.this, mBackground);
    }

    private void initBackground() {
        if (!FileUtils.hasBitmapCache(mContext)) {
            loadNewBackground();
            return;
        }
        initAuthorInfo();//显示作者信息
        mBackground = FileUtils.loadBitmapCache(mContext);
        mIvBackground.setImageBitmap(mBackground);
    }


    @OnClick({R.id.cardview_refresh, R.id.cardview_download, R.id.cardview_settings, R.id.ll_header})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardview_refresh:
                loadNewBackground();
                break;
            case R.id.cardview_download:
                doDownload();
                break;
            case R.id.cardview_settings:
                startActivity(SettingsActivity.class);
                break;
            case R.id.ll_header:
                startLocation(mAMapLocationListener);
                break;
        }
    }

    private void doDownload() {
        String downloadUrl = PreferencesUtils.readDownloadUrl(mContext);
        if (downloadUrl == null || downloadUrl.equals("")) {
            mCardviewDownload.setClickable(true);//失败后允许再次尝试下载
            Toast.makeText(mContext, "未拉取到下载地址，已取消下载", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
        //启动屏幕常亮
        final String tag = "mPowerTag";
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        final PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, tag);
        mWakeLock.acquire();


        mCardviewDownload.setClickable(false);//避免重复下载

        NetUtils.downloadFile(mContext, downloadUrl, new FileCallBack(FileUtils.getAppRootPath(), Config.DOWNLOAD_IMAGE_NAME) {
            @Override
            public void onError(Call call, Exception e, int id) {
                //取消屏幕常亮
                mWakeLock.release();
                mCardviewDownload.setClickable(true);//失败后允许再次尝试下载
                Toast.makeText(mContext, "图片下载失败：" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.i("-->", "onError: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(File response, int id) {
                //取消屏幕常亮
                mWakeLock.release();
                Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                mIvDownload.setImageResource(R.drawable.download_ok);
                Intent intent =
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(response);
                intent.setData(uri);
                mContext.sendBroadcast(intent);
            }
        });
    }

    private void resetCardviewStatusAfterRefresh(boolean refreshSucceed) {
        mCardviewRefresh.setClickable(true);
        //如果动画没停，先把动画停了
        if (!mRotateAnimation.hasEnded())
            stopAni(mRotateAnimation);
        //如果加载新图片失败了，则下载按钮的clickable状态不变，图片资源也不变
        if (!refreshSucceed)
            return;
        mCardviewDownload.setClickable(true);
        mIvDownload.setImageResource(R.drawable.download);
        initTVContent();
    }

    /*
    //开启子线程循环更新TextView
    private int updateDelay = 1000*60;//1s时间间隔
    private boolean FLAG=true;//添加一个标志位，当Activity关闭时，将FLAG设置为false停止子线程中的任务
    private Runnable updateTextView = new Runnable() {
        @Override
        public void run() {
            while (FLAG) {
                if (updateTextViewCompleted) {
                    mHandler.sendEmptyMessageDelayed(UPDATE_TEXTVIEW, updateDelay);
                    updateTextViewCompleted = false;
                }
            }
        }
    };
    private static final int UPDATE_TEXTVIEW = 1;
    private boolean updateTextViewCompleted = true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_TEXTVIEW:
                    mTvContent.setText(getTextViewContent(diffOpinion));
                    updateTextViewCompleted = true;
                    break;
            }
        }
    };
    private void startChildThread() {
        if (childThread!=null&&childThread.isAlive()&&FLAG)
            return;
        childThread=new Thread(updateTextView);
        childThread.start();
    }
    */
    @Override
    protected void onResume() {
        super.onResume();
        initTVContent();
    }

    @Override
    protected void startLocation(AMapLocationListener mLocationListener) {
        super.startLocation(mLocationListener);
        mIvWeather.setImageResource(R.drawable.n99);
        mTvTemp.setText("正在查询...");
    }

    private void initTVContent() {
        mTvContent.setText(getTextViewContent(diffOpinion));
        mTvUnits.setText(DateUtils.getUnit(diffOpinion));
    }


    private String getTextViewContent(int diffOpinion) {
        return DateUtils.getDiffStr(mContext, diffOpinion);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackground != null && !mBackground.isRecycled()) {
            mBackground.recycle();
            mBackground = null;
        }
        if (mRotateAnimation != null && !mRotateAnimation.hasEnded()) {
            mRotateAnimation.cancel();
        }
//        FLAG=false;//停止子线程中的任务

    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
            Toast.makeText(mContext, "再来一下退出应用", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
