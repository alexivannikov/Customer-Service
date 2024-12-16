package com.mycompany.interviewtask.service;

import com.mycompany.interviewtask.model.dto.CustomerDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с данными покупателей
 */
public interface CustomerService {
    /**
     * Создание покупателей
     *
     * @param customerDtoList список моделей покупателя
     * @return массив байтов для записи в файл
     */
    byte[] create(List<CustomerDto> customerDtoList) throws IOException;
}
