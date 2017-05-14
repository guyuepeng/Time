package ltns.time.network.callback;

import ltns.time.network.bean.VersionBean;

/**
 * Created by guyuepeng on 2017/5/13.
 * Email: gu.yuepeng@foxmail.com
 */

public interface CheckUpdateCallback {
    void onCheckFailed(Exception e);
    void onCheckSucceed(VersionBean mVersionBean);
}
