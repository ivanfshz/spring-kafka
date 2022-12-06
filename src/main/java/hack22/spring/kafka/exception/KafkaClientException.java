package hack22.spring.kafka.exception;

public class KafkaClientException extends  RuntimeException {

    public KafkaClientException(String msg) {
        super(msg);
    }
}