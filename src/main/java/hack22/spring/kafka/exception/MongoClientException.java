package hack22.spring.kafka.exception;
public class MongoClientException extends RuntimeException {
    public MongoClientException(String message) {
        super(message);
    }
}
