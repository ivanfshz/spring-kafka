package hack22.spring.kafka.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

@Data
@Builder
@ToString(callSuper = true)
public class DynamicXml2JsonResponse extends RepresentationModel<DynamicXml2JsonResponse> {

    private String key;
    private String message;
    public Map<String, Object> content;

}