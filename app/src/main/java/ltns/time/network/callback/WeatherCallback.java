package ltns.time.network.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import ltns.time.network.bean.WeatherBean;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by guyuepeng on 2017/5/9.
 * Email: gu.yuepeng@foxmail.com
 */

public abstract class WeatherCallback extends Callback<WeatherBean> {

    @Override
    public WeatherBean parseNetworkResponse(Response response, int id) throws Exception {
        ResponseBody mBody = response.body();
        return new Gson().fromJson(mBody.string(), WeatherBean.class);
    }

}
