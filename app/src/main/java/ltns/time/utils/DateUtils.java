package ltns.time.utils;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import ltns.time.api.Config;


/**
 * Created by guyuepeng on 2017/5/9.
 * Email: gu.yuepeng@foxmail.com
 */

public class DateUtils {
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DATE = "date";
    public static final String HOUR = "hour";
    public static final String MIN = "min";


    public static long getDiffSecond(Context context) {
        Calendar then = PreferencesUtils.readCalendar(context);
        Calendar now = Calendar.getInstance();
        Date currentDate = now.getTime();
        Date importantDate = then.getTime();
        return currentDate.getTime() - importantDate.getTime();
    }

    public static String getDiffMonths(Context context) {
        double diffMonth = getDiffSecond(context) / 1000 / 60 / 60 / 24 / ((double) 30);
        return diffMonth + "";
    }

    public static String getDiffWeeks(Context context) {
        double diffWeeks = getDiffSecond(context) / 1000 / 60 / 60 / 24 / ((double) 7);
        return diffWeeks + "";
    }

    public static String getDiffDays(Context context) {
        double diffDays = getDiffSecond(context) / 1000 / 60 / 60 / ((double) 24);
        return diffDays + "";
    }

    public static String getDiffHours(Context context) {
        double diffHours = getDiffSecond(context) / 1000 / 60 / ((double) 60);
        return diffHours + "";
    }

    public static String getDiffMins(Context context) {
        double diffMins = getDiffSecond(context) / 1000 / ((double) 60);
        return diffMins + "";
    }

    public static String getDiffStr(Context mContext, int opinion) {
        switch (opinion) {
            case Config.DiffOpinion.DIFF_MIN:
                return getDiffMins(mContext);
            case Config.DiffOpinion.DIFF_HOUR:
                return getDiffHours(mContext);
            case Config.DiffOpinion.DIFF_DAY:
                return getDiffDays(mContext);
            case Config.DiffOpinion.DIFF_WEEK:
                return getDiffWeeks(mContext);
            case Config.DiffOpinion.DIFF_MONTH:
                return getDiffMonths(mContext);
        }
        throw new RuntimeException(opinion + "不在范围内");
    }

    public static String getUnit(int opinion) {
        switch (opinion) {
            case Config.DiffOpinion.DIFF_MIN:
                return "Mins";
            case Config.DiffOpinion.DIFF_HOUR:
                return "Hours";
            case Config.DiffOpinion.DIFF_DAY:
                return "Days";
            case Config.DiffOpinion.DIFF_WEEK:
                return "Weeks";
            case Config.DiffOpinion.DIFF_MONTH:
                return "Months";
        }
        throw new RuntimeException(opinion + "不在范围内");
    }

}
