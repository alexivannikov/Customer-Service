package com.mycompany.interviewtask.advice;

import com.mycompany.interviewtask.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.mycompany.interviewtask.enumeration.Error.FILE_HANDLING_ERROR;
import static com.mycompany.interviewtask.enumeration.Error.INTERNAL_SERVER_ERROR;

/**
 * Обработчик исключений для преобразования
 * в легкочитаемую модель ошибки
 */
@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDto> handleIOException(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST,
                        FILE_HANDLING_ERROR.getDescription()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleCommonException(Exception ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        INTERNAL_SERVER_ERROR.getDescription()));
    }
}
