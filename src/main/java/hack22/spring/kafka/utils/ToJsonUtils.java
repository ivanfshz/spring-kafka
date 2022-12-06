package hack22.spring.kafka.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToJsonUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(ToJsonUtils.class);
    public static JSONObject toJsonObject(final String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            LOGGER.error("error to parse xml to json: " + json);
            return null;
        }
    }
    public static JSONObject xml2JsonObject(final String xml) {
        try {
            return XML.toJSONObject(xml);
        } catch (JSONException e) {
            LOGGER.error("error to parse xml to json: " + xml);
            return null;
        }
    }
}
