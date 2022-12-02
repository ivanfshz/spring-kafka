package hack22.spring.kafka.utils;

import org.bson.json.JsonParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import javax.management.modelmbean.XMLParseException;
import java.util.Map;
import java.util.logging.Logger;

public class ToJsonUtils {
    private static final Logger logger = Logger.getLogger(ToJsonUtils.class.getName());

    public static final JSONObject toJsonObject(String json) {
        JSONObject oJson = null;
        try {
            oJson = new JSONObject(json);
        } catch (JSONException e) {
            logger.warning("error to parse xml to json: " + json);
        }
        return oJson;
    }
    public static final JSONObject xml2JsonObject(String xml) {
        JSONObject oJson = null;
        try {
            oJson = XML.toJSONObject(xml);
        } catch (JSONException e) {
            logger.warning("error to parse xml to json: " + xml);
        }
        return oJson;
    }
    public static final JSONObject map2JsonObject(Map map) {
        JSONObject oJson = null;
        try {
            oJson = new JSONObject(map);
        } catch (JsonParseException e) {
            logger.warning("error to parse xml to json: " + map.toString() + " error mssage: " + e.getMessage());
        }
        return oJson;
    }
}
