package hack22.spring.kafka.service;

import com.github.javafaker.Faker;
import hack22.spring.kafka.model.DynamicXml2Json;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class XmlKafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public DynamicXml2Json publishMessage(String xml) {
        String key = sendMessage(getKey(), xml);
        return DynamicXml2Json.builder()
                .key(key)
                .message("message has been sent successfully. key: " + key)
                .build();
    }
    private String sendMessage(String key, String message) {
        kafkaTemplate.send("db_writer", key, message);
        return key;
    }
    private static String getKey() {
        Faker faker = Faker.instance();
        return String.valueOf(faker.random().nextInt(500));
    }
}
