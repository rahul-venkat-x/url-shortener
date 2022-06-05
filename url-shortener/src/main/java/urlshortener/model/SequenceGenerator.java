package urlshortener.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "SequenceGenerator")
public class SequenceGenerator {

    private String id;
    private long sequence;
}
