package hack22.spring.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class KafkaConsumer {

    private final static Logger logger = Logger.getLogger(KafkaConsumer.class.getName());

    @KafkaListener(topics = {"topic_0"}, groupId = "kafka-spring-cloud")
    public void consume(String quote) {

        logger.info("**********************************");
        logger.info("**********************************");
        logger.info("**********************************");

        logger.info("quote for Yessica <3: " + quote);

        logger.info("**********************************");
        logger.info("**********************************");
        logger.info("**********************************");
    }

}
