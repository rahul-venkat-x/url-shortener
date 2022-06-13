package urlshortener.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import urlshortener.exceptions.ShortenerExceptions;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(ShortenerExceptions.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleShortenerException(ShortenerExceptions exception){
        log.error(exception.toString());
        return exception.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        StringBuffer errors = new StringBuffer();
        exception.getBindingResult().getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
        log.error(errors.toString());
        return errors.toString();
    }
}
