package hack22.spring.kafka.producer;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final static Logger logger = Logger.getLogger(KafkaProducer.class.getName());

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @EventListener(ApplicationStartedEvent.class)
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

    public void generateMessages() {

        Faker faker = Faker.instance();

        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));
        final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

        Flux.zip(interval, quotes)
        .map(it -> kafkaTemplate.send("topic_0", faker.random().nextInt(42), it.getT2()))
        .blockLast();
    }
}