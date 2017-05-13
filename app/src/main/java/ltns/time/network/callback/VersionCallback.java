package ltns.time.network.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import ltns.time.network.bean.VersionBean;
import okhttp3.Response;

/**
 * Created by guyuepeng on 2017/5/13.
 * Email: gu.yuepeng@foxmail.com
 */

public abstract class VersionCallback extends Callback<VersionBean> {
    @Override
    public VersionBean parseNetworkResponse(Response response, int id) throws Exception {
        return new Gson().fromJson(response.body().string(),VersionBean.class);
    }
}
