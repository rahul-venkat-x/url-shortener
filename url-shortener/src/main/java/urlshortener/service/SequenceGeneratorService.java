package urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import urlshortener.model.SequenceGenerator;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public long generateSequence(String seqName) {
        SequenceGenerator counter = mongoTemplate.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("sequence",1), options().returnNew(true).upsert(true),
                SequenceGenerator.class);
        return !Objects.isNull(counter) ? counter.getSequence() : 1;
    }
}
