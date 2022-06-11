package urlshortener.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "urls")
public class Url {

    public static final String urlSequence = "URL_SEQUENCE";

    @Id
    private long id;

    private String originalUrl;

    @Indexed(unique = true)
    private String shortUrl;

    @Indexed(name = "creationDate", expireAfter = "5m")
    private Date creationDate;

    private Date expirationDate;

}
