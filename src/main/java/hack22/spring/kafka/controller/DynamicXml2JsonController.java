package hack22.spring.kafka.controller;

import hack22.spring.kafka.model.DynamicXml2Json;
import hack22.spring.kafka.service.DynamicXml2JsonService;
import hack22.spring.kafka.service.XmlKafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dynamic-xml-json")
public class DynamicXml2JsonController {
    private final XmlKafkaProducerService xmlKafkaProducerService;
    private final DynamicXml2JsonService dynamicXml2JsonService;

    @GetMapping
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to kafka confluent consumer/producer api", HttpStatus.OK);
    }

    @GetMapping(path = "/{key}")
    public ResponseEntity<DynamicXml2Json> get(@PathVariable String key) {
        DynamicXml2Json dynamicXml2Json = dynamicXml2JsonService.findByKey(key);
        return new ResponseEntity<>(dynamicXml2Json, HttpStatus.OK);
    }

    @PostMapping(path = "/publish",
                consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<DynamicXml2Json> publishMessage(@RequestBody String payload) {
        DynamicXml2Json dynamicXml2Json = xmlKafkaProducerService.publishMessage(payload);
        return new ResponseEntity<>(dynamicXml2Json, HttpStatus.OK);
    }
}