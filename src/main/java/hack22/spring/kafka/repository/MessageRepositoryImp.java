package hack22.spring.kafka.repository;

import hack22.spring.kafka.entity.Message;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

@Repository
@RequiredArgsConstructor
public abstract class MessageRepositoryImp implements MessageRepository {
    private static final Logger LOGGER = Logger.getLogger(MessageRepositoryImp.class.getName());

    private final MongoTemplate mongoTemplate;

    @Override
    public Message insert(ConsumerRecord<Integer, String> record) {
        Message message = Message.builder()
            .id(String.valueOf(record.key()))
            .details(toJSONObject(record.value()))
            .build();

        return mongoTemplate.insert(message);
    }
    private JSONObject toJSONObject(String value) {
        try {
            return new JSONObject(value);
        } catch (JSONException e) {
            LOGGER.warning("Error to parse string to JSONObject " + e.getMessage());
            return null;
        }
    }
}