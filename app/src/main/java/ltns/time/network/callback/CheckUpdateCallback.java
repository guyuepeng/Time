package ltns.time.network.callback;

import ltns.time.network.bean.VersionBean;
import okhttp3.Call;

/**
 * Created by guyuepeng on 2017/5/13.
 * Email: gu.yuepeng@foxmail.com
 */

public interface CheckUpdateCallback {
    void onCheckError(Call call, Exception e, int id);
    void onIsLatestVersion(VersionBean mVersionBean);
    void doUpdateApp(VersionBean mVersionBean);
}
