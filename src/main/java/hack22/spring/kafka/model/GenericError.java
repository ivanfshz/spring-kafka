package hack22.spring.kafka.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

@Data
@Builder
public class GenericError extends RepresentationModel<GenericError> {
    private Map<String, Object> response;
}
