package urlshortener.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.model.Url;
import urlshortener.model.UrlRequestDto;
import urlshortener.model.UrlResponseDto;
import urlshortener.service.IShortenerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;

@RestController
public class ShortenerController {

    private final IShortenerService shortenerService;

    public ShortenerController(IShortenerService shortenerService){
        this.shortenerService = shortenerService;
    }

    @PostMapping(value = "/getShortUrl")
    public ResponseEntity<UrlResponseDto> getShortUrl(@RequestBody @Valid UrlRequestDto urlRequestDto){

        Url url = shortenerService.getShortUrl(urlRequestDto);
        ModelMapper modelMapper = new ModelMapper();
        UrlResponseDto urlResponse = modelMapper.map(url, UrlResponseDto.class);
        return new ResponseEntity<>(urlResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity<HttpStatus> getOriginalUrl(@PathVariable("shortUrl") String shortUrl,
                                                     HttpServletResponse response) throws IOException {
       String originalUrl = shortenerService.fetchOriginalUrl(shortUrl);
       response.sendRedirect(originalUrl);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateOriginalUrl(@RequestBody UrlRequestDto urlRequestDto){
        String newUrl = urlRequestDto.getOriginalUrl();
        String shortUrl = urlRequestDto.getShortUrl();
        shortenerService.updateOriginalUrl(shortUrl,newUrl);
        return new ResponseEntity<>(urlRequestDto, HttpStatus.OK);
    }
}
