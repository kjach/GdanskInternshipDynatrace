package pl.jach.gdanskinternshipdynatrace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String numberOutOfRangeHandler(NumberOutOfRangeException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BadDateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badDateFormatHandler(BadDateFormatException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundHandler(NotFoundException ex) {
        return ex.getMessage();
    }
}
