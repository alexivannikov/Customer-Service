package com.mycompany.interviewtask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Модель ошибки
 */
@Getter
@AllArgsConstructor
public class ErrorDto {
    /**
     * Временная метка
     */
    private LocalDateTime timestamp;
    /**
     * Путь запроса
     */
    private String path;
    /**
     * HTTP-статус
     */
    private HttpStatus status;
    /**
     * Сообщение
     */
    private String message;
}
