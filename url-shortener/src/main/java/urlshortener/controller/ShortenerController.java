package urlshortener.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.model.Url;
import urlshortener.model.UrlRequestDto;
import urlshortener.model.UrlResponseDto;
import urlshortener.service.IShortenerService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ShortenerController extends BaseController {

    private final IShortenerService shortenerService;

    public ShortenerController(IShortenerService shortenerService){
        this.shortenerService = shortenerService;
    }

    @PostMapping(value = "/getShortUrl")
    public ResponseEntity<?> getShortUrl(@RequestBody UrlRequestDto urlRequestDto){
        Url url = shortenerService.getShortUrl(urlRequestDto);
        ModelMapper modelMapper = new ModelMapper();
        UrlResponseDto urlResponse = modelMapper.map(url, UrlResponseDto.class);
        return new ResponseEntity<>(urlResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
       String originalUrl = shortenerService.getOriginalUrl(shortUrl);
       response.sendRedirect(originalUrl);
       return null;
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateOriginalUrl(@RequestBody UrlRequestDto urlRequestDto){
        String newUrl = urlRequestDto.getOriginalUrl();
        String shortUrl = urlRequestDto.getShortUrl();
        shortenerService.updateOriginalUrl(shortUrl,newUrl);
        return new ResponseEntity<>(urlRequestDto, HttpStatus.OK);
    }

}
