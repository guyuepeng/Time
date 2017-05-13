package ltns.time.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ltns.time.api.Config;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by guyuepeng on 2017/5/10.
 * Email: gu.yuepeng@foxmail.com
 */

public class FileUtils {


    //将图像保存到SD卡中
    public static void saveBitmapCache(Context mContext, Bitmap mBitmap) {
        int quality = 100;
        try {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            if (getBitmapSize(mBitmap) > 1024 * 1024 * 8) {
                quality = ((int) (1024 * 1024 * 8 / getBitmapSize(mBitmap) * 100)) - 1;
            }
            boolean res = mBitmap.compress(Bitmap.CompressFormat.PNG, quality, arrayOutputStream);
            byte[] bitmapBuf = arrayOutputStream.toByteArray();
            FileOutputStream outputStream = mContext.openFileOutput(Config.BITMAP_CACHE_NAME, MODE_PRIVATE);
            outputStream.write(bitmapBuf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasBitmapCache(Context mContext) {
        File mCache = new File(mContext.getFilesDir(), Config.BITMAP_CACHE_NAME);
        return mCache.exists();
    }

    public static Bitmap loadBitmapCache(Context mContext) {
        return BitmapFactory.decodeFile(mContext.getFilesDir() + "/" + Config.BITMAP_CACHE_NAME);
    }

    public static int getBitmapSize(Bitmap mBitmap) {
        return mBitmap.getByteCount();
    }

    public static boolean existSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
