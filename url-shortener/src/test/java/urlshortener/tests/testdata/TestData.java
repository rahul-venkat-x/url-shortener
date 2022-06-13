package urlshortener.tests.testdata;

import urlshortener.model.Url;
import urlshortener.model.UrlRequestDto;

import java.time.Instant;
import java.util.Date;

public class TestData {

    public static final String INVALID_URL = "htp://www.helloworld.com";
    public static final String VALID_URL = "https://www.intuit.com/careers/hiring-process/#virtual-interview-guide";
    public static final String SHORT_URL = "intuit-interview";


    public static UrlRequestDto getInvalidUrlRequest(){
        UrlRequestDto urlRequestDto = new UrlRequestDto();
        urlRequestDto.setOriginalUrl(INVALID_URL);
        return urlRequestDto;
    }

    public static UrlRequestDto getValidUrlRequest(){
        UrlRequestDto urlRequestDto = new UrlRequestDto();
        urlRequestDto.setOriginalUrl(VALID_URL);
        return urlRequestDto;
    }

    public static UrlRequestDto getValidUrlRequestWithShortUrl(){
        UrlRequestDto urlRequestDto = new UrlRequestDto();
        urlRequestDto.setOriginalUrl(VALID_URL);
        urlRequestDto.setShortUrl(SHORT_URL);
        return urlRequestDto;
    }

    public static Url sampleUrl(){
        Url url = new Url();
        url.setOriginalUrl(VALID_URL);
        url.setShortUrl(SHORT_URL);
        url.setId(1);
        url.setCreationDate(Date.from(Instant.now()));
        url.setExpirationDate(Date.from(Instant.now()));
        return url;
    }

}
