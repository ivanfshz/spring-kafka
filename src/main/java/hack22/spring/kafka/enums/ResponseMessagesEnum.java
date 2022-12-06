package hack22.spring.kafka.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseMessagesEnum {
    NO_GOOD ("No good :("),
    ALL_GOOD ( "all good :)!");
    private final String value;
}
