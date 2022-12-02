package hack22.spring.kafka.service;

import com.mongodb.DBObject;
import hack22.spring.kafka.model.DynamicXml2Json;
import hack22.spring.kafka.repository.DynamicXml2JsonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static hack22.spring.kafka.enums.DynamicXml2JsonEnum.KEY;
import static hack22.spring.kafka.enums.DynamicXml2JsonEnum.XML;
import static hack22.spring.kafka.model.DynamicXml2Json.toDynamicXml2Json;
import static hack22.spring.kafka.utils.ToJsonUtils.xml2JsonObject;

@Service
@RequiredArgsConstructor
public class DynamicXml2JsonService {
    private final DynamicXml2JsonRepository dynamicXml2JsonRepository;
    public DynamicXml2Json save(ConsumerRecord<String, String> record) {
        Map<String, Object> map = getMap(record);
        DBObject dbObject = dynamicXml2JsonRepository.saveDynamicJson(map);
        return toDynamicXml2Json(record.key(), dbObject.toMap());
    }
    public DynamicXml2Json findByKey(final String key) {
        return dynamicXml2JsonRepository.findByKey(key)
                .orElse(DynamicXml2Json.builder()
                        .key(key)
                        .message("document does not exist with the given key: " + key)
                        .build());
    }
    private static Map<String, Object> getMap(ConsumerRecord<String, String> record) {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY.getValue(), record.key());
        map.put(XML.getValue(), xml2JsonObject(record.value()));
        return map;
    }
}







    /*
    private JSONObject toJSONObject(String value) {
        try {
            return XML.toJSONObject(value);
        } catch (JSONException e) {
            LOGGER.warning("Error to parse string to JSONObject " + e.getMessage());
            return null;
        }
    }

    private BasicDBObject toBasicDBObject(String value) {
        JsonObject jsonObject = toJsonObject(value);
        return new BasicDBObject("_template", jsonObject.getJson());
    }
    */