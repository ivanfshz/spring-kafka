package hack22.spring.kafka.controller;

import hack22.spring.kafka.exception.KafkaClientException;
import hack22.spring.kafka.exception.MongoClientException;
import hack22.spring.kafka.exception.NotFoundException;
import hack22.spring.kafka.model.GenericError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.UnsupportedMediaType;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestControllerAdvice
public class GlobalRestControllerException {

    @ExceptionHandler(UnsupportedMediaType.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> unsupportedMediaType(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GenericError> notFound(NotFoundException ex) {
        GenericError error = toGenericError(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(KafkaClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericError> errorKafkaProducer(KafkaClientException ex) {
        GenericError error = toGenericError(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MongoClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericError> mongoException(MongoClientException ex) {
        GenericError error = toGenericError(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    private static GenericError toGenericError(RuntimeException ex, HttpStatus status) {

        Map<String, Object> response = Map.of("status", status,
                "timestamp", LocalDate.now(),
                "message", status.getReasonPhrase(),
                "details", ex.getMessage());

        return GenericError.builder()
                .response(response)
                .build()
                .add(linkTo(methodOn(DynamicXml2JsonController.class)
                        .welcome())
                        .withSelfRel(),
                        linkTo(methodOn(DynamicXml2JsonController.class)
                            .getByKey("key"))
                            .withSelfRel());
    }
}
