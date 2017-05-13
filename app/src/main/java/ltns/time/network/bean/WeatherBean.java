package ltns.time.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guyuepeng on 2017/5/9.
 * Email: gu.yuepeng@foxmail.com
 */

public class WeatherBean {
    @SerializedName("name")
    public String cityName = "大连";
    @SerializedName("code")
    public String weatherCode="0";
    @SerializedName("text")
    public String weatherInfo = "晴";
    @SerializedName("temperature")
    public String temperature = "15";
    @SerializedName("country")
    public String country = "CN";
    @SerializedName("last_update")
    public String lastUpdate = "2017-05-09T16:50:00+08:00";
}
