package ltns.time.api;

import android.os.Environment;

/**
 * Created by guyuepeng on 2017/5/9.
 */
public interface Config {

    String BITMAP_CACHE_NAME = "backgroundBitmapCache";//缓存的文件名
    String DOWNLOAD_IMAGE_PATH = Environment.getRootDirectory().getPath() + "/timeDownload/";//下载文件的存储路径

    //appSettings
    String TYPEFACE_CHANGE_ACTION = "typefaceChange";

    String VERSION_INFO_URL = "https://github.com/guyuepeng/Time/blob/master/version.json";

    String FIR_KEY = "02a42fec9854a370d0800a2802ac1995";

    interface SharePreference {
        String SP_FILENAME = "usSettings";
        String SP_DEFAULT_STRING_VALUE = "";
        int SP_DEFAULT_INT_VALUE = 0;

        String KEY_TYPEFACE = "typeface";//存储在sp中的key常量
        String SP_DEFAULT_TYPEFACE = "fonts/qingting.ttf";

        String KEY_DOWNLOAD_IMAGE_URL = "downloadUrl";//存储在sp中的key常量

        String KEY_DIFF_OPINION ="textview_content_opinion";
        int SP_DEFAULT_DIFF_OPINION = DiffOpinion.DIFF_HOUR;

        String KEY_USERNAME="username";
        String SP_DEFAULT_USERNAME="小傻瓜";

        String KEY_AUTHOR_INFO = "author_info";

        String KEY_UPDATE_DOWNLOAD_URL="downloadUrl";
        String KEY_IMAGE_MAIN_COLOR = "imageMainColor";
        String KEY_USER_FAVOUR_INFO = "userFavouriteInfo";
        String KEY_DOWNLOAD_FLAG = "downloadFlag";
        String DOWNLOAD_FLAG_OK = "true";
        String DOWNLOAD_FLAG_NOT_OK = "false";

    }

    interface Weather {
        String WEATHER_KEY = "hxs1nh8wipsn3tet";

        String WEATHER_HOST = "https://api.seniverse.com/v3";
        String WEATHER_NOW_INFO = "/weather/now.json?";
        String WEATHER_LIFE_SUGGESTION = "/life/suggestion.json?";
    }

    interface UnSplash {
        String UNSPLASH_APP_ID = "4df97b8e524ea4b5d4801b0643fb8199b2d8c510bc7d318b0413fb806e0b692f";
        String UNSPLASH_HOST = "https://api.unsplash.com";
        String UNSPLASH_RANDOM = "/photos/random?";
        String UNSPLASH_PHOTOS = "/photos?";
    }

    interface Typeface {
        String XH = "fonts/thinBlack.ttf";//细黑
        String SD = "fonts/shuidi.ttf";//水滴
        String XY = "fonts/xiyuan.ttf";//细圆
        String QT = "fonts/qingting.ttf";//青庭
    }

    /**
     * 首页显示的时间差的形式
     */
    interface DiffOpinion {
        int DIFF_MIN=0;
        int DIFF_HOUR=1;
        int DIFF_DAY=2;
        int DIFF_WEEK=3;
        int DIFF_MONTH=4;
    }
}
