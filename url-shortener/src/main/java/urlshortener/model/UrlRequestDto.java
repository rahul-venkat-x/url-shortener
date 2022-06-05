package urlshortener.model;

import lombok.Data;

@Data
public class UrlRequestDto {
    private String originalUrl;
    private String shortUrl;
}
