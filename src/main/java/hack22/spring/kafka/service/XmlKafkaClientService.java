package hack22.spring.kafka.service;

import hack22.spring.kafka.client.XmlKafkaProducer;
import hack22.spring.kafka.model.DynamicXml2JsonResponse;
import hack22.spring.kafka.properties.KafkaClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class XmlKafkaClientService {
    private final XmlKafkaProducer xmlKafkaProducer;
    private final KafkaClientProperties kafkaClientProperties;

    public CompletableFuture<DynamicXml2JsonResponse> publishMessage(String topic,
                                                                     String xml) {
        if(topic == null || topic.equals("") || topic.trim().equals(""))
            topic = kafkaClientProperties.getTopic();
        return xmlKafkaProducer.sendMessage(topic, getKey(), xml);
    }
    private static String getKey() {
        Random random = new Random();
        int number = random.nextInt(999);
        return String.valueOf(number);
    }
}
