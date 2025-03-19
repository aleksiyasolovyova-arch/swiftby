package be.kdg.swiftby.presentation.controller;


import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.presentation.webapi.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({NotFoundException.class})
    public Object handleNotFoundException(Exception e, HttpServletRequest request) throws Exception {
        if (request.getRequestURI().startsWith("/api")) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDto(e.getMessage()));
        }

        log.error(e.getMessage());
        throw e;
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public Object handleAlreadyExistsException(Exception e, HttpServletRequest request) throws Exception {
        if (request.getRequestURI().startsWith("/api")) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorDto(e.getMessage()));
        }

        log.error(e.getMessage());
        throw e;
    }

    @ExceptionHandler({Exception.class})
    public Object handleAllExceptions(Exception e, HttpServletRequest request) throws Exception {
        if (request.getRequestURI().startsWith("/api")) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDto(e.getMessage()));
        }

        log.error(e.getMessage());
        throw e;
    }

}

