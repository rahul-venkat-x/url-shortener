package urlshortener.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import urlshortener.exceptions.ShortenerExceptions;


public class BaseController {

     public static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(ShortenerExceptions.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleShortenerException(ShortenerExceptions exception){
        return exception.getMessage();
    }
}
