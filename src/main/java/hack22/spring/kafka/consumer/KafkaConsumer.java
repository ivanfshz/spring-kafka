package hack22.spring.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class KafkaConsumer {
    private final static Logger logger = Logger.getLogger(KafkaConsumer.class.getName());

    @KafkaListener(topics = {"topic_1"}, groupId = "consumer1")
    public void consume(ConsumerRecord<Integer, String> message) {
        logger.info("**********************************");
        logger.info("**********************************");
        logger.info("**********************************");

        logger.info("key: " + message.key());
        logger.info("quote: " + message.value());

        logger.info("**********************************");
        logger.info("**********************************");
        logger.info("**********************************");
    }
}
