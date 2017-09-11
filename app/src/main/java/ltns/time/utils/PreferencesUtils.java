package ltns.time.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ltns.time.api.Config;


/**
 * Created by guyuepeng on 2017/5/10.
 * Email: gu.yuepeng@foxmail.com
 */

public class PreferencesUtils {

    public static void saveDownloadUrl(Context mContext, String downloadUrl) {
        put(mContext, Config.SharePreference.KEY_DOWNLOAD_IMAGE_URL, downloadUrl);
    }

    public static void saveAuthorInfo(Context mContext, String authorInfo) {
        put(mContext, Config.SharePreference.KEY_AUTHOR_INFO, authorInfo);
    }

    public static void saveImageMainColor(Context context, String color) {
        put(context, Config.SharePreference.KEY_IMAGE_MAIN_COLOR, color);
    }

    public static String readDownloadUrl(Context mContext) {
        return getString(mContext, Config.SharePreference.KEY_DOWNLOAD_IMAGE_URL);
    }

    public static String readAuthorInfo(Context mContext) {
        return getString(mContext, Config.SharePreference.KEY_AUTHOR_INFO);
    }

    public static String readImageMainColor(Context mContext) {
        return getString(mContext, Config.SharePreference.KEY_IMAGE_MAIN_COLOR);
    }

    public static void saveUserFavour(Context context, String color) {
        String info = readUserFavour(context);
        Map<String, String> mMap = !"".equals(info)
                ? new Gson().fromJson(info, Map.class) : new HashMap<>();
        if (mMap.containsKey(color)) {
            String valueStr=  mMap.get(color);
            int value=Integer.parseInt(valueStr)+1;
            mMap.put(color,value+"");
        }else{
            mMap.put(color,"1");
        }
        put(context,Config.SharePreference.KEY_USER_FAVOUR_INFO,new Gson().toJson(mMap));
    }

    /**
     * 将用户最爱的颜色解析成map
     * @param mContext
     * @return
     */
    public static Map<String,String> parseUserFavour(Context mContext){
        return new Gson().fromJson(readUserFavour(mContext),Map.class);
    }

    public static String readUserFavour(Context context) {
        return getString(context, Config.SharePreference.KEY_USER_FAVOUR_INFO);
    }
    public static String readDownloadFlag(Context context){
        return getString(context,Config.SharePreference.KEY_DOWNLOAD_FLAG,Config.SharePreference.DOWNLOAD_FLAG_NOT_OK);
    }
    public static void saveDownloadFlag(Context context,String flag){
        put(context,Config.SharePreference.KEY_DOWNLOAD_FLAG,flag);
    }

    public static void saveCalendar(Context context, Calendar calendar) {
        SharedPreferences mSp = context.getSharedPreferences(Config.SharePreference.SP_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSp.edit();
        mEditor.putInt(DateUtils.YEAR, calendar.get(Calendar.YEAR));
        mEditor.putInt(DateUtils.MONTH, calendar.get(Calendar.MONTH));
        mEditor.putInt(DateUtils.DATE, calendar.get(Calendar.DATE));
        mEditor.putInt(DateUtils.HOUR, calendar.get(Calendar.HOUR_OF_DAY));
        mEditor.putInt(DateUtils.MIN, calendar.get(Calendar.MINUTE));
        mEditor.apply();
    }

    public static Calendar readCalendar(Context context) {
        SharedPreferences mSp = context.getSharedPreferences(Config.SharePreference.SP_FILENAME, Context.MODE_PRIVATE);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(mSp.getInt(DateUtils.YEAR, 2016), mSp.getInt(DateUtils.MONTH, 4)
                , mSp.getInt(DateUtils.DATE, 18), mSp.getInt(DateUtils.HOUR, 5), mSp.getInt(DateUtils.MIN, 21));
        return mCalendar;
    }

    public static void put(Context context, @NonNull String key, Object value) {
        SharedPreferences shp = context.getSharedPreferences(Config.SharePreference.SP_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shp.edit();
        if (value instanceof String)
            edit.putString(key, (String) value);
        if (value instanceof Boolean)
            edit.putBoolean(key, (Boolean) value);
        if (value instanceof Float)
            edit.putFloat(key, (Float) value);
        if (value instanceof Long)
            edit.putLong(key, (Long) value);
        if (value instanceof Integer)
            edit.putInt(key, (Integer) value);
        if (value instanceof Set)
            edit.putStringSet(key, (Set<String>) value);
        edit.apply();
    }

    public static int getInt(Context context, @NonNull String key) {
        return getInt(context, key, Config.SharePreference.SP_DEFAULT_INT_VALUE);
    }

    public static int getInt(Context context, @NonNull String key, int defaultValue) {
        SharedPreferences shp = context.getSharedPreferences(Config.SharePreference.SP_FILENAME, Context.MODE_PRIVATE);
        return shp.getInt(key, defaultValue);
    }

    public static String getString(Context context, @NonNull String key) {
        return getString(context, key, Config.SharePreference.SP_DEFAULT_STRING_VALUE);
    }

    public static String getString(Context context, @NonNull String key, @NonNull String defaultString) {
        SharedPreferences shp = context.getSharedPreferences(Config.SharePreference.SP_FILENAME, Context.MODE_PRIVATE);
        return shp.getString(key, defaultString);
    }


}
