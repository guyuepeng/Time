package ltns.time.network.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Arrays;
import java.util.List;

import ltns.time.network.bean.ListUnsplashBean;
import okhttp3.Response;

/**
 * Created by guyuepeng on 2017/5/9.
 * Email: gu.yuepeng@foxmail.com
 */

public abstract class ListUnsplashCallback extends Callback<List<ListUnsplashBean>> {
    @Override
    public List<ListUnsplashBean> parseNetworkResponse(Response response, int id) throws Exception {
        ListUnsplashBean beans[]= new Gson().fromJson(response.body().string(), ListUnsplashBean[].class);
        return Arrays.asList(beans);
    }
}
