package hack22.spring.kafka.consumer;

import hack22.spring.kafka.model.DynamicXml2Json;
import hack22.spring.kafka.service.DynamicXml2JsonService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class XmlKafkaConsumer {
    private final static Logger logger = Logger.getLogger(XmlKafkaConsumer.class.getName());
    private final DynamicXml2JsonService dynamicXml2JsonService;

    @KafkaListener(topics = {"db_writer"},  groupId = "db-writer-test-1")
    public void consume(ConsumerRecord<String, String> record) {
        DynamicXml2Json dynamicXml2Json = dynamicXml2JsonService.save(record);
        logger.info("New record added: " + dynamicXml2Json.toString());
    }
}
