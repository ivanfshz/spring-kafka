package hack22.spring.kafka.producer;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    public Map<Integer, String> publishMessage(final String message) {
        Faker faker = Faker.instance();

        Map<Integer, String> map = new HashMap<>();
        for(int i = 0; i < 10; i++) {

            Integer key = faker.random().nextInt(500);

            kafkaTemplate.send("topic_1",
                               key,
                               message);
            map.put(key, message);
        }
    return map;
    }
    public Map<Integer, String> publishHobbitQuotes() {
        Faker faker = Faker.instance();

        Map<Integer, String> map = new HashMap<>();
        for(int i = 0; i < 10; i++) {

            Integer key = faker.random().nextInt(500);
            String value = faker.hobbit().quote();

            kafkaTemplate.send("topic_1",
                               key,
                               value);
            map.put(key, value);
        }
        return map;
    }
}