package urlshortener.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UrlRequestDto {

    @NotNull(message = "Original Url can not be null..Please provide a valid url")
    private String originalUrl;

    @Size(min = 5, max = 20, message = "ShortUrl length should be between 5 and 20")
    private String shortUrl;
}
