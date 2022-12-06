package hack22.spring.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hack22.spring.kafka.enums.DynamicXml2JsonEnum;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static hack22.spring.kafka.enums.DynamicXml2JsonEnum.DOCUMENT;
import static hack22.spring.kafka.enums.DynamicXml2JsonEnum.KEY;
@Data
@Builder
@ToString(callSuper = true)
public class DynamicXml2Json {

    @JsonProperty("_key")
    private String key;
    @JsonProperty("Document")
    private Map<String, Object> document;

    public static DynamicXml2Json toDynamicXml2Json(final JSONObject jsonObject) {
        return DynamicXml2Json.builder()
                .key(String.valueOf(getKey(jsonObject, KEY)))
                .document((Map) getKey(jsonObject, DOCUMENT))
                .build();
    }
    public static DynamicXml2Json toDynamicXml2Json(final String key, final Map map) {
        return DynamicXml2Json.builder()
                .key(key)
                .document(map)
                .build();
    }
    private static Object getKey(final JSONObject jsonObject, final DynamicXml2JsonEnum key) {
        try {
            return switch (key) {
                case KEY -> jsonObject.get(key.getValue());
                case DOCUMENT -> new ObjectMapper().readValue(jsonObject.toString(), HashMap.class);
                default -> null;
            };
        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}