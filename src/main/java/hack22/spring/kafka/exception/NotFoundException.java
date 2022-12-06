package hack22.spring.kafka.exception;

public class NotFoundException extends  RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }
}
