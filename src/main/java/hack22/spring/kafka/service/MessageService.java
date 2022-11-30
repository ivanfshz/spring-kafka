package hack22.spring.kafka.service;

import hack22.spring.kafka.entity.Message;
import hack22.spring.kafka.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;
    public Message insert(ConsumerRecord<Integer, String> record) {
        return repository.insert(record);
    }
}