package com.mycompany.interviewtask.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Ошибка с описанием
 */
@Getter
@AllArgsConstructor
public enum Error {
    FILE_HANDLING_ERROR("Ошибка при обработке файла"),
    DATA_NOT_FOUND_ERROR("Данные не найдены"),
    INTERNAL_SERVER_ERROR("Внутренняя ошибка сервера");

    private final String description;
}
