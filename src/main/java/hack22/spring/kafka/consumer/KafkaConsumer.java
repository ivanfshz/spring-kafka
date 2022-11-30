package hack22.spring.kafka.consumer;

import hack22.spring.kafka.entity.Message;
import hack22.spring.kafka.repository.MessageRepository;
import hack22.spring.kafka.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final static Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getName());

    private final MessageService service;

    //@KafkaListener(topics = {"topic_1"}, groupId = "consumer_1x")
    public void consume(ConsumerRecord<Integer, String> record) {
        Message message = service.insert(record);
        LOGGER.info("New record added: " + message.toString());
    }
}
