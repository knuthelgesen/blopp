package no.plasmid.blopp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionMapper {

    private final static Logger LOG = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler(BloppException.class)
    public ResponseEntity<ErrorJson> handleBloppException(BloppException e) {
        LOG.debug(e.getMessage(), e);
        return new ResponseEntity<ErrorJson>(new ErrorJson(e), e.getHttpStatus());
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorJson handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        LOG.debug(e.getMessage(), e);
        return new ErrorJson(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorJson handleAnyException(Exception e) {
        LOG.error(e.getMessage(), e);
        return new ErrorJson(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
