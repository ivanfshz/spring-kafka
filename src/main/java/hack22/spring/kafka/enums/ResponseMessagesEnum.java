package hack22.spring.kafka.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseMessagesEnum {
    NO_GOOD ("No good :("),
    DOCUMENT_NOT_FOUND("xml in json representation not found with the given key."),
    KAFKA_CLIENT_ERROR("Error to publish message into the given topic"),
    ALL_GOOD ( "all good :)!");
    private final String value;
}
