package hack22.spring.kafka.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import hack22.spring.kafka.model.DynamicXml2Json;
import hack22.spring.kafka.utils.ToJsonUtils;
import lombok.RequiredArgsConstructor;
import org.bson.BsonDocument;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static hack22.spring.kafka.enums.DynamicXml2JsonEnum.KEY;
import static hack22.spring.kafka.enums.DynamicXml2JsonEnum.XML;
@Repository
@RequiredArgsConstructor
public class DynamicXml2JsonRepository {
    public static final String COLLECTION_NAME = "messages";
    private final Logger LOGGER = LoggerFactory.getLogger(DynamicXml2JsonRepository.class);
    private final MongoTemplate mongoTemplate;
    public DBObject saveDynamicJson(final Map<String, Object> map) throws RuntimeException {
        DBObject dbObject = Optional.ofNullable(toDBObject(map))
                .orElseThrow(() -> new RuntimeException("error"));
        return mongoTemplate.save(dbObject, COLLECTION_NAME);
    }
    public Optional<DynamicXml2Json> findByKey(final String key) {
        return Optional.ofNullable(mongoTemplate.getCollection(COLLECTION_NAME)
                .find(getQueryFilter(key))
                .sort(getSortExpression())
                .map(Document::toJson)
                .map(ToJsonUtils::toJsonObject)
                .map(DynamicXml2Json::toDynamicXml2Json)
                .first());
    }
    private static BsonDocument getSortExpression() {
        var sortExpression = "{'" +
                KEY.getValue() +
                "':-1}";
        return BsonDocument.parse(sortExpression);
    }
    private static BsonDocument getQueryFilter(final String key) {
        var  queryFilter = "{'_key':'" +
                key +
                "'}";
        return BsonDocument.parse(queryFilter);
    }
    private DBObject toDBObject(final Map map) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<>() {};
        try {
            String xml = String.valueOf(map.get(XML.getValue()));
            String key = String.valueOf(map.get(KEY.getValue()));
            HashMap<String,Object> mapDBObject = mapper.readValue(xml, typeRef);
            mapDBObject.put(KEY.getValue(), key);
            return new BasicDBObject(mapDBObject);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while processing json: {}", e.getMessage());
            return null;
        }
    }
}