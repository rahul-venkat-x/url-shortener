package urlshortener.urlRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import urlshortener.model.Url;

@Repository
public interface IUrlRepository extends MongoRepository<Url,Long> {
    Url findByShortUrl(String shortUrl);

    Boolean existsByShortUrl(String shortUrl);


}
