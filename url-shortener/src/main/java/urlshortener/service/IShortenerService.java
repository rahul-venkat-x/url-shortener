package urlshortener.service;

import urlshortener.model.Url;
import urlshortener.model.UrlRequestDto;

import java.util.Date;

public interface IShortenerService {

     Url getShortUrl(UrlRequestDto url);

     void saveShortUrl(Url url);

     void updateOriginalUrl(String shortUrl, String originalUrl);

     String encodeUrl(String originalUrl, Date creationTime);

     String getOriginalUrl(String shortUrl);

}
