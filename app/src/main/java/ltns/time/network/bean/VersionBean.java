package ltns.time.network.bean;

/**
 * Created by guyuepeng on 2017/5/13.
 * Email: gu.yuepeng@foxmail.com
 */

public class VersionBean {

    /**
     * name : time
     * version : 1
     * changelog :
     * updated_at : 1494665144
     * versionShort : 1.0
     * build : 1
     * installUrl : http://download.fir.im/v2/app/install/5916c77cca87a85a9f0002e5?download_token=a9443b3dbed6737ddb089475e51e5215&source=update
     * install_url : http://download.fir.im/v2/app/install/5916c77cca87a85a9f0002e5?download_token=a9443b3dbed6737ddb089475e51e5215&source=update
     * direct_install_url : http://download.fir.im/v2/app/install/5916c77cca87a85a9f0002e5?download_token=a9443b3dbed6737ddb089475e51e5215&source=update
     * update_url : http://fir.im/timeLTNS
     * binary : {"fsize":11736509}
     */

    private String name;
    private String version;
    private String changelog;
    private int updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    private BinaryBean binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public static class BinaryBean {
        /**
         * fsize : 11736509
         */

        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }
    }
}
