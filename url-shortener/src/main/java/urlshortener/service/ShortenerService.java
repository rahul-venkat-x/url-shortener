package urlshortener.service;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import urlshortener.exceptions.ShortenerExceptions;
import urlshortener.model.Url;
import urlshortener.model.UrlRequestDto;
import urlshortener.urlRepository.IUrlRepository;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ShortenerService implements IShortenerService {

    @Autowired
    private IUrlRepository urlRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Url getShortUrl(UrlRequestDto urlRequestDto) {

        UrlValidator urlValidator = new UrlValidator();
        if(!urlValidator.isValid(urlRequestDto.getOriginalUrl()))
            throw new ShortenerExceptions("Given Url is not valid..Please try another URL");

        Url url = new Url();
        url.setId(sequenceGeneratorService.generateSequence(Url.urlSequence));
        url.setOriginalUrl(urlRequestDto.getOriginalUrl());

        LocalDateTime timeNow = LocalDateTime.now();
        Instant createAt = timeNow.toInstant(ZoneOffset.UTC);
        Instant expireAt = timeNow.plusMinutes(5).toInstant(ZoneOffset.UTC);
        url.setCreationDate(Date.from(Instant.now()));
        url.setExpirationDate(Date.from(expireAt));

        if(urlRequestDto.getShortUrl()!=null) {
            if(urlRepository.existsByShortUrl(urlRequestDto.getShortUrl()))
                throw new ShortenerExceptions("Given short Url already taken..Please try new url");
            url.setShortUrl(urlRequestDto.getShortUrl());
        }
        else {
            String encodedUrl = encodeUrl(url.getOriginalUrl(),url.getCreationDate());
            url.setShortUrl(encodedUrl);
        }
        saveShortUrl(url);
        return url;
    }

    @Override
    public void saveShortUrl(Url url) {
        urlRepository.save(url);
    }

    @Override
    public void updateOriginalUrl(String shortUrl, String originalUrl){
        if(!urlRepository.existsByShortUrl(shortUrl))
           throw new ShortenerExceptions("Given short url not available in database or maybe expired");

        mongoTemplate.updateFirst(new Query(where("shortUrl").is(shortUrl)),
                Update.update("originalUrl",originalUrl),Url.class);
    }

    @Override
    public String encodeUrl(String originalUrl, Date creationTime) {
        return Hashing.crc32()
                .hashString(originalUrl.concat(creationTime.toString()), StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        if(!urlRepository.existsByShortUrl(shortUrl))
            throw new ShortenerExceptions("Given short url not available in database or maybe expired");
        Url url = urlRepository.findByShortUrl(shortUrl);
        return url.getOriginalUrl();
    }

}
