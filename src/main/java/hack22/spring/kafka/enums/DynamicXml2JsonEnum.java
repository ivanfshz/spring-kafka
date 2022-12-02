package hack22.spring.kafka.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DynamicXml2JsonEnum {
    KEY("_key"),
    DOCUMENT("Document"),
    XML("_xml");

    private String value;
}
