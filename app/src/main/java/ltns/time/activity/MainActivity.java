package ltns.time.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ltns.time.R;
import ltns.time.activity.base.BaseActivity;
import ltns.time.api.Config;
import ltns.time.network.ParamsBuilder;
import ltns.time.network.bean.ListUnsplashBean;
import ltns.time.network.bean.RandomUnsplashBean;
import ltns.time.network.bean.WeatherBean;
import ltns.time.network.callback.ListUnsplashCallback;
import ltns.time.network.callback.RandomUnsplashCallback;
import ltns.time.network.callback.WeatherCallback;
import ltns.time.utils.ColorUtils;
import ltns.time.utils.DateUtils;
import ltns.time.utils.FileUtils;
import ltns.time.utils.NetUtils;
import ltns.time.utils.PreferencesUtils;
import okhttp3.Call;
import okhttp3.Request;


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
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar mProgressBar;

    private Bitmap mBackground;

    private boolean downloadFlag = false;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        downloadFlag = Config.SharePreference.DOWNLOAD_FLAG_OK
                .equals(PreferencesUtils.readDownloadFlag(mContext));
        initLocation();
        initBackground();

    }

    /**
     * 初始化位置信息，加载天气状况
     */
    private void initLocation() {
        startLocation(mAMapLocationListener);
    }


    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //example：location=39.93:116.40（Latitude:Longitude）
            String locationInfo = aMapLocation.getLatitude() + ":" + aMapLocation.getLongitude();
            NetUtils.getNowWeatherInfo(mContext, locationInfo, new WeatherCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    doNotGotWeatherInfo(e);
                }

                @Override
                public void onResponse(WeatherBean response, int id) {
                    doGotWeatherInfo(response);
                }
            });
        }
    };

    private void doNotGotWeatherInfo(Exception e) {
        mIvWeather.setImageResource(R.drawable.n99);
        mTvTemp.setText(getStrRes(R.string.getLocationError));
        Toast.makeText(mContext, getStrRes(R.string.weather_error) + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void doGotWeatherInfo(WeatherBean response) {
        String weatherCode = response.weatherCode;
        int resID = getResources().getIdentifier("n" + weatherCode, "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resID);
        mIvWeather.setImageDrawable(image);
        mTvTemp.setText("");
        //心知天气api气温获取不准确
//        mTvTemp.setText(response.temperature + "℃");

    }


    /**
     * 随机加载图片
     */
    private void loadRandomBGImage() {
        mCardviewRefresh.setClickable(false);
        startAni();

        ParamsBuilder mBuilder = new ParamsBuilder();
        mBuilder.addParams("client_id", Config.UnSplash.UNSPLASH_APP_ID);
        NetUtils.get(Config.UnSplash.UNSPLASH_HOST + Config.UnSplash.UNSPLASH_RANDOM, mBuilder, new RandomUnsplashCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mContext, getStrRes(R.string.getImgError) + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                resetCardviewStatusAfterRefresh(false);
            }

            @Override
            public void onResponse(RandomUnsplashBean response, int id) {
                //save downloadUrl and imageMainColor
                PreferencesUtils.saveDownloadUrl(mContext, response.getLinks().getDownload());
                PreferencesUtils.saveImageMainColor(mContext, response.getColor());
                PreferencesUtils.saveAuthorInfo(mContext, response.getUser().getUsername());
                //save cache and set ImageRes
                String url = response.getUrls().getRegular();
                NetUtils.getBitmap(MainActivity.this, url, new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, getStrRes(R.string.loadImgError) + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

    /**
     * 根据喜好加载图片
     */
    @Deprecated
    private void loadBGImageByFavour() {
        mCardviewRefresh.setClickable(false);
        startAni();

        ParamsBuilder mBuilder = new ParamsBuilder();
        mBuilder.addParams("client_id", Config.UnSplash.UNSPLASH_APP_ID);
        NetUtils.get(Config.UnSplash.UNSPLASH_HOST + Config.UnSplash.UNSPLASH_PHOTOS, mBuilder, new ListUnsplashCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mContext, getStrRes(R.string.getImgError) + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                resetCardviewStatusAfterRefresh(false);
            }

            @Override
            public void onResponse(List<ListUnsplashBean> response, int id) {
                ListUnsplashBean bestBean = response.get(0);
                double bestRate = ColorUtils.rateColor2Favour(mContext, bestBean.getColor());
                for (ListUnsplashBean mUnsplashBean : response) {
                    Log.e("------>", "url" + mUnsplashBean.getLinks().getDownload());
                    if (bestRate < ColorUtils.rateColor2Favour(mContext, mUnsplashBean.getColor()))
                        continue;
                    bestBean = mUnsplashBean;
                    bestRate = ColorUtils.rateColor2Favour(mContext, mUnsplashBean.getColor());
                }
                Log.i("------>", "url" + bestBean.getLinks().getDownload());

                //执行到此处bestRate最优
                //save downloadUrl and imageMainColor
                PreferencesUtils.saveDownloadUrl(mContext, bestBean.getLinks().getDownload());
                PreferencesUtils.saveImageMainColor(mContext, bestBean.getColor());
                PreferencesUtils.saveAuthorInfo(mContext, bestBean.getUser().getUsername());
                //save cache and set ImageRes
                String url = bestBean.getUrls().getRegular();
                NetUtils.getBitmap(MainActivity.this, url, new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, getStrRes(R.string.loadImgError) + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
            loadRandomBGImage();
            return;
        }
        initAuthorInfo();//show author info
        mBackground = FileUtils.loadBitmapCache(mContext);
        mIvBackground.setImageBitmap(mBackground);
        if (downloadFlag) {
            mIvDownload.setImageResource(R.drawable.download_ok);
            mCardviewDownload.setClickable(false);
        }
    }


    @OnClick({R.id.cardview_refresh, R.id.cardview_download, R.id.cardview_settings, R.id.ll_header})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardview_refresh:
                loadRandomBGImage();
                break;
            case R.id.cardview_download:
//                saveUserFavourInfo();
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

    @Deprecated
    private void saveUserFavourInfo() {
        String color = PreferencesUtils.readImageMainColor(mContext);
        PreferencesUtils.saveUserFavour(mContext, color);
    }

    private void doDownload() {
        String downloadUrl = PreferencesUtils.readDownloadUrl(mContext);
        if (downloadUrl == null || downloadUrl.equals("")) {
            mCardviewDownload.setClickable(true);//失败后允许再次尝试下载
            Toast.makeText(mContext, getStrRes(R.string.getDownloadUrlError), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(mContext, getStrRes(R.string.startDownload), Toast.LENGTH_SHORT).show();
        //启动屏幕常亮
        final String tag = "mPowerTag";
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        final PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, tag);
        mWakeLock.acquire();


        mCardviewDownload.setClickable(false);//avoid downloading again

        String imageUrl = PreferencesUtils.readDownloadUrl(mContext);

        NetUtils.downloadFile(mContext, downloadUrl, new FileCallBack(FileUtils.getAppRootPath()
                , imageUrl.hashCode() + ".png") {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                mProgressBar.setMax(100);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                //取消屏幕常亮
                mWakeLock.release();
                mCardviewDownload.setClickable(true);//allow to download after error
                Toast.makeText(mContext, getStrRes(R.string.downloadImgError) + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.i("-->", "download Image onError: " + e.getLocalizedMessage());
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                mProgressBar.setProgress(((int) (progress * 100)));
            }

            @Override
            public void onResponse(File response, int id) {
                //取消屏幕常亮
                mWakeLock.release();
                Toast.makeText(mContext, getStrRes(R.string.downloadFinish), Toast.LENGTH_SHORT).show();
                PreferencesUtils.saveDownloadFlag(mContext, Config.SharePreference.DOWNLOAD_FLAG_OK);
                downloadFlag = true;
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
        //if animation is playing, stop it first
        if (!mRotateAnimation.hasEnded())
            stopAni(mRotateAnimation);
        //if loading new Image failed,not reset the button state
        if (!refreshSucceed)
            return;
        mCardviewDownload.setClickable(true);
        mIvDownload.setImageResource(R.drawable.download);
        PreferencesUtils.saveDownloadFlag(mContext, Config.SharePreference.DOWNLOAD_FLAG_NOT_OK);
        downloadFlag = false;
        initTVContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTVContent();
    }

    @Override
    protected void startLocation(AMapLocationListener mLocationListener) {
        super.startLocation(mLocationListener);
        mIvWeather.setImageResource(R.drawable.n99);
        mTvTemp.setText(getStrRes(R.string.querying));
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

    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
            Toast.makeText(mContext, getStrRes(R.string.exitAppToast), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }


    //动画部分
    private RotateAnimation mRotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private static final int ANI_DURATION = 1000;

    private void startAni() {
        mRotateAnimation.setDuration(ANI_DURATION);//animation duration
        mRotateAnimation.setRepeatCount(50);//repeat time
        mIvRefresh.startAnimation(mRotateAnimation);
    }

    private void stopAni(Animation mAnimation) {
        mAnimation.cancel();
    }


}
