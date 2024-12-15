package com.mycompany.interviewtask.service;

import com.mycompany.interviewtask.model.dto.CustomerDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Сервис работы с данными покупателей
 */
public interface CustomerService {

    /**
     * Получение списка номеров телефонов покупателей
     *
     * @return массив байтов для записи в файл
     */
    byte[] getPhoneNumbers() throws IOException;

    /**
     * Сохранение списка покупателей
     *
     * @param customerDtoList список моделей покупателя
     * @return список идентификаторов
     */
    List<UUID> save(List<CustomerDto> customerDtoList);
}
