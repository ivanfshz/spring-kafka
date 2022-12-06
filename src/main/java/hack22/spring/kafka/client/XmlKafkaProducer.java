package hack22.spring.kafka.client;

import hack22.spring.kafka.model.DynamicXml2JsonResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static hack22.spring.kafka.enums.ResponseMessagesEnum.ALL_GOOD;

@Service
@RequiredArgsConstructor
public class XmlKafkaProducer {
    private final Logger LOGGER = LoggerFactory.getLogger(XmlKafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    public CompletableFuture<DynamicXml2JsonResponse> publishMessage(final String xml) {
        String topic = "db_writer";
        return sendMessage(topic, getKey(), xml);
    }
    private CompletableFuture<DynamicXml2JsonResponse> sendMessage(final String topic, final String key, final String message) throws  RuntimeException{
         return kafkaTemplate.send(new ProducerRecord<>(topic, key, message))
                 .thenApplyAsync(record -> {
                         RecordMetadata metadata = record.getRecordMetadata();
                         Map<String, Object> meta = Map.of(
                                 "topic", metadata.topic(),
                                 "offset", metadata.offset(),
                                 "partition", metadata.partition(),
                                 "timestamp", metadata.timestamp()
                         );

                         LOGGER.info("topic--{} offset--{} partition--{} timestamp--{}", metadata.topic(),
                                                                                         metadata.offset(),
                                                                                         metadata.partition(),
                                                                                         metadata.timestamp());

                         return DynamicXml2JsonResponse.builder()
                                 .key(key)
                                 .message(ALL_GOOD.getValue())
                                 .content(meta)
                                 .build();
                 });
    }
    private static String getKey() {
        Random random = new Random();
        int number = random.nextInt(999);
        return String.valueOf(number);
    }
}
