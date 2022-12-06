package hack22.spring.kafka.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaClientProperties {
    private String topic;
}
