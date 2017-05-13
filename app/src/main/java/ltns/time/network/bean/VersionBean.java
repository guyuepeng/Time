package ltns.time.network.bean;

/**
 * Created by guyuepeng on 2017/5/13.
 * Email: gu.yuepeng@foxmail.com
 */

public class VersionBean {

    /**
     * appname : us
     * update_url : https:...
     * versionName : 1.0
     * versionCode : 1
     */

    private String appname;
    private String update_url;
    private String versionName;
    private int versionCode;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
