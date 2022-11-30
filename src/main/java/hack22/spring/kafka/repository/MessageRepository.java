package hack22.spring.kafka.repository;

import hack22.spring.kafka.entity.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
    Message insert(ConsumerRecord<Integer, String> record);
}