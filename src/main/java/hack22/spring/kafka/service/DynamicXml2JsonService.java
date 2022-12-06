package hack22.spring.kafka.service;

import com.mongodb.DBObject;
import hack22.spring.kafka.model.DynamicXml2Json;
import hack22.spring.kafka.model.DynamicXml2JsonResponse;
import hack22.spring.kafka.repository.DynamicXml2JsonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static hack22.spring.kafka.enums.DynamicXml2JsonEnum.KEY;
import static hack22.spring.kafka.enums.DynamicXml2JsonEnum.XML;
import static hack22.spring.kafka.enums.ResponseMessagesEnum.ALL_GOOD;
import static hack22.spring.kafka.model.DynamicXml2Json.toDynamicXml2Json;
import static hack22.spring.kafka.utils.ToJsonUtils.xml2JsonObject;

@Service
@RequiredArgsConstructor
public class DynamicXml2JsonService {
    private final DynamicXml2JsonRepository dynamicXml2JsonRepository;
    public DynamicXml2Json save(ConsumerRecord<String, String> record) {
        Map<String, Object> map = toMap(record);
        DBObject dbObject = dynamicXml2JsonRepository.saveDynamicJson(map);
        return toDynamicXml2Json(record.key(), dbObject.toMap());
    }
    public Optional<DynamicXml2JsonResponse> findByKey(final String key) {
        return  dynamicXml2JsonRepository.findByKey(key)
                .map(DynamicXml2Json::getDocument)
                .map(content -> DynamicXml2JsonResponse.builder()
                        .key(key)
                        .message(ALL_GOOD.getValue())
                        .content(content)
                        .build());
    }
    private static Map<String, Object> toMap(ConsumerRecord<String, String> record) {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY.getValue(), record.key());
        map.put(XML.getValue(), xml2JsonObject(record.value()));
        return map;
    }
}