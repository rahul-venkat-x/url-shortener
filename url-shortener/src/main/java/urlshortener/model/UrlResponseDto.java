package urlshortener.model;

import lombok.Data;

import java.util.Date;

@Data
public class UrlResponseDto {

   private String originalUrl;
   private String shortUrl;
   private Date expirationDate;
}
