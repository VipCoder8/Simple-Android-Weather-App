package bee.corp.wepp;
import android.util.Log;

import org.json.*;
public class JSONParser {
    public JSONParser(){}
    public String parseArray(String json, String value){
        try {
            JSONArray j = new JSONArray(json);
            return j.getJSONObject(0).getString(value);
        } catch (JSONException e) {e.printStackTrace();}
        return "null";
    }
    public String parseObject(String json, String value){
        try {
            JSONObject j = new JSONObject(json);
            return j.getString(value);
        } catch (JSONException e) {e.printStackTrace();}
        return "null";
    }
}
