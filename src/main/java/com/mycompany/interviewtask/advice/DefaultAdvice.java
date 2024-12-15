package com.mycompany.interviewtask.advice;

import com.mycompany.interviewtask.exception.DataNotFoundException;
import com.mycompany.interviewtask.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Обработчик исключений для преобразования
 * в легкочитаемую модель ошибки
 */
@ControllerAdvice
public class DefaultAdvice {
    private static final String FILE_HANDLING_ERROR_TEXT = "Ошибка при обработке файла";
    private static final String DATA_NOT_FOUND_ERROR_TEXT = "Данные не найдены";
    private static final String INTERNAL_SERVER_ERROR_TEXT = "Внутренняя ошибка сервера";

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDto> handleIOException(IOException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST,
                        String.format("%s: %s", FILE_HANDLING_ERROR_TEXT, ex.getMessage())));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorDto> handleDataNotFoundException(DataNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.NOT_FOUND,
                        String.format("%s: %s", DATA_NOT_FOUND_ERROR_TEXT, ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleCommonException(Exception ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage()));
    }
}
