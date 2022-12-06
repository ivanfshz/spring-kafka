package hack22.spring.kafka.client;

import hack22.spring.kafka.model.DynamicXml2Json;
import hack22.spring.kafka.service.DynamicXml2JsonService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class XmlKafkaConsumer {
    private final Logger LOGGER = LoggerFactory.getLogger(XmlKafkaConsumer.class);
    private final DynamicXml2JsonService dynamicXml2JsonService;


    @KafkaListener(topics = {"db_writer"},  groupId = "db-writer-test-1")
    public void consume(@Payload ConsumerRecord<String, String> record,
                        @Header(name = KafkaHeaders.KEY, required = false) String key,
                        @Header(name = KafkaHeaders.RAW_DATA, required = false) String rawData,
                        @Header(name = KafkaHeaders.OFFSET, required = false) int offset,
                        @Header(name = KafkaHeaders.ACKNOWLEDGMENT, required = false) String ack,
                        @Header(name = KafkaHeaders.RECEIVED_TIMESTAMP, required = false) String receivedTimestamp,
                        @Header(name = KafkaHeaders.RECEIVED_TOPIC, required = false) String receivedTopic,
                        @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String receivedKey,
                        @Header(name = KafkaHeaders.RECEIVED_PARTITION, required = false) int partition) {

        LOGGER.info(String.format("Received key--{} message [{}] from partition-{} with offset-{}",
                key,
                rawData,
                partition,
                offset));
        LOGGER.info(String.format("Acknowledgment with receivedKey-{} ack--{} receivedTimestamp--{} receivedTopic--{}",
                receivedKey,
                ack,
                receivedTimestamp,
                receivedTopic));

        DynamicXml2Json dynamicXml2Json = dynamicXml2JsonService.save(record);
        LOGGER.info("New record added: {}", dynamicXml2Json.toString());
    }

    //@KafkaListener(topics = "somewhere")
    //@SendTo("nowhere")
    public String listenAndReply(String message) {
        LOGGER.info(String.format("ListenAndReply {}", message));
        return "This is a reply sent after receiving message";
    }
}
