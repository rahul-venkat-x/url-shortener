package urlshortener.service;

import urlshortener.model.Url;
import urlshortener.model.UrlRequestDto;

public interface IShortenerService {

     Url getShortUrl(UrlRequestDto url);

     void saveShortUrl(Url url);

     void updateOriginalUrl(String shortUrl, String originalUrl);

     String encodeUrl(String originalUrl, String creationTime);

     String fetchOriginalUrl(String shortUrl);

}
