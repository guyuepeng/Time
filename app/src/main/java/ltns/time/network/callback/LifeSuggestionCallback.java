package ltns.time.network.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import ltns.time.network.bean.LifeSuggestionBean;
import okhttp3.Response;

/**
 * Created by guyuepeng on 2017/5/9.
 * Email: gu.yuepeng@foxmail.com
 */

public abstract class LifeSuggestionCallback extends Callback<LifeSuggestionBean> {
    @Override
    public LifeSuggestionBean parseNetworkResponse(Response response, int id) throws Exception {
        return new Gson().fromJson(response.body().string(),LifeSuggestionBean.class);
    }
}
