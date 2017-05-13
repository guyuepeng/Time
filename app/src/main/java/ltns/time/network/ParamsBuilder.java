package ltns.time.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guyuepeng on 2017/5/9.
 */
public class ParamsBuilder {
    public Map<String,String> map;

    public ParamsBuilder() {
        this.map = new HashMap<>();
    }
    public void addParams(String key,String value){
        map.put(key,value);
    }

}
