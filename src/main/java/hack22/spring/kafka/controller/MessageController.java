package hack22.spring.kafka.controller;

import hack22.spring.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final static Logger logger = Logger.getLogger(MessageController.class.getName());

    private final KafkaProducer kafkaProducer;

    @GetMapping("/")
    public ResponseEntity<String> gretting() {
        logger.info("hello from controller");
        return new ResponseEntity<>("Welcome to kafka confluent consumer/producer api", HttpStatus.OK);
    }

    @PutMapping("/publish")
    public ResponseEntity pusblishMessage() {
        Map<Integer, String> map = kafkaProducer.publishHobbitQuotes();

        map.entrySet()
            .forEach(e -> {
                logger.info("key: " + e.getKey());
                logger.info("value: " + e.getValue());
            });
        return new ResponseEntity<>(kafkaProducer.publishHobbitQuotes(), HttpStatus.OK);
    }
}