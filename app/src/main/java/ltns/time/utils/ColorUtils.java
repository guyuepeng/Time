package ltns.time.utils;

import android.content.Context;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by guyuepeng on 2017/5/26.
 * Email: gu.yuepeng@foxmail.com
 */

public class ColorUtils {
    /**
     * @param color 参数形式应为#RRGGBB
     * @return
     */
    public static int[] getRGB(String color) {
        String r = color.substring(1, 3);
        String g = color.substring(3, 5);
        String b = color.substring(5);
        int[] rgb = new int[]{Integer.parseInt(r, 16), Integer.parseInt(g, 16), Integer.parseInt(b, 16)};
        return rgb;
    }

    public static float[] rgb2hsv(int rgbR, int rgbG, int rgbB) {
        assert 0 <= rgbR && rgbR <= 255;
        assert 0 <= rgbG && rgbG <= 255;
        assert 0 <= rgbB && rgbB <= 255;
        int[] rgb = new int[]{rgbR, rgbG, rgbB};
        Arrays.sort(rgb);
        int max = rgb[2];
        int min = rgb[0];

        float hsbB = max / 255.0f;
        float hsbS = max == 0 ? 0 : (max - min) / (float) max;

        float hsbH = 0;
        if (max == rgbR && rgbG >= rgbB) {
            hsbH = (rgbG - rgbB) * 60f / (max - min) + 0;
        } else if (max == rgbR && rgbG < rgbB) {
            hsbH = (rgbG - rgbB) * 60f / (max - min) + 360;
        } else if (max == rgbG) {
            hsbH = (rgbB - rgbR) * 60f / (max - min) + 120;
        } else if (max == rgbB) {
            hsbH = (rgbR - rgbG) * 60f / (max - min) + 240;
        }

        return new float[]{hsbH, hsbS, hsbB};
    }

    public static int[] hsv2rgb(float h, float s, float v) {
        assert Float.compare(h, 0.0f) >= 0 && Float.compare(h, 360.0f) <= 0;
        assert Float.compare(s, 0.0f) >= 0 && Float.compare(s, 1.0f) <= 0;
        assert Float.compare(v, 0.0f) >= 0 && Float.compare(v, 1.0f) <= 0;

        float r = 0, g = 0, b = 0;
        int i = (int) ((h / 60) % 6);
        float f = (h / 60) - i;
        float p = v * (1 - s);
        float q = v * (1 - f * s);
        float t = v * (1 - (1 - f) * s);
        switch (i) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
                r = v;
                g = p;
                b = q;
                break;
            default:
                break;
        }
        return new int[]{(int) (r * 255.0), (int) (g * 255.0),
                (int) (b * 255.0)};
    }

    @Deprecated
    public static String getBestFavourColor(Context mContext) {
        Map<String, String> mFavour = PreferencesUtils.parseUserFavour(mContext);
        if (mFavour == null)
            return null;
        Iterator mIterator = mFavour.entrySet().iterator();
        String biggestKey = "";
        int biggestValue = 0;
        while (mIterator.hasNext()) {
            Map.Entry<String, String> mEntry = (Map.Entry) mIterator.next();
            if (Integer.parseInt(mEntry.getValue()) < biggestValue)
                continue;
            biggestKey = mEntry.getKey();
            biggestValue = Integer.parseInt(mEntry.getValue());
        }
        return biggestKey;
    }


    /**
     * 对颜色和最喜欢的颜色对比，评价结果为返回值越小，则越接近
     *
     * @param mContext
     * @param color
     * @return
     */
    public static double rateColor2Favour(Context mContext, String color) {
        int[] rgb = getRGB(color);
        int[] favorRGB = getRGB(getBestFavourColor(mContext));
        float[] hsv = rgb2hsv(rgb[0], rgb[1], rgb[2]);
        float[] favorHSV = rgb2hsv(favorRGB[0], favorRGB[1], favorRGB[2]);
        return Math.abs(favorHSV[0] - hsv[0]) * 0.8
                + Math.abs(favorHSV[1] - hsv[1]) * 0.1
                + Math.abs(favorHSV[2] - hsv[2]) * 0.1;
    }

}
