package ltns.time.utils;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.util.Iterator;

import ltns.time.api.Config;
import ltns.time.network.ParamsBuilder;
import ltns.time.network.callback.LifeSuggestionCallback;
import ltns.time.network.callback.VersionCallback;
import ltns.time.network.callback.WeatherCallback;

/**
 * Created by guyuepeng on 2017/5/9.
 */
public class NetUtils {
    public static void get(String url, ParamsBuilder paramsBuilder, Callback callback) {
        GetBuilder mBuilder = OkHttpUtils
                .get().url(url);
        Iterator mIterator = paramsBuilder.map.keySet().iterator();
        while (mIterator.hasNext()) {
            String key = (String) mIterator.next();
            mBuilder.addParams(key, paramsBuilder.map.get(key));
        }
        mBuilder.build()
                .execute(callback);
    }

    public static void post(String url, ParamsBuilder paramsBuilder, Callback callback) {
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post().url(url);
        Iterator mIterator = paramsBuilder.map.keySet().iterator();
        while (mIterator.hasNext()) {
            String key = (String) mIterator.next();
            postFormBuilder.addParams(key, paramsBuilder.map.get(key));
        }
        postFormBuilder.build().execute(callback);
    }

    public static void getBitmap(Context context, String url, BitmapCallback callback) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(callback);
    }

    public static void downloadFile(Context mContext, String url, FileCallBack callBack) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(callBack);
    }

    public static void getNowWeatherInfo(Context context, String locationInfo, WeatherCallback mWeatherCallback) {
        ParamsBuilder paramsBuilder = new ParamsBuilder();
        paramsBuilder.addParams("key", Config.Weather.WEATHER_KEY);
        paramsBuilder.addParams("location", locationInfo);
        get(Config.Weather.WEATHER_HOST + Config.Weather.WEATHER_NOW_INFO, paramsBuilder, mWeatherCallback);
    }

    public static void getLifeSuggestion(Context context, String locationInfo, LifeSuggestionCallback mLifeSuggestionCallback) {
        ParamsBuilder paramsBuilder = new ParamsBuilder();
        paramsBuilder.addParams("key", Config.Weather.WEATHER_KEY);
        paramsBuilder.addParams("location", locationInfo);
        get(Config.Weather.WEATHER_HOST + Config.Weather.WEATHER_LIFE_SUGGESTION, paramsBuilder, mLifeSuggestionCallback);
    }

    public static void getVersionInfo(Context mContext, VersionCallback callback) {
        get(Config.VERSION_INFO_URL,new ParamsBuilder(),callback);
    }
}
