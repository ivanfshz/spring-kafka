package hack22.spring.kafka.controller;

import hack22.spring.kafka.exception.KafkaClientException;
import hack22.spring.kafka.exception.NotFoundException;
import hack22.spring.kafka.model.DynamicXml2Json;
import hack22.spring.kafka.model.DynamicXml2JsonResponse;
import hack22.spring.kafka.service.DynamicXml2JsonService;
import hack22.spring.kafka.service.XmlKafkaClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static hack22.spring.kafka.enums.ResponseMessagesEnum.DOCUMENT_NOT_FOUND;
import static hack22.spring.kafka.enums.ResponseMessagesEnum.KAFKA_CLIENT_ERROR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dynamic-xml-json")
public class DynamicXml2JsonController {
    private  final XmlKafkaClientService xmlKafkaClientService;
    private final DynamicXml2JsonService dynamicXml2JsonService;
    @GetMapping
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to kafka confluent consumer/producer api", HttpStatus.OK);
    }
    @Operation(summary = "Get a json representation of an inserted xml")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found a json with a given key",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DynamicXml2Json.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid key supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
    @GetMapping(path = "/json/{key}")
    public ResponseEntity<DynamicXml2JsonResponse> getByKey(@Parameter(description = "key of json to be searched")
                                                            @PathVariable String key) throws RuntimeException {
        DynamicXml2JsonResponse response =  dynamicXml2JsonService.findByKey(key)
                                                    .orElseThrow(() -> new NotFoundException(DOCUMENT_NOT_FOUND.getValue()));
        return new ResponseEntity<>(response.add(
                                            linkTo(methodOn(DynamicXml2JsonController.class)
                                                    .welcome())
                                                    .withSelfRel(),
                                            linkTo(methodOn(DynamicXml2JsonController.class)
                                                .getByKey(response.getKey()))
                                                .withSelfRel()), HttpStatus.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succeed operation confirmation!",
                    content = { @Content( mediaType =MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = DynamicXml2Json.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid xml supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
    @PostMapping(path = "/publish/xml/topic/{topic}", consumes =  MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<DynamicXml2JsonResponse>> publishMessage(@Parameter(description = "xml to be sent to kafka topic") @RequestBody String payload,
                                                                                     @PathVariable(name = "topic") String topic) throws RuntimeException {
        return xmlKafkaClientService.publishMessage(topic, payload)
                .thenApplyAsync(record -> record.add(
                        linkTo(methodOn(DynamicXml2JsonController.class)
                                .welcome())
                                .withSelfRel(),
                        linkTo(methodOn(DynamicXml2JsonController.class)
                                .getByKey(record.getKey()))
                                .withSelfRel())
                ).whenCompleteAsync((record, e) -> {
                    if (e != null) {
                        throw new KafkaClientException(KAFKA_CLIENT_ERROR.getValue());
                    }
                })
                .thenApplyAsync(record -> new ResponseEntity<>(record, HttpStatus.OK));
    }
}