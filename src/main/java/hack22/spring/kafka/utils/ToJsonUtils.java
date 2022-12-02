package hack22.spring.kafka.utils;

import org.bson.json.JsonParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.Map;
import java.util.logging.Logger;

public class ToJsonUtils {
    private static final Logger logger = Logger.getLogger(ToJsonUtils.class.getName());

    public static JSONObject toJsonObject(final String json) {
        JSONObject oJson = null;
        try {
            oJson = new JSONObject(json);
        } catch (JSONException e) {
            logger.warning("error to parse xml to json: " + json);
        }
        return oJson;
    }
    public static JSONObject xml2JsonObject(final String xml) {
        JSONObject oJson = null;
        try {
            oJson = XML.toJSONObject(xml);
        } catch (JSONException e) {
            logger.warning("error to parse xml to json: " + xml);
        }
        return oJson;
    }
}
